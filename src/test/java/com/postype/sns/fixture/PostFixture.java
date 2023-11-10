package com.postype.sns.fixture;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.domain.Point;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.dto.request.PostCreateRequest;
import com.postype.sns.domain.post.dto.request.PostModifyRequest;

public class PostFixture {
	public static Post get(Member member){
		return Post.builder()
				.title("title")
				.body("body")
				.member(member)
				.price(new Point(2000))
				.build();
	}
	public static Post get(){
		return Post.builder()
				.title("title")
				.body("body")
				.member(MemberFixture.get("memberId", "password"))
				.price(new Point(2000))
				.build();
	}
	public static PostModifyRequest getModifyRequest(){
		return PostModifyRequest.builder()
				.title("title")
				.body("modify")
				.price(10000)
				.build();
	}
	public static PostCreateRequest getCreateRequest(){
		return PostCreateRequest.builder()
				.title("title")
				.body("create")
				.price(1000)
				.build();
	}
}
