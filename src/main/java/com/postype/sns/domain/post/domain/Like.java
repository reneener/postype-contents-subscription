package com.postype.sns.domain.post.domain;

import com.postype.sns.domain.BaseEntity;
import com.postype.sns.domain.member.domain.Member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Like extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "member_id")
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "post_id")
	private Post post;

	public static Like of(Member member, Post post){
		Like like = new Like();
		like.setMember(member);
		like.setPost(post);
		return like;
	}

}
