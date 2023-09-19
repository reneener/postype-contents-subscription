package com.postype.sns.domain.post.dto;

import com.postype.sns.domain.post.domain.Comment;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private String memberId;
	private Long postId;
	private String comment;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

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
