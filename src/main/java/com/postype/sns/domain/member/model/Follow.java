package com.postype.sns.domain.member.model;

import com.postype.sns.application.contoller.dto.MemberDto;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "from_member_id")
	private Member fromMember;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "to_member_id")
	private Member toMember;
	@Column(name = "register_at")
	private Timestamp registeredAt;
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	@Column(name = "deleted_at")
	private Timestamp deletedAt;
	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}
	@PreUpdate
	void updatedAt() {
		this.updatedAt = Timestamp.from(Instant.now());
	}
	public static Follow of(Member fromMember, Member toMember){
		Follow follow = new Follow();
		follow.setFromMember(fromMember);
		follow.setToMember(toMember);
		return follow;
	}

}
