package com.postype.sns.domain.member.domain.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class PageCursor<T>{
	private final CursorRequest nextCursorRequest;
	private final List<T> contents;
}