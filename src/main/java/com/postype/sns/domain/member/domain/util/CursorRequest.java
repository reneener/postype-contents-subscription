package com.postype.sns.domain.member.domain.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class CursorRequest{
	private final Long key;
	private final int size;

	public static final Long DEFAULT_KEY = -1L;

	public Boolean hasKey(){
		return this.key != null;
	}

	public CursorRequest next(Long key){
		return new CursorRequest(key, size);
	}
}