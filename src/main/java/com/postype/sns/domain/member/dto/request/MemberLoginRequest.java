package com.postype.sns.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberLoginRequest{
	private String memberId;
	private String password;

	public MemberLoginRequest(String memberId, String password){
		this.memberId = memberId;
		this.password = password;
	}
}
