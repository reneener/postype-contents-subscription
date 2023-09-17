package com.postype.sns.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {
	private Long receiveMemberId;
	private AlarmType type;
	private AlarmArgs args;
}
