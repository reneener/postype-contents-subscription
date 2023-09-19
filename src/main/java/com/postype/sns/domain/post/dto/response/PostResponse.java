package com.postype.sns.domain.post.dto.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.postype.sns.domain.member.dto.response.MemberResponse;
import com.postype.sns.domain.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

	private String title;
	private String body;
	private MemberResponse member;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	@Builder
	public PostResponse(String title, String body, MemberResponse member, LocalDateTime registeredAt, LocalDateTime updatedAt) {
		this.title = title;
		this.body = body;
		this.member = member;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
	}

	public static PostResponse fromPostDto(PostDto post){
		return PostResponse.builder()
				.title(post.getTitle())
				.body(post.getBody())
				.member(MemberResponse.fromMemberDto(post.getMember()))
				.registeredAt(post.getRegisteredAt())
				.updatedAt(post.getUpdatedAt())
				.build();
	}
}
