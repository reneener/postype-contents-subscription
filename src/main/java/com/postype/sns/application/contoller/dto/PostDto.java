package com.postype.sns.application.contoller.dto;

import com.postype.sns.domain.post.model.Post;
import com.postype.sns.domain.post.repository.LikeRepository;
import com.postype.sns.domain.order.model.Point;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class PostDto {

	private Long id;
	private String title;
	private String body;
	private MemberDto member;
	private int price;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;


	public static PostDto fromPost(Post post){
		return new PostDto(
			post.getId(),
			post.getTitle(),
			post.getBody(),
			MemberDto.fromEntity(post.getMember()),
			post.getPrice().getValue(),
			post.getRegisteredAt(),
			post.getUpdatedAt(),
			post.getDeletedAt()
		);
	}
}
