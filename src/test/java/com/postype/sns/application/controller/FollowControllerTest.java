package com.postype.sns.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.controller.dto.FollowDto;
import com.postype.sns.application.controller.dto.MemberDto;
import com.postype.sns.domain.member.service.FollowService;
import com.postype.sns.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FollowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private FollowService followService;

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("팔로우 요청 성공 테스트")
	void saveFollowSuccess() throws Exception {
		String fromMember = "fromMember";
		String toMember = "toMember";

		when(memberService.getMember(fromMember)).thenReturn(mock(MemberDto.class));
		when(memberService.getMember(toMember)).thenReturn(mock(MemberDto.class));
		when(followService.create(any(), any())).thenReturn(mock(FollowDto.class));

		mockMvc.perform(post("/api/v1/follow/test03")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("팔로우 시 로그인 하지 않은 경우 실패 테스트")
	@WithAnonymousUser
	void followFailCausedByNotLoginMember() throws java.lang.Exception {

		mockMvc.perform(post("/api/v1/follow/test03")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
	}
	@Test
	@WithMockUser
	@DisplayName("팔로우 할 멤버가 존재 하지 않아 팔로우 요청 실패 테스트")
	void followFailCausedNotFoundedMember() throws Exception {

		when(followService.create(any(), any())).thenThrow(new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

		mockMvc.perform(post("/api/v1/follow/test03")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().is(ErrorCode.MEMBER_NOT_FOUND.getStatus().value()));
	}



}
