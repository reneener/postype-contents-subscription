package com.postype.sns.domain.member.dto;

import com.postype.sns.domain.member.domain.Alarm;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmDto {
	private Long id;
	private AlarmType alarmType;
	private AlarmArgs alarmArgs;

	public static AlarmDto fromEntity(Alarm alarm){
		return new AlarmDto(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs()
		);
	}
}
