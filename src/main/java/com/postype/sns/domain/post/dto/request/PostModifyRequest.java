package com.postype.sns.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
public class PostModifyRequest {
	private String title;
	private String body;
	private int price;

	@Builder
	public PostModifyRequest(String title, String body, int price) {
		this.title = title;
		this.body = body;
		this.price = price;
	}
}
