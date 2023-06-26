package com.postype.sns.domain.member.model;

import com.postype.sns.domain.member.model.AlarmArgs;
import com.postype.sns.domain.member.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
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
