package com.kingoftech.stream_video.api.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingoftech.stream_video.api.enums.VideoEnum;

public class ResponseModel {

	private JsonNode body;
	private int statusCode;
	private JsonNode headers;

	public ResponseModel(JsonNode json, int statusCode, JsonNode headers) {
		this.body = json;
		this.statusCode = statusCode;
		this.headers = headers;
	}

	public JsonNode getBody() {
		return body;
	}

	public void setBody(JsonNode body) {
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public JsonNode getHeaders() {
		return headers;
	}

	public void setHeaders(JsonNode headers) {
		this.headers = headers;
	}

	public int getRateLimit() {

		JsonNode xRateLimitLimit = getHeaders().findValue("X-RateLimit-Limit");

		if (xRateLimitLimit != null)
			return Integer.parseInt(xRateLimitLimit.textValue());

		return 0;

	}

	public int getRateLimitRemaining() {

		JsonNode xRateLimitRemaining = getHeaders().findValue("X-RateLimit-Remaining");

		if (xRateLimitRemaining != null)
			return Integer.parseInt(xRateLimitRemaining.textValue());

		return 0;
	}

	public int getRateLimitReset() {
		JsonNode xRateLimitReset =  getHeaders().findValue("X-RateLimit-Reset");
		
		if (xRateLimitReset != null)
			return Integer.parseInt(xRateLimitReset.textValue());

		return 0;

	}

	@Override
	public String toString() {
		return "ResponseModel [body=" + body + ", statusCode=" + statusCode + ", headers=" + headers + "]";
	}

}