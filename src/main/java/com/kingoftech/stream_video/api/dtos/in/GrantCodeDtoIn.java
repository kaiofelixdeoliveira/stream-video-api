package com.kingoftech.stream_video.api.dtos.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrantCodeDtoIn {

	@NotNull
	@JsonProperty("access_token")
	String accessToken;
	@NotNull
	@JsonProperty("token_type")
	String tokenType;
	@NotNull
	List<String> scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "GrantCodeDTO [accessToken=" + accessToken + ", tokenType=" + tokenType + ", scope=" + scope + "]";
	}
}
