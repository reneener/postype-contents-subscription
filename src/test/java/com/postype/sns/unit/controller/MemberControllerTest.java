package com.postype.sns.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postype.sns.domain.member.dto.request.MemberLoginRequest;
import com.postype.sns.domain.member.dto.request.MemberRegisterRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.application.MemberService;
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
@AutoConfigureMockMvc //컨트롤러 api 테스트
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;
	@Test
	@DisplayName("회원 가입 성공 테스트")
	@WithAnonymousUser
	public void registerSuccess() throws java.lang.Exception {
		String memberId = "memberId";
		String password = "password";
		String memberName = "memberName";
		String email = "email";

		MemberRegisterRequest request = new MemberRegisterRequest(memberId, password, memberName, email);

		when(memberService.register(request)).thenReturn(mock(MemberDto.class));

		mockMvc.perform(post("/api/v1/members/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(new MemberRegisterRequest(memberId, password, memberName, email)))
		).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("이미 가입된 memberId일 경우 회원가입 실패 테스트")
	public void registerFailCausedByDuplicatedId() throws java.lang.Exception {
		String memberId = "memberId";
		String password = "password";
		String memberName = "memberName";
		String email = "email";

		MemberRegisterRequest request = new MemberRegisterRequest(memberId, password, memberName, email);

		when(memberService.register(request)).thenThrow(new ApplicationException(ErrorCode.DUPLICATED_MEMBER_ID));

		mockMvc.perform(post("/api/v1/members/register")
				.contentType(MediaType.APPLICATION_JSON)
				// TODO : add request body
			.content(objectMapper.writeValueAsBytes(new MemberRegisterRequest(memberId, password, memberName, email)))
		).andDo(print())
			.andExpect(status().isConflict());

	}

	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccess() throws java.lang.Exception {
		String memberId = "memberId";
		String password = "password";
		MemberLoginRequest request = new MemberLoginRequest(memberId, password);

		when(memberService.login(request)).thenReturn("testToken");

		mockMvc.perform(post("/api/v1/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new MemberLoginRequest(memberId, password)))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("MemberId를 찾지 못해 로그인 실패 테스트")
	public void loginFailCausedByNotFoundedId() throws java.lang.Exception {
		String memberId = "name";
		String password = "password";
		MemberLoginRequest request = new MemberLoginRequest(memberId, password);

		when(memberService.login(request)).thenThrow(new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

		mockMvc.perform(post("/api/v1/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				// TODO : add request body
				.content(objectMapper.writeValueAsBytes(new MemberLoginRequest("name", "password")))
			).andDo(print())
			.andExpect(status().is(ErrorCode.MEMBER_NOT_FOUND.getStatus().value()));
	}

	@Test
	@DisplayName("잘못된 패스워드 입력으로 로그인 실패 테스트")
	public void loginFailCausedByWrongPassword() throws java.lang.Exception {
		String memberId = "name";
		String password = "password";
		MemberLoginRequest request = new MemberLoginRequest(memberId, password);

		when(memberService.login(request)).thenThrow(new ApplicationException(ErrorCode.INVALID_PASSWORD));

		mockMvc.perform(post("/api/v1/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				// TODO : add request body
				.content(objectMapper.writeValueAsBytes(new MemberLoginRequest(memberId, password)))
			).andDo(print())
			.andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus().value()));
	}

	@Test
	@WithMockUser
	@DisplayName("알람 리스트 가져오기 성공 테스트")
	public void getAlarmSuccessTest() throws Exception {

		when(memberService.getAlarmList(any(), any())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/v1/members/alarm")
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	@DisplayName("알람 리스트 가져오기 시 로그인 하지 않은 유저의 실패 테스트")
	public void getAlarmListFailCausedByNotLogin() throws Exception {
		mockMvc.perform(get("/api/v1/members/alarm")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}
}
