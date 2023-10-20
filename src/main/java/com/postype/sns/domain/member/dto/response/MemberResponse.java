package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberResponse {

	private final Long id;
	private final String userName;

	public MemberResponse(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public static MemberResponse fromMemberDto(MemberDto member) {
		return new MemberResponse(
				member.getId(),
				member.getMemberId()
		);
	}

}

