package com.postype.sns.domain.post.model;

import com.postype.sns.application.controller.dto.PostDto;
import com.postype.sns.domain.BaseDateEntity;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.order.model.Point;
import javax.persistence.Column;
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
@Table(name = "\"post\"")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class Post extends BaseDateEntity {
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

	public static Post of(String title, String body, Member member, int price){
		Post post = new Post();
		post.setTitle(title);
		post.setBody(body);
		post.setMember(member);
		post.setPrice(new Point(price));
		return post;
	}
	public static Post of(PostDto dto){
		Post post = new Post();
		post.setId(dto.getId());
		post.setTitle(dto.getTitle());
		post.setBody(dto.getBody());
		post.setMember(Member.fromDto(dto.getMember()));
		post.setPrice(new Point(dto.getPrice()));
		return post;
	}

}
