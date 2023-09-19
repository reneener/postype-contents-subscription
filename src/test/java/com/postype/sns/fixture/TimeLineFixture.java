package com.postype.sns.fixture;

import com.postype.sns.domain.post.domain.TimeLine;

public class TimeLineFixture {
	public static TimeLine get(Long memberId, Long postId){
		return new TimeLine(memberId, postId);
	}
}
