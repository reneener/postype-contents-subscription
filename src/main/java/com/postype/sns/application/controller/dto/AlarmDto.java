package com.postype.sns.application.controller.dto;

import com.postype.sns.domain.member.model.Alarm;
import com.postype.sns.domain.member.model.AlarmArgs;
import com.postype.sns.domain.member.model.AlarmType;
import java.sql.Timestamp;
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
