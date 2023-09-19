package com.postype.sns.fixture;

import com.postype.sns.domain.post.domain.Like;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.post.domain.Post;
import java.sql.Timestamp;
import java.time.Instant;

public class LikeFixture {
	public static Like get(Member member, Post post) {
		Like like = new Like(member, post);
		return like;
	}
}
