package com.postype.sns.fixture;

import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.order.model.Point;
import com.postype.sns.domain.post.model.Post;

public class PostFixture {
	public static Post get(String memberId, Long postId, Long id){
		Member member = new Member();
		member.setId(id);
		member.setMemberId(memberId);

		Post post = new Post();
		post.setMember(member);
		post.setId(postId);
		post.setPrice(new Point(2000));
		return post;
	}
}
