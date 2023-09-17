package com.postype.sns.domain.member.dto.response;

import com.postype.sns.domain.member.dto.AlarmDto;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmResponse {
	private Long id;
	private AlarmType alarmType;
	private AlarmArgs alarmArgs;
	private String message;

	public static AlarmResponse fromDto(AlarmDto alarm){
		return new AlarmResponse(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getAlarmArgs(),
			alarm.getAlarmType().getMessage()
		);
	}
}
