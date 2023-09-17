package com.postype.sns.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
	NEW_COMMENT_ON_POST("New Comment!"),
	NEW_LIKE_ON_POST("New Like!"),
	NEW_SUBSCRIBE_ON_MEMBER("New Subscribe"),
	NEW_POST_ON_SUBSCRIBER("New Post!")
	;
	private final String message;
}
