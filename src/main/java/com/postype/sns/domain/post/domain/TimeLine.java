package com.postype.sns.domain.post.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"timeline\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name= "member_id")
	private Long memberId; //delivery 되어야 할 memberId

	@Column(name= "post_id")
	private Long postId;

	@Column(name = "register_at")
	private LocalDateTime registeredAt;

	@PrePersist
	void registeredAt() {
		this.registeredAt = LocalDateTime.now();
	}

	public TimeLine(Long memberId, Long postId){
		this.memberId = Objects.requireNonNull(memberId);
		this.postId = Objects.requireNonNull(postId);
	}
}
