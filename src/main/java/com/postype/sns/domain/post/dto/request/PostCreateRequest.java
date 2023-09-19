package com.postype.sns.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
	private String title;
	private String body;
	private int price;

	public PostCreateRequest(String title, String body, int price) {
		this.title = title;
		this.body = body;
		this.price = price;
	}
}
