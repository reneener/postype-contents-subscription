package com.postype.sns.application.controller.dto.response;

import com.postype.sns.application.controller.dto.AlarmDto;
import com.postype.sns.domain.member.model.AlarmArgs;
import com.postype.sns.domain.member.model.AlarmType;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmResponse {
	private Long id;
	private AlarmType alarmType;
	private AlarmArgs alarmArgs;
	private String message;
	private Timestamp registerAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static AlarmResponse fromDto(AlarmDto alarm){
		return new AlarmResponse(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs(),
			alarm.getAlarmType().getMessage(),
			alarm.getRegisterAt(),
			alarm.getUpdatedAt(),
			alarm.getDeletedAt()
		);
	}
}
