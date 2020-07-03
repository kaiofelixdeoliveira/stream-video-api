package com.kingoftech.stream_video.api.exceptions;

public class VideoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VideoException(String message) {
		super(message);
	}

	public VideoException(String message, Throwable cause) {
		super(message, cause);

	}

}
