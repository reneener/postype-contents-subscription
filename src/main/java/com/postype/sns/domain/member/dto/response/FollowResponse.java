package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.FollowDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FollowResponse {
	private Long id;
	private Long fromMemberId;
	private Long toMemberId;

	public FollowResponse(Long id, Long fromMemberId, Long toMemberId) {
		this.id = id;
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
	}

	public static FollowResponse fromFollowDto(FollowDto follow){
		return new FollowResponse(
			follow.getId(),
			follow.getFromMemberId(),
			follow.getToMemberId()
		);
	}

}
