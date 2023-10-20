package com.postype.sns.domain.post.dto;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.domain.Post;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {

	private Long id;
	private String title;
	private String body;
	private MemberDto member;
	private int price;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Builder
	public PostDto(Long id, String title, String body, MemberDto member, int price,
				   LocalDateTime registeredAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.member = member;
		this.price = price;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

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
