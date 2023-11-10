package com.postype.sns.domain.member.dto.request;

import com.postype.sns.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest{
	@NotBlank(message = "아이디를 입력해주세요")
	private String memberId;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
	private String password;

	@NotBlank(message = "닉네임을 입력해주세요")
	private String memberName;

	@Email(message = "이메일 형식에 맞지 않습니다")
	@NotBlank(message = "이메일을 입력해주세요")
	private String email;

	@Builder
	public MemberRegisterRequest(String memberId, String password, String memberName, String email) {
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.email = email;
	}

	public static Member getMember(MemberRegisterRequest request) {
		return Member.builder()
				.memberId(request.getMemberId())
				.memberName(request.getMemberName())
				.password(request.getPassword())
				.email(request.getEmail())
				.build();
	}
}
