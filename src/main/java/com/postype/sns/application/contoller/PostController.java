package com.postype.sns.application.contoller;

import com.postype.sns.application.contoller.dto.request.PostCreateRequest;
import com.postype.sns.application.contoller.dto.request.PostModifyRequest;
import com.postype.sns.application.contoller.dto.response.PostResponse;
import com.postype.sns.application.contoller.dto.response.Response;
import com.postype.sns.application.usecase.CreatePostUseCase;
import com.postype.sns.application.usecase.TimeLinePostsUseCase;
import com.postype.sns.domain.member.model.util.CursorRequest;
import com.postype.sns.domain.member.model.util.PageCursor;
import com.postype.sns.domain.post.model.Post;
import com.postype.sns.domain.post.model.PostDto;
import com.postype.sns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	private final TimeLinePostsUseCase timeLinePostsUseCase;

	private final CreatePostUseCase createPostUseCase;


	@PostMapping
	public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication){
		createPostUseCase.execute(request.getTitle(), request.getBody(), request.getPrice(), authentication.getName());
		return Response.success();
	}

	@PutMapping("/{postId}")
	public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication){
		PostDto post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
		return Response.success(PostResponse.fromPostDto(post));
	}

	@DeleteMapping("/{postId}")
	public Response<Void> delete(@PathVariable Long postId, Authentication authentication){
		postService.delete(authentication.getName(), postId);
		return Response.success();
	}

	@GetMapping
	public Response<Page<PostResponse>> getPostList(Pageable pageable, Authentication authentication){
		return Response.success(postService.getList(pageable).map(PostResponse::fromPostDto));
	}

	@GetMapping("/my")
	public Response<Page<PostResponse>> getMyPostList(Pageable pageable, Authentication authentication){
		return Response.success(postService.getMyPostList(authentication.getName(), pageable).map(PostResponse::fromPostDto));
	}

	@GetMapping("/member/timeline")
	public PageCursor<Post> getTimeLine(Authentication authentication, CursorRequest request){
		return timeLinePostsUseCase.executeTimeLine(authentication.getName(), request);
	}





}