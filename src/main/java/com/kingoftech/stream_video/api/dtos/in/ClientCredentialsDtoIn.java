package com.kingoftech.stream_video.api.dtos.in;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.JsonNode;

public class ClientCredentialsDtoIn extends GrantCodeDtoIn {

	@NotNull
	JsonNode app;

	public JsonNode getApp() {
		return app;
	}

	public void setApp(JsonNode app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return "ClientCredentialsDTO [app=" + app + ", accessToken=" + accessToken + ", tokenType=" + tokenType
				+ ", scope=" + scope + ", getApp()=" + getApp() + ", getAccessToken()=" + getAccessToken()
				+ ", getTokenType()=" + getTokenType() + ", getScope()=" + getScope() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
