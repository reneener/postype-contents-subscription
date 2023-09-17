package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRegisterResponse {
	private Long id;
	private String memberId;

	public static MemberRegisterResponse fromMemberDto(MemberDto member){
		return new MemberRegisterResponse(
			member.getId(),
			member.getMemberId()
		);
	}
}
