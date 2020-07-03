package com.kingoftech.stream_video.api.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingoftech.stream_video.api.enums.AuthEnum;
import com.kingoftech.stream_video.api.enums.VideoEnum;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.exceptions.VideoException;
import com.kingoftech.stream_video.api.models.RequestModel;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.utils.ObjectMapperUtil;

@Component
public class ApiComponent {

	private static final Logger log = LoggerFactory.getLogger(ApiComponent.class);

	@Value("${id.client}")
	private void setIdClient(String idClient) {
		ApiComponent.ID_CLIENT = idClient;
	}

	@Value("${secret}")
	private void setSecret(String secret) {
		ApiComponent.SECRET = secret;
	}

	private static String SECRET;
	private static String ID_CLIENT;

	/**
	 * @param methodName
	 * @param params
	 * @param url
	 * @param typeAuthorization
	 * @param token
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 */
	public static ResponseModel apiRequest(RequestModel request) throws UnsupportedEncodingException, VideoException {

		try (CloseableHttpClient client = HttpClients.createDefault()) {

			HttpRequestBase requestBase = setRequestBase(request.getUrl(), request.getMethodName());

			HttpRequestBase requestWithHeaders = setHeaders(requestBase, request.getContentType(),
					request.getTypeAuthorization(), request.getToken(), request.getIsUpload());

			HttpRequestBase requestComplete = setParams(request.getParams(), request.getInputStream(),
					requestWithHeaders);

			CloseableHttpResponse response = client.execute(requestComplete);

			ResponseModel vimeoResponse = setResponse(response, request.getMethodName());

			return vimeoResponse;

		} catch (VideoException | ParseException | IOException e) {

			throw new VideoException("error when request api vimeo" + e);

		}

	}

	private static String basicAuth() {
		String userCredentials = ID_CLIENT + ":" + SECRET;
		return new String(Base64.getEncoder().encode(userCredentials.getBytes()));
	}

	private static HttpRequestBase setHeaders(HttpRequestBase request, //
			AuthEnum contentType, AuthEnum typeAuthorization, String token, boolean isUpload) {//

		request.addHeader(HttpHeaders.ACCEPT, AuthEnum.VALUE_HEADER_ACCEPT.getValue());

		if (request.getMethod().equals(HttpHead.METHOD_NAME)) {

			request.setHeader(VideoEnum.TUS_RESUMABLE.getValue(), VideoEnum.VALUE_TUS_RESUMABLE.getValue());

		}
		if (contentType == AuthEnum.CONTENT_TYPE_STREAM) {
			request.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE_STREAM.getValue());
			if (isUpload) {
				request.setHeader(VideoEnum.TUS_RESUMABLE.getValue(), VideoEnum.VALUE_TUS_RESUMABLE.getValue());
				request.setHeader(VideoEnum.UPLOAD_OFFSET.getValue(), VideoEnum.VALUE_UPLOAD_OFFSET.getValue());
			}

		} else if (contentType == AuthEnum.CONTENT_TYPE_JSON) {
			request.setHeader(HttpHeaders.CONTENT_TYPE, AuthEnum.VALUE_HEADER_CONTENT_TYPE_JSON.getValue());
		}

		if (AuthEnum.AUTHORIZATION_BASIC == typeAuthorization) {

			request.setHeader(HttpHeaders.AUTHORIZATION, AuthEnum.VALUE_AUTHORIZATION_BASIC.getValue() + basicAuth());
			return request;

		} else if (AuthEnum.AUTHORIZATION_BEARER == typeAuthorization && !token.isEmpty()) {
			request.setHeader(HttpHeaders.AUTHORIZATION, AuthEnum.VALUE_AUTHORIZATION_BEARER.getValue() + token);
			return request;
		}

		log.warn("typeAuthorization not setting");
		return request;
	}

	private static HttpRequestBase setRequestBase(String url, String methodName) {
		HttpRequestBase requestBase = null;

		if (methodName.equals(HttpGet.METHOD_NAME)) {
			requestBase = new HttpGet(url);
		} else if (methodName.equals(HttpPost.METHOD_NAME)) {
			requestBase = new HttpPost(url);
		} else if (methodName.equals(HttpPut.METHOD_NAME)) {
			requestBase = new HttpPut(url);
		} else if (methodName.equals(HttpDelete.METHOD_NAME)) {
			requestBase = new HttpDelete(url);
		} else if (methodName.equals(HttpPatch.METHOD_NAME)) {
			requestBase = new HttpPatch(url);
		} else if (methodName.equals(HttpHead.METHOD_NAME)) {
			requestBase = new HttpHead(url);
		}
		return requestBase;
	}

	private static HttpRequestBase setParams(JSONObject params, InputStream inputStream, HttpRequestBase request)
			throws UnsupportedEncodingException {

		HttpEntity entity = null;

		if (params != null) {

			entity = new StringEntity(params.toString());

		} else if (inputStream != null) {
			entity = new InputStreamEntity(inputStream, ContentType.MULTIPART_FORM_DATA);
		}

		if (entity != null) {
			if (request instanceof HttpPost) {
				((HttpPost) request).setEntity(entity);
			} else if (request instanceof HttpPatch) {
				((HttpPatch) request).setEntity(entity);
			} else if (request instanceof HttpPut) {
				((HttpPut) request).setEntity(entity);

			}
		}
		return request;
	}

	private static ResponseModel setResponse(CloseableHttpResponse response, String methodName) throws ParseException, IOException {

		String responseBody = "";

		int statusCode = response.getStatusLine().getStatusCode();

		log.info("status code:" + statusCode);

		if (response.getEntity() != null) {
			responseBody = EntityUtils.toString(response.getEntity());
		}

		JsonNode headers = ObjectMapperUtil.objectMapper.createObjectNode();
		JsonNode body = ObjectMapperUtil.objectMapper.createObjectNode();

		if (methodName.equals(HttpPut.METHOD_NAME) || methodName.equals(HttpDelete.METHOD_NAME)||  methodName.equals(HttpHead.METHOD_NAME)) {

			JSONObject headerJson = new JSONObject();
			for (Header header : response.getAllHeaders()) {
				headerJson.put(header.getName(), header.getValue());
			}

			headers = ObjectMapperUtil.objectMapper.readTree(headerJson.toString());
		}

		if (!responseBody.isEmpty()) {
			body = ObjectMapperUtil.objectMapper.readValue(responseBody, JsonNode.class);
		}
		return new ResponseModel(body, statusCode, headers);
	}

}
