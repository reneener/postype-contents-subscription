package com.postype.sns.fixture;

import com.postype.sns.domain.post.domain.TimeLine;

public class TimeLineFixture {
	public static TimeLine get(Long memberId, Long postId){
		TimeLine timeLine = TimeLine.builder()
			.memberId(memberId)
			.postId(postId)
			.build();
		return timeLine;
	}
}
