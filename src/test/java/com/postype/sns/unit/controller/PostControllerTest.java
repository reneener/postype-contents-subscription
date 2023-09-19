package com.postype.sns.unit.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.dto.request.PostCommentRequest;
import com.postype.sns.domain.post.dto.request.PostCreateRequest;
import com.postype.sns.domain.post.dto.request.PostModifyRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.post.application.PostUseCase;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.post.dto.PostDto;
import com.postype.sns.domain.post.application.PostService;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

	@MockBean
	private PostUseCase postUseCase;

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 작성 성공 테스트")
	void postCreateSuccess() throws java.lang.Exception {
		String title = "title";
		String body = "body";
		String memberId = "memberId";
		int price = 1000;
		PostCreateRequest request = new PostCreateRequest(title, body, price);
		Member writer = MemberFixture.get(memberId, "password", 1L);

		when(postUseCase.execute(request, MemberDto.fromEntity(writer)))
			.thenReturn(PostFixture.get(memberId, 1L, 1L).getId());

		mockMvc.perform(post("/api/v1/posts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body, price)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("포스트 작성 시 로그인을 하지 않은 경우 실패 테스트")
	void postCreateFailCausedByNotLogin() throws java.lang.Exception {

		String title = "title";
		String body = "body";
		int price = 0;

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body, price)))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 수정 성공 테스트")
	void postModifySuccess() throws java.lang.Exception {

		String title = "title";
		String body = "body";
		int price = 0;
		PostModifyRequest request = new PostModifyRequest(title, body, price);
		when(postService.modify(1L, request, any()))
			.thenReturn(PostDto.fromPost(PostFixture.get("memberId", 1L, 1L)));

		mockMvc.perform(put("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body, 0)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("포스트 수정 시 로그인 하지 않은 경우 실패 테스트")
	@WithAnonymousUser
	void postModifyFailCausedByNotLoginMember() throws java.lang.Exception {

		String title = "title";
		String body = "body";

		mockMvc.perform(put("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body, 0)))
			).andDo(print())
			.andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 수정 멤버가 작성 멤버가 아닐 경우 실패 테스트")
	void postModifyFailCausedByNotCreatedMember() throws java.lang.Exception {

		String title = "title";
		String body = "body";
		int price = 0;
		PostModifyRequest request = new PostModifyRequest(title, body, price);

		//반환 값이 없는 경우 when이 아닌 doThrow
		doThrow(new ApplicationException(ErrorCode.INVALID_PERMISSION))
				.when(postService).modify(1L, request, any());

		mockMvc.perform(put("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body, 0)))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 수정 시 포스트가 없는 경우 실패 테스트")
	void postModifyFailCausedByNotFoundedPost() throws java.lang.Exception {

		String title = "title";
		String body = "body";
		int price = 0;
		PostModifyRequest request = new PostModifyRequest(title, body, price);

		//mocking
		doThrow(new ApplicationException(ErrorCode.POST_NOT_FOUND))
				.when(postService).modify(1L, request, any());


		mockMvc.perform(put("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostModifyRequest(title, body, 0)))
			).andDo(print())
			.andExpect(status().isNotFound());
	}
	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 삭제 성공 테스트")
	void postDeleteSuccess() throws java.lang.Exception {

		mockMvc.perform(delete("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("포스트 삭제 시 로그인 하지 않은 경우 실패 테스트")
	void postDeleteFailNotLoginMember() throws java.lang.Exception {

		mockMvc.perform(delete("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 삭제 시 작성자와 삭제요청자가 다를 경우 실패 테스트")
	void postDeleteFailCausedByNotMatchedMember() throws java.lang.Exception {
		//mocking
		doThrow(new ApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).delete(any(), any());

		mockMvc.perform(delete("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 삭제 시 포스트가 존재하지 않는 경우 실패 테스트")
	void postDeleteFailCausedByNotFoundedPost() throws java.lang.Exception {

		doThrow(new ApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).delete(any(), any());
		mockMvc.perform(delete("/api/v1/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("뉴스 피드 목록 조회 성공 테스트")
	void NewsFeedSuccess() throws Exception {

		when(postService.getList(any())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("뉴스피드 목록 조회 시 로그인 하지 않은 경우 실패 테스트")
	void NewsFeedFailCausedByNotLogin() throws Exception {

		when(postService.getList(any())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("내 뉴스 피드 목록 조회 성공 테스트")
	void MyNewsFeedSuccess() throws Exception {

		when(postService.getMyPostList(any(), any())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/v1/posts/my")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("내 뉴스 피드 목록 조회 시 로그인 하지 않은 경우 실패 테스트")
	void MyNewsFeedFailCausedByNotLogin() throws Exception {

		when(postService.getMyPostList(any(), any())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/v1/posts/my")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("포스트 저장 시 타임라인 저장 성공 테스트")
	void saveTimeLineSuccess(){
		String memberId = "member";
		CursorRequest cursorRequest = mock(CursorRequest.class);

		//when(timeLinePostsUseCase.executeTimeLine(memberId, cursorRequest)).thenReturn(PageCursor .class);

	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("좋아요 기능 성공 테스트")
	void likeCreateSuccess() throws Exception {
		mockMvc.perform(post("/api/v1/posts/1/likes")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("좋아요 클릭 시 로그인 하지 않은 경우 실패 테스트")
	void likeCreateFailCausedByNotLogin() throws Exception {

		mockMvc.perform(post("/api/v1/posts/1/likes")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("좋아요 클릭 시 포스트가 없는 경우 실패 테스트")
	void likeCreateFailCausedByNotFoundedPost() throws Exception {
		doThrow(new ApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).like(any(), any());

		mockMvc.perform(post("/api/v1/posts/1/likes")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isNotFound());
	}
	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("댓글 기능 성공 테스트")
	void CommentCreateSuccess() throws Exception {
		mockMvc.perform(post("/api/v1/posts/1/comments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostCommentRequest("comment")))
			).andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("댓글 등록 시 로그인 하지 않은 경우 실패 테스트")
	void CommentCreateFailCausedByNotLogin() throws Exception {

		mockMvc.perform(post("/api/v1/posts/1/comments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostCommentRequest("comment")))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("댓글 등록 시 포스트가 없는 경우 실패 테스트")
	void CommentCreateFailCausedByNotFoundedPost() throws Exception {
		doThrow(new ApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).comment(any(), any(), any());

		mockMvc.perform(post("/api/v1/posts/1/comments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new PostCommentRequest("comment")))
			).andDo(print())
			.andExpect(status().isNotFound());
	}




}
