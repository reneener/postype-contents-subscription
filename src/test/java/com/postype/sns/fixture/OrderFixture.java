package com.postype.sns.fixture;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.domain.Order;
import com.postype.sns.domain.post.domain.Post;

public class OrderFixture {
	public static Order get(Member member, Post post) {
		Order order = Order.of(member, post);
		return order;
	}

}
