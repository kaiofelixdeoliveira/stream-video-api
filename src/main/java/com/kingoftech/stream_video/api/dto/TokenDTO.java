package com.kingoftech.stream_video.api.dto;

import javax.validation.constraints.NotNull;

public class TokenDTO {

	@NotNull
	private String token; 
	
	public TokenDTO() {
	}
	
	public TokenDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}