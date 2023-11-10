package com.postype.sns.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberLoginRequest{
	@NotBlank(message = "아이디를 입력해주세요")
	private String memberId;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
	private String password;

	public MemberLoginRequest(String memberId, String password){
		this.memberId = memberId;
		this.password = password;
	}
}
