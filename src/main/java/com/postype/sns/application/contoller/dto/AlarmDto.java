package com.postype.sns.application.contoller.dto;

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
	private Timestamp registerAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static AlarmDto fromEntity(Alarm alarm){
		return new AlarmDto(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs(),
			alarm.getRegisteredAt(),
			alarm.getUpdatedAt(),
			alarm.getDeletedAt()
		);
	}
}
