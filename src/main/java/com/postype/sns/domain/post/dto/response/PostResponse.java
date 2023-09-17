package com.postype.sns.domain.post.dto.response;

import java.sql.Timestamp;

import com.postype.sns.domain.member.dto.response.MemberResponse;
import com.postype.sns.domain.post.dto.PostDto;
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

	public static PostResponse fromPostDto(PostDto post){
		return new PostResponse(
			post.getId(),
			post.getTitle(),
			post.getBody(),
			MemberResponse.fromMemberDto(post.getMember()),
			post.getRegisteredAt(),
			post.getUpdatedAt()
		);
	}
}
