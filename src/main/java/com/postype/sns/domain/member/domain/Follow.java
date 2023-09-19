package com.postype.sns.domain.member.domain;

import com.postype.sns.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "follow")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//TODO :: table에 인덱스 추가
public class Follow extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "from_member_id")
	private Member fromMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "to_member_id")
	private Member toMember;

	@Builder
	public Follow(Member fromMember, Member toMember){
		this.fromMember = fromMember;
		this.toMember = toMember;
	}

}
