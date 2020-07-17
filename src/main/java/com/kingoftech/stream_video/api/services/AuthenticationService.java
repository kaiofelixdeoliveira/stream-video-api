package com.kingoftech.stream_video.api.services;

import java.io.UnsupportedEncodingException;

import org.springframework.lang.NonNull;

import com.kingoftech.stream_video.api.dtos.in.AuthorizationCodeDtoIn;
import com.kingoftech.stream_video.api.dtos.in.ClientCredentialsDtoIn;
import com.kingoftech.stream_video.api.dtos.in.DeviceCodeDtoIn;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.models.ResponseModel;

public interface AuthenticationService {

	public ResponseModel checkDeviceGrant(String userCode, String deviceCode)
			throws AuthenticationException, UnsupportedEncodingException;

	public ResponseModel isTokenOAuth2(@NonNull String token) throws AuthenticationException, UnsupportedEncodingException;

	public ResponseModel generateTokenClientCredentialsGrant() throws AuthenticationException, UnsupportedEncodingException;

	public String generateUriAuthorizationCodeGrant() throws AuthenticationException, UnsupportedEncodingException;

	public String generateUriTokenGrant() throws AuthenticationException, UnsupportedEncodingException;

	public ResponseModel generateUriDeviceGrant() throws AuthenticationException, UnsupportedEncodingException;

	public ResponseModel swapCodeGrantToToken(String codeGrant, String state)
			throws AuthenticationException, UnsupportedEncodingException;
	
	public ResponseModel generateTokenGrant() throws AuthenticationException;


}
