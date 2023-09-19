package com.postype.sns.domain.post.domain;

import com.postype.sns.domain.BaseEntity;
import com.postype.sns.domain.member.domain.Member;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"likes\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted is NULL")
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

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public Like(Member member, Post post){
		this.member = member;
		this.post = post;
	}



}
