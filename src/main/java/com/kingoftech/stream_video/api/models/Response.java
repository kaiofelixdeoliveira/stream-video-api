package com.kingoftech.stream_video.api.models;

import com.fasterxml.jackson.databind.JsonNode;

public class Response<T> {

	private T data;

	private JsonNode error;

	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public JsonNode getError() {
		return error;
	}

	public void setError(JsonNode error) {
		this.error = error;
	}

}