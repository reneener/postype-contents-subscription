package com.postype.sns.domain.member.model.util;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageCursor<T> (
	CursorRequest nextCursorRequest,
	List<T> contents
){

}
