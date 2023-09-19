package com.postype.sns.domain.member.domain;

import com.postype.sns.domain.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
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
import javax.persistence.Table;

import lombok.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"alarm\"", indexes = {
	@Index(name = "member_id_idx", columnList = "member_id")
})
@Getter
@TypeDef(name = "json", typeClass = JsonType.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private AlarmType alarmType;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private AlarmArgs alarmArgs;

	@Builder
	public Alarm(Member member, AlarmType type, AlarmArgs args){
		this.member = member;
		this.alarmType = type;
		this.alarmArgs = args;
	}
}
