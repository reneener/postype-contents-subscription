package com.postype.sns.domain.post.model;

import com.postype.sns.domain.BaseDateEntity;
import com.postype.sns.domain.member.model.Member;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"comment\"", indexes = {
	@Index(name = "post_id_idx", columnList = "post_id")
})
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Comment extends BaseDateEntity {
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
	public static Comment of(Member member, Post post, String comment){
		Comment commentEntity = new Comment();
		commentEntity.setMember(member);
		commentEntity.setPost(post);
		commentEntity.setComment(comment);
		return commentEntity;
	}

}
