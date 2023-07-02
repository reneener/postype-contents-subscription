package com.postype.sns.domain.member.model;

import com.postype.sns.domain.BaseDateEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "follow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE follow SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
//TODO :: table에 인덱스 추가
public class Follow extends BaseDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "from_member_id")
	private Member fromMember;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "to_member_id")
	private Member toMember;

	public static Follow of(Member fromMember, Member toMember){
		Follow follow = new Follow();
		follow.setFromMember(fromMember);
		follow.setToMember(toMember);
		return follow;
	}

}
