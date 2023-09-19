package com.postype.sns.domain.post.domain;

import com.postype.sns.domain.post.dto.PostDto;
import com.postype.sns.domain.BaseEntity;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.domain.Point;
import javax.persistence.Column;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "\"post\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String body;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "member_id")
	private Member member;

	@Column(name="price")
	private Point price;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
	@Builder
	public Post(String title, String body, Member member, Point price) {
		this.title = title;
		this.body = body;
		this.member = member;
		this.price = price;
	}

	public static Post fromDto(PostDto dto){
		return Post.builder()
				.title(dto.getTitle())
				.body(dto.getBody())
				.member(Member.fromDto(dto.getMember()))
				.price(new Point(dto.getPrice()))
				.build();
	}

	public void modify(String title, String body){
		this.title = title;
		this.body = body;
	}

}
