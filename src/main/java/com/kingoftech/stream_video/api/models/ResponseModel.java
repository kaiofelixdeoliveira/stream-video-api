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

		String xRateLimitLimit = getHeaders().findValue("X-RateLimit-Limit").textValue();

		if (xRateLimitLimit != null)
			return Integer.parseInt(xRateLimitLimit);

		return 0;

	}

	public int getRateLimitRemaining() {

		String xRateLimitRemaining = getHeaders().findValue("X-RateLimit-Remaining").textValue();

		if (xRateLimitRemaining != null)
			return Integer.parseInt(xRateLimitRemaining);

		return 0;
	}

	public String getRateLimitReset() {
		return getHeaders().findValue("X-RateLimit-Reset").textValue();

	}

	@Override
	public String toString() {
		return "ResponseModel [body=" + body + ", statusCode=" + statusCode + ", headers=" + headers + "]";
	}

}