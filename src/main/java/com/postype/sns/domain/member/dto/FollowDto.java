package com.postype.sns.domain.member.dto;

import com.postype.sns.domain.member.domain.Follow;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FollowDto{
	private Long id;
	private Long fromMemberId;
	private Long toMemberId;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	public FollowDto(Long id, Long fromMemberId, Long toMemberId, LocalDateTime registeredAt, LocalDateTime updatedAt) {
		this.id = id;
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
	}

	public static FollowDto fromEntity(Follow follow){
		return new FollowDto(
			follow.getId(),
			follow.getFromMember().getId(),
			follow.getToMember().getId(),
			follow.getRegisteredAt(),
			follow.getUpdatedAt()
		);
	}
}
