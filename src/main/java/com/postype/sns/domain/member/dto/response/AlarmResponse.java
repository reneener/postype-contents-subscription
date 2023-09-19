package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.AlarmDto;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AlarmResponse {
	private Long id;
	private AlarmType alarmType;
	private AlarmArgs alarmArgs;
	private String message;

	public AlarmResponse(Long id, AlarmType alarmType, AlarmArgs alarmArgs, String message) {
		this.id = id;
		this.alarmType = alarmType;
		this.alarmArgs = alarmArgs;
		this.message = message;
	}

	public static AlarmResponse fromDto(AlarmDto alarm){
		return new AlarmResponse(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs(),
			alarm.getAlarmType().getMessage()
		);
	}
}
