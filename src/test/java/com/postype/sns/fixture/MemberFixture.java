package com.postype.sns.fixture;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.MemberRole;
import com.postype.sns.domain.member.dto.request.MemberLoginRequest;
import com.postype.sns.domain.member.dto.request.MemberRegisterRequest;

public class MemberFixture {
	public static Member get(String memberId, String password){
		return Member.builder()
				.memberId(memberId)
				.password(password)
				.role(MemberRole.USER)
				.build();
	}
	public static Member get(){
		return Member.builder()
				.memberId("memberId")
				.password("password")
				.role(MemberRole.USER)
				.email("test@test.com")
				.build();
	}

	public static MemberRegisterRequest getRegisterCreateRequest(){
		return MemberRegisterRequest.builder()
				.memberId("memberId")
				.memberName("memberName")
				.password("password")
				.email("test@test.com")
				.build();
	}
	public static MemberLoginRequest getLoginRequest(){
		return new MemberLoginRequest("memberId", "password");
	}
}
