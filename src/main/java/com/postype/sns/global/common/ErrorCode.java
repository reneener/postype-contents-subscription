package com.postype.sns.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
	DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, "Member name is duplicated"),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not founded"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
	MEMBER_IS_SAME(HttpStatus.BAD_REQUEST, "fromMember and toMember is the same"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error"),
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurs in database"),
	ALREADY_LIKE(HttpStatus.CONFLICT, "Member already liked post"),
	ALREADY_ORDER(HttpStatus.CONFLICT, "Post already ordered by member"),
	ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connecting alarm occurs error")

	;

	private final HttpStatus status;
	private final String message;

}
