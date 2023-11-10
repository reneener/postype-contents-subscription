package com.postype.sns.unit.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.postype.sns.domain.member.domain.Follow;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.repository.FollowRepository;
import com.postype.sns.domain.post.dto.request.PostCreateRequest;
import com.postype.sns.domain.post.dto.request.PostModifyRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.post.domain.Comment;
import com.postype.sns.domain.post.domain.Like;
import com.postype.sns.domain.post.repository.CommentRepository;
import com.postype.sns.domain.post.repository.LikeRepository;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.repository.PostRepository;
import com.postype.sns.domain.post.application.PostService;
import com.postype.sns.fixture.LikeFixture;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.PostFixture;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import com.postype.sns.global.utill.AlarmProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@InjectMocks
	private PostService postService;
	@Mock
	private PostRepository postRepository;
	@Mock
	private LikeRepository likeRepository;
	@Mock
	private FollowRepository followRepository;
	@Mock
	private AlarmProducer alarmProducer;
	@Mock
	private CommentRepository commentRepository;

	@Test
	@DisplayName("포스트 작성 성공 테스트")
	void PostCreateSuccess(){
		PostCreateRequest request = PostFixture.getCreateRequest();
		Member writer = MemberFixture.get();
		Member subscribedMember = MemberFixture.get("subscribe", "password");
		List<Follow> list = List.of(Follow.builder()
						.fromMember(subscribedMember)
						.toMember(writer)
				.build());

		when(postRepository.save(any())).thenReturn(mock(Post.class));
		when(followRepository.findAllByToMemberId(any())).thenReturn(list);
		doNothing().when(alarmProducer).send(any());

		Assertions.assertDoesNotThrow(() -> postService.create(request, MemberDto.fromEntity(writer)));
	}

	@Test
	@DisplayName("포스트 수정 성공 테스트")
	void PostModifySuccess(){
		PostModifyRequest request = PostFixture.getModifyRequest();
		Member writer = MemberFixture.get();
		Post post = PostFixture.get(writer);

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
		when(postRepository.saveAndFlush(any())).thenReturn(post);

		Assertions.assertDoesNotThrow(() -> postService.modify(anyLong(), request, MemberDto.fromEntity(writer)));
	}
	@Test
	@DisplayName("포스트 수정 시 작성자와 수정자가 다를 경우 실패 테스트")
	void postModifyFailCausedByNotLoginMember(){
		PostModifyRequest request = PostFixture.getModifyRequest();
		Member modifier = MemberFixture.get("modifier","password");
		Member writer = MemberFixture.get();
		Post post = PostFixture.get(writer);

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.modify(anyLong(), request, MemberDto.fromEntity(modifier)));
		Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
	}
	@Test
	@DisplayName("포스트 수정 시 포스트가 존재 하지 않는 경우 실패 테스트")
	void postModifyFailCausedByNotFoundedPost(){
		PostModifyRequest request = PostFixture.getModifyRequest();
		Member writer = MemberFixture.get();

		when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.modify(anyLong(), request, MemberDto.fromEntity(writer)));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("포스트 삭제 성공 테스트")
	void PostDeleteSuccess(){
		Member writer = MemberFixture.get();
		Post post = PostFixture.get(writer);

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
		doNothing().when(postRepository).delete(any());

		Assertions.assertDoesNotThrow(() -> postService.delete(MemberDto.fromEntity(writer), anyLong()));
	}
	@Test
	@DisplayName("포스트 삭제 시 권한이 없는 경우 실패 테스트")
	void postDeleteFailCausedByNotLoginMember(){
		Member writer = MemberFixture.get();
		Post post = PostFixture.get(writer);
		Member requestMember = MemberFixture.get("requestMember", "password");

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.delete(MemberDto.fromEntity(requestMember), anyLong()));
		Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
	}
	@Test
	@DisplayName("포스트 삭제 시 포스트가 존재 하지 않는 경우 실패 테스트")
	void postDeleteFailCausedByNotFoundedPost(){
		Member writer = MemberFixture.get();

		when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.delete(MemberDto.fromEntity(writer), anyLong()));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("인기 포스트 목록 조회 성공 테스트")
	void FeedListSuccess(){
		Pageable pageable = mock(Pageable.class);

		when(postRepository.findAll(pageable)).thenReturn(Page.empty());

		Assertions.assertDoesNotThrow(() -> postService.getList(pageable));
	}

	@Test
	@DisplayName("내 포스트 목록 조회 성공 테스트")
	void MyFeedListSuccess(){
		Pageable pageable = mock(Pageable.class);
		Member member = mock(Member.class);

		when(postRepository.findAllByMemberId(member.getId(), pageable)).thenReturn(Page.empty());

		Assertions.assertDoesNotThrow(() -> postService.getMyPostList(MemberDto.fromEntity(member), pageable));
	}

	@Test
	@DisplayName("포스트 좋아요 성공 테스트")
	void LikeCreateSuccess(){
		Member requestMember = MemberFixture.get("request", "password");
		Post post = PostFixture.get();

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
		when(likeRepository.findAllByMemberAndPost(any(), any())).thenReturn(Optional.empty());
		when(likeRepository.save(any())).thenReturn(mock(Like.class));
		doNothing().when(alarmProducer).send(any());

		Assertions.assertDoesNotThrow(() -> postService.like(anyLong(), MemberDto.fromEntity(requestMember)));
	}

	@Test
	@WithMockUser
	@DisplayName("이미 포스트의 좋아요를 클릭한 경우 실패 테스트")
	void LikeCreateFailCausedByAlreadyLike(){
		Post post = PostFixture.get();
		Member requestMember = MemberFixture.get("request", "password");
		Like like = LikeFixture.get(requestMember, post);

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
		when(likeRepository.findAllByMemberAndPost(any(), any())).thenReturn(Optional.of(like));

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.like(anyLong(), MemberDto.fromEntity(requestMember)));
		Assertions.assertEquals(ErrorCode.ALREADY_LIKE, e.getErrorCode());
	}

	@Test
	@DisplayName("좋아요를 누른 포스트가 존재하지 않는 경우 실패 테스트")
	void LikeCreateFailCausedByNotFoundedPost(){
		Member requestMember = MemberFixture.get("request", "password");

		when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.like(anyLong(), MemberDto.fromEntity(requestMember)));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@Transactional
	@DisplayName("코멘트 등록 성공 테스트")
	void CommentCreateSuccess(){
		Post post = PostFixture.get();
		Member writer = MemberFixture.get();

		when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
		when(commentRepository.save(any())).thenReturn(mock(Comment.class));
		doNothing().when(alarmProducer).send(any());

		Assertions.assertDoesNotThrow(() -> postService.comment(anyLong(), MemberDto.fromEntity(writer), anyString()));
	}

	@Test
	@DisplayName("코멘트를 등록할 떄 포스트가 존재하지 않는 경우 실패 테스트")
	void CommentCreateFailCausedByNotFoundedPost(){
		Post post = PostFixture.get();
		Member writer = MemberFixture.get();

		when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> postService.comment(anyLong(), MemberDto.fromEntity(writer), anyString()));
		Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
	}

}
