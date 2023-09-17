package com.postype.sns.domain.post.dto.response;

import java.sql.Timestamp;

import com.postype.sns.domain.post.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
	private Long id;
	private String memberId;
	private Long postId;
	private String comment;
	private Timestamp registeredAt;
	private Timestamp updatedAt;

	public static CommentResponse fromDto(CommentDto comment){
		return new CommentResponse(
			comment.getId(),
			comment.getMemberId(),
			comment.getId(),
			comment.getComment(),
			comment.getRegisteredAt(),
			comment.getUpdatedAt()
		);
	}
}
