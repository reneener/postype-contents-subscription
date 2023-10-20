package com.postype.sns.domain.member.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class MemberRegisterRequest{
	@NotBlank(message = "아이디를 입력해주세요")
	private final String memberId;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
	private final String password;

	@NotBlank(message = "닉네임을 입력해주세요")
	private final String memberName;

	@Email(message = "이메일 형식에 맞지 않습니다")
	@NotBlank(message = "이메일을 입력해주세요")
	private final String email;

	public MemberRegisterRequest(String memberId, String password, String memberName, String email) {
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.email = email;
	}
}
