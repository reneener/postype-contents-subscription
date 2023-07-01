package com.postype.sns.application.controller.dto.response;

import com.postype.sns.application.controller.dto.MemberDto;
import com.postype.sns.domain.member.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRegisterResponse {
	private Long id;
	private String memberId;
	private MemberRole role;

	public static MemberRegisterResponse fromMemberDto(MemberDto member){
		return new MemberRegisterResponse(
			member.getId(),
			member.getMemberId(),
			member.getRole()
		);
	}
}
