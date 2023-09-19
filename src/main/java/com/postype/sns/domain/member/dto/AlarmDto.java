package com.postype.sns.domain.member.dto;

import com.postype.sns.domain.member.domain.Alarm;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AlarmDto {
	private Long id;
	private AlarmType alarmType;
	private AlarmArgs alarmArgs;

	public AlarmDto(Long id, AlarmType alarmType, AlarmArgs alarmArgs) {
		this.id = id;
		this.alarmType = alarmType;
		this.alarmArgs = alarmArgs;
	}

	public static AlarmDto fromEntity(Alarm alarm){
		return new AlarmDto(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs()
		);
	}
}
