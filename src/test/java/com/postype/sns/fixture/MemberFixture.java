package com.postype.sns.fixture;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.MemberRole;
import java.sql.Timestamp;
import java.time.Instant;

public class MemberFixture {
//테스트용으로 만든 Member Entity
	public static Member get(String memberId, String password, Long Id){
		Member member = new Member();
			member.setId(Id);
			member.setMemberId(memberId);
			member.setPassword(password);
			member.setRole(MemberRole.USER);
			member.setRegisteredAt(Timestamp.from(Instant.now()));
		return member;
	}
}
