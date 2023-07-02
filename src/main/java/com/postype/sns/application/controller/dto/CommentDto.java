package com.postype.sns.application.controller.dto;

import com.postype.sns.domain.post.model.Comment;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private String memberId;
	private Long postId;
	private String comment;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static CommentDto fromEntity(Comment comment){
		return new CommentDto(
			comment.getId(),
			comment.getMember().getMemberId(),
			comment.getPost().getId(),
			comment.getComment(),
			comment.getRegisteredAt(),
			comment.getUpdatedAt(),
			comment.getDeletedAt()
		);
	}
}
