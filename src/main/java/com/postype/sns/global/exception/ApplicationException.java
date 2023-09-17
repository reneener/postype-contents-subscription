package com.postype.sns.global.exception;

import com.postype.sns.global.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApplicationException extends RuntimeException{

	private ErrorCode errorCode;
	private String message;

	public ApplicationException(ErrorCode errorCode){
		this.errorCode = errorCode;
		this.message = null;
	}

	@Override
	public String getMessage() {
		if(message == null){
			return errorCode.getMessage();
		}
		return String.format("%s. %s", errorCode.getMessage(), message);
	}
}
