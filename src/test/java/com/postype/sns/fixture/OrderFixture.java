package com.postype.sns.fixture;

import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.order.model.Order;
import com.postype.sns.domain.post.model.Post;

public class OrderFixture {
	public static Order get(Member member, Post post) {
		Order order = Order.of(member, post);
		return order;
	}

}
