package com.postype.sns.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRequest{

	private String memberId;
	private String password;
	private String memberName;
	private String email;
}
