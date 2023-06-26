package com.postype.sns.domain.member.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.fixture.MemberFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private BCryptPasswordEncoder encoder;

	@Test
	@DisplayName("회원가입 성공 테스트")
	void registerOk(){
		String memberId = "memberId";
		String password = "password";
		String memberName = "memberName";
		String email = "email";

		//mocking
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());
		when(encoder.encode(password)).thenReturn("encrypt password");
		when(memberRepository.save(any())).thenReturn(MemberFixture.get(memberId,password,1L));

		Assertions.assertDoesNotThrow(() -> memberService.register(memberId, password, memberName, email));
	}

	@Test
	@DisplayName("회원가입 실패 테스트 - memberId가 이미 있는 경우")
	void registerFailCausedByDuplicatedId(){
		String memberId = "memberId";
		String password = "password";
		String memberName = "memberName";
		String email = "email";

		Member fixture = MemberFixture.get(memberId, password, 1L);
		//mocking
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(fixture));
		when(encoder.encode(password)).thenReturn("encrypt password");
		when(memberRepository.save(any())).thenReturn(Optional.of(fixture));

	 	ApplicationException e =	Assertions.assertThrows(
			ApplicationException.class, () -> memberService.register(memberId, password, memberName, email));
		 Assertions.assertEquals(ErrorCode.DUPLICATED_MEMBER_ID, e.getErrorCode());
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	void loginOk(){
		String memberId = "memberId";
		String password = "password";

		Member fixture = MemberFixture.get(memberId, password, 1L);
		//mocking member 객체로 하기 when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(mock(Member.classs)));
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(fixture));
		when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

		Assertions.assertDoesNotThrow(() -> memberService.login(memberId, password));
	}

	@Test
	@DisplayName("로그인 실패 테스트 - memberId가 없는 경우")
	void loginFailCausedByDuplicatedId(){
		String memberId = "memberId";
		String password = "password";

		//mocking
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> memberService.login(memberId, password));
		Assertions.assertEquals(ErrorCode.MEMBER_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("로그인 실패 테스트 - password가 틀린 경우")
	void loginFailCausedByWrongPassword(){
		String memberId = "memberId";
		String password = "password";
		String wrongPassword = "wrongPassword";

		Member fixture = MemberFixture.get(memberId, password, 1L);
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(fixture));


		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> memberService.login(memberId, wrongPassword));
		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
	}
}
