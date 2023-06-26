package com.postype.sns.application.contoller.dto.response;

import com.postype.sns.application.contoller.dto.AlarmDto;
import com.postype.sns.application.contoller.dto.MemberDto;
import com.postype.sns.domain.member.model.Alarm;
import com.postype.sns.domain.member.model.AlarmArgs;
import com.postype.sns.domain.member.model.AlarmType;
import com.postype.sns.domain.member.model.Member;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;
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
