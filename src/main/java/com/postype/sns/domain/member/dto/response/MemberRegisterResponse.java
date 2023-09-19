package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.MemberDto;
import lombok.Getter;

@Getter
public class MemberRegisterResponse {
	private Long id;
	private String memberId;

	public MemberRegisterResponse(Long id, String memberId) {
		this.id = id;
		this.memberId = memberId;
	}

	public static MemberRegisterResponse fromMemberDto(MemberDto member){
		return new MemberRegisterResponse(
			member.getId(),
			member.getMemberId()
		);
	}
}
