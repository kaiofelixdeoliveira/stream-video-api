package com.kingoftech.stream_video.api.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Token {

	@NotNull(message="token can't null")
	@NotEmpty(message="token can't empty")
	private String token; 
	
	public Token() {
	}
	
	public Token(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}