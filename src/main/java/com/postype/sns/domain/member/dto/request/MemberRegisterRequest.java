package com.postype.sns.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberRegisterRequest{
	private String memberId;
	private String password;
	private String memberName;
	private String email;

	public MemberRegisterRequest(String memberId, String password, String memberName, String email) {
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.email = email;
	}
}
