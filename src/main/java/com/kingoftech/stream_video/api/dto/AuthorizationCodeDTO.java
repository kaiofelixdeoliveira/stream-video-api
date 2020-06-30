package com.kingoftech.stream_video.api.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.JsonNode;

public class AuthorizationCodeDTO extends GrantCodeDTO {

	@NotNull
	JsonNode user;

	public JsonNode getUser() {
		return user;
	}

	public void setUser(JsonNode user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuthorizationCodeDTO [user=" + user + ", accessToken=" + accessToken + ", tokenType=" + tokenType
				+ ", scope=" + scope + ", getUser()=" + getUser() + ", getAccessToken()=" + getAccessToken()
				+ ", getTokenType()=" + getTokenType() + ", getScope()=" + getScope() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
