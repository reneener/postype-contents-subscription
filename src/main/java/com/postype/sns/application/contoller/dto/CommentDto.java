package com.postype.sns.application.contoller.dto;

import com.postype.sns.domain.order.model.Point;
import com.postype.sns.domain.post.model.Comment;
import com.postype.sns.domain.post.model.Post;
import com.postype.sns.domain.post.repository.LikeRepository;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
