package com.kingoftech.stream_video.api.models;

import java.io.InputStream;

import javax.validation.constraints.NotNull;

import org.json.JSONObject;

import com.kingoftech.stream_video.api.enums.AuthEnum;

public class RequestModel {

	@NotNull
	String methodName;
	JSONObject params;
	String url;
	AuthEnum typeAuthorization;
	String token;
	InputStream inputStream;
	AuthEnum contentType;
	boolean isUpload = false;

	public RequestModel() {

	}

	public RequestModel(String methodName, JSONObject params, String url, AuthEnum typeAuthorization, String token,
			InputStream inputStream, AuthEnum contentType, boolean isUpload) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.url = url;
		this.typeAuthorization = typeAuthorization;
		this.token = token;
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.isUpload = isUpload;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AuthEnum getTypeAuthorization() {
		return typeAuthorization;
	}

	public void setTypeAuthorization(AuthEnum typeAuthorization) {
		this.typeAuthorization = typeAuthorization;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public AuthEnum getContentType() {
		return contentType;
	}

	public void setContentType(AuthEnum contentType) {
		this.contentType = contentType;
	}

	public boolean setIsUpload(boolean isUpload) {
		return this.isUpload = isUpload;
	}

	public boolean getIsUpload() {
		return isUpload;
	}

	

	@Override
	public String toString() {
		return "Request [methodName=" + methodName + ", params=" + params + ", url=" + url + ", typeAuthorization="
				+ typeAuthorization + ", token=" + token + ", inputStream=" + inputStream + ", contentType="
				+ contentType + ", isUpload=" + isUpload + "]";
	}

}
