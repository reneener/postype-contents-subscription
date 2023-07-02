package com.postype.sns.application.controller.dto.response;

import com.postype.sns.application.controller.dto.CommentDto;
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
