package com.postype.sns.domain.post.dto;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.domain.Post;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

	private Long id;
	private String title;
	private String body;
	private MemberDto member;
	private int price;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;


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
