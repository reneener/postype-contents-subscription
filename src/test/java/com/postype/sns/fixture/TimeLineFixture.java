package com.postype.sns.fixture;

import com.postype.sns.domain.post.model.TimeLine;

public class TimeLineFixture {
	public static TimeLine get(Long memberId, Long postId){
		TimeLine timeLine = TimeLine.builder()
			.memberId(memberId)
			.postId(postId)
			.build();
		return timeLine;
	}
}
