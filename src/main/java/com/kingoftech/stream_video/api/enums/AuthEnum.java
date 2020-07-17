package com.kingoftech.stream_video.api.enums;

public enum AuthEnum {

	// ENUM
	AUTHORIZATION_BASIC,
	AUTHORIZATION_BEARER,
	CONTENT_TYPE_STREAM,
	CONTENT_TYPE_JSON,
	
	// FIELD

	USER_CODE("user_code"),
	DEVICE_CODE("device_code"),
	CODE("code"), //
	AUTHORIZATION("authorization"), //
	CLIENT_ID("client_id"), //
	REDIRECT_URI("redirect_uri"), //
	STATE("state"), //
	SCOPE("scope"), //
	RESPONSE_TYPE("response_type"), //
	GRANT_TYPE("grant_type"), //

	// FIELDS VALUES

	/*
	 * AUTHORIZATION
	 */
	VALUE_AUTHORIZATION_BASIC("basic "), //
	VALUE_AUTHORIZATION_BEARER("bearer "), //
	/*
	 * REDIRECT_URI
	 */
	VALUE_REDIRECT_URI_GRANT_CODE_TO_TOKEN("https://stream-video-api.herokuapp.com/auth/grant/code/to/token"), //
	VALUE_REDIRECT_URI_GRANT_TOKEN("https://stream-video-api.herokuapp.com/auth/grant/token"), //

	/*
	 * HEADER
	 */
	
	VALUE_HEADER_ACCEPT("application/vnd.vimeo.*+json;version=3.4"), //
	VALUE_HEADER_CONTENT_TYPE_JSON("application/json"), //
	VALUE_HEADER_CONTENT_TYPE_STREAM("application/offset+octet-stream"),

	/*
	 * STATE
	 */
	VALUE_STATE("state_return"), //

	/*
	 * SCOPE
	 */
	VALUE_SCOPE_PUBLIC("public"), //
	VALUE_SCOPE_PRIVATE("private"), //
	VALUE_SCOPE_CREATE("create"),//
	VALUE_SCOPE_EDIT("edit"),//
	VALUE_SCOPE_UPLOAD("upload"),//
	VALUE_SCOPE_FULL("private create edit upload delete public"),
	/*
	 * RESPONSE TYPE
	 */
	VALUE_RESPONSE_TYPE_CODE("code"), //
	VALUE_RESPONSE_TYPE_TOKEN("token"), //

	/*
	 * GRANT TYPE
	 */

	VALUE_GRANT_TYPE_DEVICE("device_grant"), //
	VALUE_GRANT_TYPE_CLIENT_DETAILS("client_credentials"), //
	VALUE_GRANT_TYPE_AUTHORIZATION_CODE("authorization_code");//

	private String value;

	AuthEnum(final String value) {
		this.value = value;
	}

	AuthEnum() {
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}