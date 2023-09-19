package com.postype.sns.domain.post.application;

import com.postype.sns.domain.order.domain.Point;
import com.postype.sns.domain.post.dto.CommentDto;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.dto.request.PostCreateRequest;
import com.postype.sns.domain.post.dto.request.PostModifyRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmEvent;
import com.postype.sns.domain.member.domain.AlarmType;
import com.postype.sns.domain.member.domain.Follow;
import com.postype.sns.domain.member.repository.FollowRepository;
import com.postype.sns.domain.post.domain.Comment;
import com.postype.sns.domain.post.domain.Like;
import com.postype.sns.domain.post.repository.CommentRepository;
import com.postype.sns.domain.post.repository.LikeRepository;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.dto.PostDto;
import com.postype.sns.domain.post.repository.PostRepository;
import com.postype.sns.global.utill.AlarmProducer;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PostService{

	private final PostRepository postRepository;
	private final FollowRepository followRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final AlarmProducer alarmProducer;

	@Transactional
	public Long create(PostCreateRequest request, MemberDto memberDto){
		Member writer = Member.fromDto(memberDto);

		Post newPost = Post.builder()
				.title(request.getTitle())
				.body(request.getBody())
				.member(writer)
				.price(new Point(request.getPrice()))
				.build();

		Long savedPostId = postRepository.save(newPost).getId();

		List<Member> followingList = followRepository.findAllByToMemberId(writer.getId()).stream()
				.map(Follow::getFromMember)
				.collect(Collectors.toList());

		followingList.forEach(followingMember -> {
			alarmProducer.send(new AlarmEvent(followingMember.getId(), //알람을 받는 작성자를 팔로우 하고 있는 사람들
							AlarmType.NEW_POST_ON_SUBSCRIBER,
							new AlarmArgs(writer.getId(),"Post", savedPostId))
						);
			});

		return savedPostId;
	}

	@Transactional
	public PostDto modify(Long postId, PostModifyRequest request, MemberDto memberDto){
		Member foundedMember = Member.fromDto(memberDto);
		Post post = getPostOrException(postId);

		if(post.getMember().getId() != foundedMember.getId()){
			throw new ApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", foundedMember.getMemberId(), postId));
		}

		post.modify(request.getTitle(), request.getBody());

		return PostDto.fromPost(postRepository.saveAndFlush(post));
	}
	@Transactional
	public void delete(MemberDto memberDto, Long postId){
		Member foundedMember = Member.fromDto(memberDto);
		Post post = getPostOrException(postId);

		if(post.getMember().getId() != foundedMember.getId()){
			throw new ApplicationException(ErrorCode.INVALID_PERMISSION,
					String.format("%s has no permission to delete post %s", foundedMember.getMemberId(), postId));
		}

		likeRepository.deleteAllByPost(post);
		commentRepository.deleteAllByPost(post);
		postRepository.delete(post);
	}

	public Page<PostDto> getList(Pageable pageable){
		return postRepository.findAll(pageable).map(PostDto::fromPost);
	}

	public Page<PostDto> getMyPostList (MemberDto memberDto, Pageable pageable){
		return postRepository.findAllByMemberId(memberDto.getId(), pageable)
				.map(PostDto::fromPost);
	}

	public List<Post> getPostsByIds(List<Long> ids){
		return postRepository.findAllByInId(ids);
	}

	public PostDto getPost(Long id) {
		return postRepository.findById(id)
				.map(PostDto::fromPost).orElseThrow(() ->
						new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", id)));
	}

	public PageCursor<Post> getTimeLinePosts(List<Long> memberIds, CursorRequest request){
		List<Post> posts = findAllByMemberId(memberIds, request);
		Long nextKey = posts.stream()
			.mapToLong(Post::getId)
			.min()
			.orElse(CursorRequest.DEFAULT_KEY);
		return new PageCursor<>(request.next(nextKey), posts);
	}

	private List<Post> findAllByMemberId(List<Long> memberIds, CursorRequest request){
		if(request.hasKey())
			return postRepository.findAllByLessThanIdAndInMemberIdsAndOrderByIdDesc(request.getKey(), memberIds,
				request.getSize());
		return postRepository.findAllByINMemberIdsAndOrderByIdDesc(memberIds, request.getSize());

	}

	@Transactional
	public void like(Long postId, MemberDto memberDto){
		Post post = getPostOrException(postId);
		Member member = Member.fromDto(memberDto);

		likeRepository.findAllByMemberAndPost(member, post).ifPresent(it -> {
			throw new ApplicationException(ErrorCode.ALREADY_LIKE,
				String.format("memberName %s already like post %d", member.getMemberId(), postId));
		});

		likeRepository.save(new Like(member, post));

		alarmProducer.send(new AlarmEvent(post.getMember().getId(), //알람을 받는 포스트 작성자에게 알람 전송
				AlarmType.NEW_LIKE_ON_POST,
				new AlarmArgs(member.getId(), // 알람을 발생시킨 좋아요를 누른 사람의 아이디
					"Like", post.getId())// 알람이 발생한 포스트의 아이디
			)
		);
	}

	public long getLikeCount(Long postId) {
		Post post = getPostOrException(postId);
		return likeRepository.countByPost(post);
	}

	public Page<PostDto> getLikeByMember(MemberDto memberDto, Pageable pageable) {
		Member member = Member.fromDto(memberDto);

		List<Like> likedList = likeRepository.findAllByMember(member);
		List<Post> postList = likedList.stream()
				.map(Like::getPost)
				.collect(Collectors.toList());

		return postRepository.findAllByMember(postList.stream()
				.map(Post::getId)
				.collect(Collectors.toList()), pageable)
				.map(PostDto::fromPost);
	}

	@Transactional
	public void comment(Long postId, MemberDto memberDto, String comment){
		Member member = Member.fromDto(memberDto);
		Post post = getPostOrException(postId);
		Comment newComment = Comment.builder()
				.member(member)
				.post(post)
				.comment(comment)
				.build();

		Long cmtId = commentRepository.save(newComment).getId();

		alarmProducer.send(new AlarmEvent(post.getMember().getId(), //알람을 받는 포스트 작성자에게 알람 전송
			AlarmType.NEW_COMMENT_ON_POST,
			new AlarmArgs(member.getId(), //알람을 발생시킨 코멘트를 작성한 사람의 아이디
				"Comment", cmtId)//알람이 발생한 코멘트의 아이디
			)
		);

	}

	public Page<CommentDto> getComment(Long postId, Pageable pageable){
		Post post = getPostOrException(postId);
		return commentRepository.findAllByPost(post, pageable)
				.map(CommentDto::fromEntity);
	}

	private Post getPostOrException(Long postId){
		return postRepository.findById(postId).orElseThrow(() ->
			new ApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
	}
}
