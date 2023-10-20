package com.postype.sns.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequest {
	@NotBlank(message = "댓글 내용을 입력해주세요")
	private String comment;
}
