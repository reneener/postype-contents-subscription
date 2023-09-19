package com.postype.sns.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
	private String token;

	public MemberLoginResponse(String token) {
		this.token = token;
	}
}
