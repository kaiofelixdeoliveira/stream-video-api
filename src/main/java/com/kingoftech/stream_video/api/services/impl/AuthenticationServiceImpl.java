package com.kingoftech.stream_video.api.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kingoftech.stream_video.api.components.ApiComponent;
import com.kingoftech.stream_video.api.enums.AuthEnum;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.RequestModel;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Value("${id.client}")
	private String idClient;

	@Value("${secret}")
	private String secret;

	@Value("${token.private}")
	private String tokenPrivate;

	@Value("${token.public}")
	private String tokenPublic;

	@Value("${base.url.vimeo}")
	private String baseUrlVimeo;

	public ResponseModel isTokenOAuth2(@NonNull String token)
			throws AuthenticationException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpGet.METHOD_NAME);
		request.setToken(token);
		request.setUrl(baseUrlVimeo + "/oauth/verify");
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);

		return ApiComponent.apiRequest(request);
	}

	/**
	 * Using the client credentials grant
	 * 
	 * generate token for access only data public
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public ResponseModel generateTokenClientCredentialsGrant() throws AuthenticationException, UnsupportedEncodingException {

		JSONObject params = new JSONObject();
		params.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_CLIENT_DETAILS.getValue());//
		params.put(AuthEnum.SCOPE.getValue(), AuthEnum.VALUE_SCOPE_PUBLIC.getValue());//

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPost.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/oauth/authorize/client");
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BASIC);
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setParams(params);

		return ApiComponent.apiRequest(request);
	}

	/**
	 * Using the authorization code grant
	 * 
	 * await received values code and state from API vimeo
	 * 
	 * @param codeGrant mandatory for swap to token
	 * @param state
	 * @return token if sucess
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public ResponseModel swapCodeGrantToToken(@NonNull String codeGrant, @NonNull String state)
			throws AuthenticationException, UnsupportedEncodingException {

		if (!state.equals(AuthEnum.VALUE_STATE.getValue())) {
			throw new AuthenticationException("state received not equals state send");

		}
		JSONObject params = new JSONObject();
		params.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_AUTHORIZATION_CODE.getValue());
		params.put(AuthEnum.CODE.getValue(), codeGrant);
		params.put(AuthEnum.REDIRECT_URI.getValue(), AuthEnum.VALUE_REDIRECT_URI_GRANT_CODE_TO_TOKEN.getValue());

		RequestModel request = new RequestModel();

		request.setUrl(baseUrlVimeo + "/oauth/access_token");
		request.setParams(params);
		request.setMethodName(HttpPost.METHOD_NAME);
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BASIC);
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);

		return ApiComponent.apiRequest(request);
	}

	/**
	 * Using the authorization code grant
	 * 
	 * return url for redirect user for page the vimeo, then get code authorization
	 * if the user accept
	 */
	public String generateUriAuthorizationCodeGrant() throws AuthenticationException {
		StringBuilder url = new StringBuilder();
		url.append(baseUrlVimeo)//
				.append("/oauth/authorize?") //
				.append(AuthEnum.RESPONSE_TYPE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_RESPONSE_TYPE_CODE.getValue()) //
				.append("&")//
				.append(AuthEnum.CLIENT_ID.getValue()) //
				.append("=")//
				.append(idClient) //
				.append("&")//
				.append(AuthEnum.REDIRECT_URI.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_REDIRECT_URI_GRANT_CODE_TO_TOKEN.getValue()) //
				.append("&")//
				.append(AuthEnum.STATE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_STATE.getValue()) //
				.append("&")//
				.append(AuthEnum.SCOPE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_SCOPE_FULL.getValue());//
		return url.toString();

	}

	/**
	 * Using the implicit grant
	 * 
	 * return url for redirect user for page the vimeo, then get token authorization
	 * if the user accept
	 */
	public String generateUriTokenGrant() throws AuthenticationException {
		StringBuilder url = new StringBuilder();

		url.append(baseUrlVimeo)//
				.append("/oauth/authorize?") //
				.append(AuthEnum.RESPONSE_TYPE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_RESPONSE_TYPE_TOKEN.getValue()) //
				.append("&")//
				.append(AuthEnum.CLIENT_ID.getValue()) //
				.append("=").append(idClient) //
				.append("&")//
				.append(AuthEnum.REDIRECT_URI.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_REDIRECT_URI_GRANT_TOKEN.getValue()) //
				.append("&")//
				.append(AuthEnum.STATE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_STATE.getValue()) //
				.append("&")//
				.append(AuthEnum.SCOPE.getValue()) //
				.append("=")//
				.append(AuthEnum.VALUE_SCOPE_FULL.getValue());//
		return url.toString();

	}

	public ResponseModel generateUriDeviceGrant() throws AuthenticationException, UnsupportedEncodingException {
		String url = baseUrlVimeo + "/oauth/device";

		JSONObject params = new JSONObject();
		params.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_DEVICE.getValue());//
		params.put(AuthEnum.SCOPE.getValue(), AuthEnum.VALUE_SCOPE_FULL.getValue());//

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPost.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/oauth/device");
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BASIC);
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setParams(params);

		return ApiComponent.apiRequest(request);

	}

	/**
	 * Check if code device is accept, then return data the user
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public ResponseModel checkDeviceGrant(@NonNull String userCode, @NonNull String deviceCode)
			throws AuthenticationException, UnsupportedEncodingException {

		JSONObject params = new JSONObject();
		params.put(AuthEnum.USER_CODE.getValue(), userCode);//
		params.put(AuthEnum.DEVICE_CODE.getValue(), deviceCode);//

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPost.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/oauth/device/authorize");
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BASIC);
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setParams(params);

		return ApiComponent.apiRequest(request);

	}

}
