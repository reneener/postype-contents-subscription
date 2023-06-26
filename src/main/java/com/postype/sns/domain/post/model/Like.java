package com.postype.sns.domain.post.model;

import com.postype.sns.domain.member.model.Member;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"likes\"")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE likes SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "member_id")
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "post_id")
	private Post post;
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

	public static Like of(Member member, Post post){
		Like like = new Like();
		like.setMember(member);
		like.setPost(post);
		return like;
	}

}
