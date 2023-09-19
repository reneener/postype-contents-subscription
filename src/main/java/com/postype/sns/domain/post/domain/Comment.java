package com.postype.sns.domain.post.domain;

import com.postype.sns.domain.BaseEntity;
import com.postype.sns.domain.member.domain.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"comment\"", indexes = {
	@Index(name = "post_id_idx", columnList = "post_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "post_id")
	private Post post;

	@Column(name = "comment")
	private String comment;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Comment(Member member, Post post, String comment) {
		this.member = member;
		this.post = post;
		this.comment = comment;
	}

}
