package com.postype.sns.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.postype.sns.domain.member.dto.request.MemberLoginRequest;
import com.postype.sns.domain.member.dto.request.MemberRegisterRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.member.application.MemberService;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.fixture.MemberFixture;

import java.util.Optional;

import com.postype.sns.global.utill.JwtTokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private BCryptPasswordEncoder encoder;

	@BeforeEach
	public void setUp(){
		ReflectionTestUtils.setField(memberService, "expiredTimeMs", 60000L);
		ReflectionTestUtils.setField(memberService, "secretKey", "2023-postype.sns-application-project.secret_key");
	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void registerOk(){
		MemberRegisterRequest request = MemberFixture.getRegisterCreateRequest();

		when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.empty());
		when(encoder.encode(anyString())).thenReturn("encrypted password");
		when(memberRepository.save(any())).thenReturn(mock(Member.class));

		Assertions.assertDoesNotThrow(() -> memberService.register(request));
	}

	@Test
	@DisplayName("회원가입 실패 테스트 - memberId가 이미 있는 경우")
	void registerFailCausedByDuplicatedId(){
		MemberRegisterRequest request = MemberFixture.getRegisterCreateRequest();

		when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(mock(Member.class)));

	 	ApplicationException e = Assertions.assertThrows(
				 ApplicationException.class, () -> memberService.register(request));
		 Assertions.assertEquals(ErrorCode.DUPLICATED_MEMBER_ID, e.getErrorCode());
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	void loginOk(){
		MemberLoginRequest request = MemberFixture.getLoginRequest();
		Member registeredMember = MemberFixture.get();

		when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(registeredMember));
		when(encoder.matches(any(), any())).thenReturn(true);
		try(MockedStatic<JwtTokenUtils> jwtUtilsMock = mockStatic(JwtTokenUtils.class)) {
			jwtUtilsMock.when(() -> JwtTokenUtils.generateToken(anyString(), anyString(), anyLong())).thenReturn("accessToken");
			Assertions.assertDoesNotThrow(() -> memberService.login(request));
		}


	}

	@Test
	@DisplayName("로그인 실패 테스트 - memberId가 없는 경우")
	void loginFailCausedByDuplicatedId(){
		MemberLoginRequest request = MemberFixture.getLoginRequest();

		when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> memberService.login(request));
		Assertions.assertEquals(ErrorCode.MEMBER_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("로그인 실패 테스트 - password가 틀린 경우")
	void loginFailCausedByWrongPassword(){
		MemberLoginRequest request = MemberFixture.getLoginRequest();
		when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(mock(Member.class)));
		when(encoder.matches(any(), any())).thenReturn(false);

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> memberService.login(request));
		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
	}
}
