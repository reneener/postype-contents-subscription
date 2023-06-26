package com.postype.sns.domain.member.model;

import com.postype.sns.domain.BaseDateEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"alarm\"", indexes = {
	@Index(name = "member_id_idx", columnList = "member_id")
})
@Getter
@Setter
@TypeDef(name = "json", typeClass = JsonType.class)
@NoArgsConstructor
@SQLDelete(sql = "UPDATE alarm SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Alarm extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//알람을 받는 사람에 대한 정보
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@Enumerated(EnumType.STRING)
	private AlarmType alarmType;
	@Type(type = "json")
	@Column(columnDefinition = "json")
	private AlarmArgs alarmArgs;

	public static Alarm of(Member member, AlarmType type, AlarmArgs args){
		Alarm alarm = new Alarm();
		alarm.setMember(member);
		alarm.setAlarmType(type);
		alarm.setAlarmArgs(args);
		return alarm;
	}

}
