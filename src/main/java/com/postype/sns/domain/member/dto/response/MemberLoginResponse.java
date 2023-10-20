package com.postype.sns.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
	private final String token;

	public MemberLoginResponse(String token) {
		this.token = token;
	}
}
