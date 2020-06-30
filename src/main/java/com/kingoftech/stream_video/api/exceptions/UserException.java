package com.kingoftech.stream_video.api.exceptions;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);

	}

}
