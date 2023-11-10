package com.postype.sns.domain.member.domain.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class CursorRequest{
	private Long key;
	private int size;

	public static final Long DEFAULT_KEY = -1L;

	public CursorRequest(Long key, int size) {
		this.key = key;
		this.size = size;
	}

	public Boolean hasKey(){
		return this.key != null;
	}

	public CursorRequest next(Long key){
		return new CursorRequest(key, size);
	}
}