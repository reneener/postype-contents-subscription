package com.postype.sns.application.contoller.dto.response;

import com.postype.sns.application.contoller.dto.CommentDto;
import com.postype.sns.domain.post.model.Comment;
import java.sql.Timestamp;
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
	private Timestamp deletedAt;

	public static CommentResponse fromDto(CommentDto comment){
		return new CommentResponse(
			comment.getId(),
			comment.getMemberId(),
			comment.getId(),
			comment.getComment(),
			comment.getRegisteredAt(),
			comment.getUpdatedAt(),
			comment.getDeletedAt()
		);
	}
}
