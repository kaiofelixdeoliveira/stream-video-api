package com.kingoftech.stream_video.api.services;

import org.springframework.lang.NonNull;

import com.kingoftech.stream_video.api.dto.AuthorizationCodeDTO;
import com.kingoftech.stream_video.api.dto.ClientCredentialsDTO;
import com.kingoftech.stream_video.api.dto.DeviceCodeDTO;
import com.kingoftech.stream_video.api.dto.TokenDTO;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.Response;

public interface AuthenticationService {

	public Response<DeviceCodeDTO> checkDeviceGrant(String userCode, String deviceCode) throws AuthenticationException;

	public Response<Boolean> isTokenOAuth2(@NonNull TokenDTO token) throws AuthenticationException;

	public Response<ClientCredentialsDTO> generateTokenClientCredentialsGrant() throws AuthenticationException;

	public String generateUriAuthorizationCodeGrant() throws AuthenticationException;

	public String generateUriTokenGrant() throws AuthenticationException;

	public Response<DeviceCodeDTO> generateUriDeviceGrant() throws AuthenticationException;

	public Response<AuthorizationCodeDTO> swapCodeGrantToToken(String codeGrant, String state)
			throws AuthenticationException;

}
