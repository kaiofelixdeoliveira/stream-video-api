package com.kingoftech.stream_video.api.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kingoftech.stream_video.api.dto.AuthorizationCodeDTO;
import com.kingoftech.stream_video.api.dto.ClientCredentialsDTO;
import com.kingoftech.stream_video.api.dto.DeviceCodeDTO;
import com.kingoftech.stream_video.api.dto.TokenDTO;
import com.kingoftech.stream_video.api.enums.AuthEnum;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.Response;
import com.kingoftech.stream_video.api.services.AuthenticationService;
import com.kingoftech.stream_video.api.util.ObjectMapperUtil;

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

	public Response<Boolean> isTokenOAuth2(@NonNull TokenDTO token) throws AuthenticationException {

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(baseUrlVimeo + "/oauth/verify");

			httpGet.setHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());
			httpGet.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE.getValue());
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.getToken());

			CloseableHttpResponse response = client.execute(httpGet);
			String responseBody = EntityUtils.toString(response.getEntity());
			log.info("status line: {}", response.getStatusLine());

			Response<Boolean> reponseApi = new Response<Boolean>();
			if (response.getStatusLine().getStatusCode() == 200) {
				reponseApi.setData(true);
				return reponseApi;
			}
			log.error("status line: {}", response.getStatusLine());
			log.error("status line: {}", responseBody);

			reponseApi.setError(ObjectMapperUtil.objectMapper.readTree(responseBody));
			return reponseApi;
		} catch (Exception e) {

			throw new AuthenticationException("error when check token type OAuth2" + e);

		}
	}

	/**
	 * Using the client credentials grant
	 * 
	 * generate token for access only data public
	 */
	public Response<ClientCredentialsDTO> generateTokenClientCredentialsGrant() throws AuthenticationException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(baseUrlVimeo + "/oauth/authorize/client");

			JSONObject json = new JSONObject();
			json.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_CLIENT_DETAILS.getValue());//
			json.put(AuthEnum.SCOPE.getValue(), AuthEnum.VALUE_SCOPE_PRIVATE.getValue());//

			StringEntity entity = new StringEntity(json.toString());

			httpPost.setEntity(entity);
			httpPost.setHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE.getValue());
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "basic " + basicAuth());

			CloseableHttpResponse response = client.execute(httpPost);

			log.info("status line: {}", response.getStatusLine());
			String responseBody = EntityUtils.toString(response.getEntity());
			Response<ClientCredentialsDTO> reponseApi = new Response<ClientCredentialsDTO>();
			if (response.getStatusLine().getStatusCode() == 200) {

				reponseApi.setData(ObjectMapperUtil.objectMapper.readValue(responseBody, ClientCredentialsDTO.class));
				log.info("responseBody: {}", reponseApi);
				return reponseApi;
			}
			log.error("status line: {}", response.getStatusLine());
			log.error("status line: {}", responseBody);

			reponseApi.setError(ObjectMapperUtil.objectMapper.readTree(responseBody));
			return reponseApi;

		} catch (Exception e) {

			throw new AuthenticationException("error when genetate Token using the client credentials grant" + e);

		}
	}

	/**
	 * Using the authorization code grant
	 * 
	 * await received values code and state from API vimeo
	 * 
	 * @param codeGrant mandatory for swap to token
	 * @param state
	 * @return token if sucess
	 * @throws IOException
	 */
	public Response<AuthorizationCodeDTO> swapCodeGrantToToken(@NonNull String codeGrant, @NonNull String state)
			throws AuthenticationException {

		if (!state.equals(AuthEnum.VALUE_STATE.getValue())) {
			throw new AuthenticationException("state received not equals state send");

		}
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(baseUrlVimeo + "/oauth/access_token");

			JSONObject json = new JSONObject();
			json.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_AUTHORIZATION_CODE.getValue());
			json.put(AuthEnum.CODE.getValue(), codeGrant);
			json.put(AuthEnum.REDIRECT_URI.getValue(), AuthEnum.VALUE_REDIRECT_URI_GRANT_CODE_TO_TOKEN.getValue());

			StringEntity entity = new StringEntity(json.toString());

			httpPost.setEntity(entity);

			httpPost.setHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE.getValue());
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "basic " + basicAuth());

			log.info("params:" + EntityUtils.toString(httpPost.getEntity()));
			CloseableHttpResponse response = client.execute(httpPost);

			String responseBody = EntityUtils.toString(response.getEntity());
			Response<AuthorizationCodeDTO> responseApi = new Response<AuthorizationCodeDTO>();

			if (response.getStatusLine().getStatusCode() == 200) {

				responseApi.setData(ObjectMapperUtil.objectMapper.readValue(responseBody, AuthorizationCodeDTO.class));
				log.info("responseBody: {}", responseApi);

				return responseApi;
			}
			log.error("status line: {}", response.getStatusLine());
			log.error("status line: {}", responseBody);
			responseApi.setError(ObjectMapperUtil.objectMapper.readTree(responseBody));
			return responseApi;

		} catch (Exception e) {

			throw new AuthenticationException(
					"error when swap CodeGrant To Token using the authorization code grant" + e);

		}

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
				.append(AuthEnum.VALUE_SCOPE_PRIVATE.getValue());//
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
				.append(AuthEnum.VALUE_SCOPE_PRIVATE.getValue());//
		return url.toString();

	}

	

	public Response<DeviceCodeDTO> generateUriDeviceGrant() throws AuthenticationException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(baseUrlVimeo + "/oauth/device");

			JSONObject json = new JSONObject();
			json.put(AuthEnum.GRANT_TYPE.getValue(), AuthEnum.VALUE_GRANT_TYPE_DEVICE.getValue());//
			json.put(AuthEnum.SCOPE.getValue(), AuthEnum.VALUE_SCOPE_PRIVATE.getValue());//

			StringEntity entity = new StringEntity(json.toString());
			httpPost.setEntity(entity);
			httpPost.setHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE.getValue());
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "basic " + basicAuth());

			CloseableHttpResponse response = client.execute(httpPost);
			String responseBody = EntityUtils.toString(response.getEntity());

			log.info("status line: {}", response.getStatusLine());

			Response<DeviceCodeDTO> responseApi = new Response<DeviceCodeDTO>();
			if (response.getStatusLine().getStatusCode() == 200) {

				responseApi.setData(ObjectMapperUtil.objectMapper.readValue(responseBody, DeviceCodeDTO.class));
				log.info("responseBody: {}", responseApi.toString());
				return responseApi;
			}
			log.error("status line: {}", response.getStatusLine());
			log.error("status line: {}", responseBody.toString());
			responseApi.setError(ObjectMapperUtil.objectMapper.readTree(responseBody));
			return responseApi;

		} catch (Exception e) {

			throw new AuthenticationException("error when generate uri device Grant" + e);

		}

	}
	
	/**
	 * Check if code device is accept, then return data the user
	 */
	public Response<DeviceCodeDTO> checkDeviceGrant(@NonNull String userCode, @NonNull String deviceCode) throws AuthenticationException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(baseUrlVimeo + "/oauth/device/authorize");

			JSONObject json = new JSONObject();
			json.put(AuthEnum.USER_CODE.getValue(), userCode);//
			json.put(AuthEnum.DEVICE_CODE.getValue(), deviceCode);//

			StringEntity entity = new StringEntity(json.toString());
			httpPost.setEntity(entity);
			httpPost.setHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE.getValue());
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "basic " + basicAuth());

			CloseableHttpResponse response = client.execute(httpPost);
			String responseBody = EntityUtils.toString(response.getEntity());

			log.info("status line: {}", response.getStatusLine());

			Response<DeviceCodeDTO> responseApi = new Response<DeviceCodeDTO>();
			if (response.getStatusLine().getStatusCode() == 200) {

				responseApi.setData(ObjectMapperUtil.objectMapper.readValue(responseBody, DeviceCodeDTO.class));
				log.info("responseBody: {}", responseBody);
				return responseApi;
			}
			log.error("status line: {}", response.getStatusLine());
			log.error("status line: {}", responseBody);
			responseApi.setError(ObjectMapperUtil.objectMapper.readTree(responseBody));
			return responseApi;

		} catch (Exception e) {

			throw new AuthenticationException("error when check device code" + e);

		}

	}

	public String basicAuth() {
		String userCredentials = idClient + ":" + secret;
		return new String(Base64.getEncoder().encode(userCredentials.getBytes()));
	}

}
