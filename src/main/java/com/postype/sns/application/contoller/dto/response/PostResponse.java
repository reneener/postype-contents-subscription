package com.postype.sns.application.contoller.dto.response;

import com.postype.sns.application.contoller.dto.PostDto;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {

	private Long id;
	private String title;
	private String body;
	private MemberResponse member;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static PostResponse fromPostDto(PostDto post){
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getBody(),
			MemberResponse.fromMemberDto(post.getMember()),
			post.getRegisteredAt(),
			post.getUpdatedAt(),
			post.getDeletedAt()
		);
	}
}
