package com.postype.sns.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.application.FollowService;
import com.postype.sns.domain.member.domain.Follow;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.repository.FollowRepository;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.fixture.MemberFixture;

import com.postype.sns.global.utill.AlarmProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {

	@InjectMocks
	FollowService followService;
	@Mock
	FollowRepository followrepository;
	@Mock
	AlarmProducer alarmProducer;

	@Test
	@DisplayName("팔로우 성공 테스트")
	void createFollowSuccess(){
		Member toMember = MemberFixture.get("toMember", "000");
		Member fromMember = MemberFixture.get("fromMember", "000");
		Follow follow = Follow.builder()
				.fromMember(fromMember)
				.toMember(toMember)
				.build();

		when(followrepository.save(any())).thenReturn(follow);
		doNothing().when(alarmProducer).send(any());

		Assertions.assertDoesNotThrow(() -> followService.create(MemberDto.fromEntity(fromMember), MemberDto.fromEntity(toMember)));
	}

	@Test
	@WithMockUser
	@DisplayName("팔로우 요청 멤버와 팔로우 할 멤버가 동일할 경우 실패 테스트")
	void createFollowFailCausedByDuplicatedMemberId(){
		Member toMember = MemberFixture.get("toMember", "password");
		Member fromMember = MemberFixture.get("toMember", "password");

		ApplicationException e = Assertions.assertThrows(
			ApplicationException.class, () -> followService.create(MemberDto.fromEntity(fromMember), MemberDto.fromEntity(toMember)));
		Assertions.assertEquals(ErrorCode.MEMBER_IS_SAME, e.getErrorCode());
	}
}
