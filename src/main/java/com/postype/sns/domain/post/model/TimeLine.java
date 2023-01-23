package com.postype.sns.domain.post.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"timeline\"")
@Getter
@NoArgsConstructor
@Where(clause = "deleted_at is NULL")
public class TimeLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name= "member_id")
	private Long memberId; //delivery 되어야 할 memberId
	@Column(name= "post_id")
	private Long postId;
	@Column(name = "register_at")
	private Timestamp registeredAt;
	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}

	@Builder
	public TimeLine(Long id, Long memberId, Long postId){
		this.id = id;
		this.memberId = Objects.requireNonNull(memberId);
		this.postId = Objects.requireNonNull(postId);
	}
}
