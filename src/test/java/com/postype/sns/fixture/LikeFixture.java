package com.postype.sns.fixture;

import com.postype.sns.domain.post.model.Like;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.post.model.Post;
import java.sql.Timestamp;
import java.time.Instant;

public class LikeFixture {
	public static Like get(Member member, Post post) {
		Like like = new Like();
		like.setId(1L);
		like.setMember(member);
		like.setPost(post);
		like.setRegisteredAt(Timestamp.from(Instant.now()));
		return like;
	}
}
