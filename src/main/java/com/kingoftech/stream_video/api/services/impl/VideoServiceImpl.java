package com.kingoftech.stream_video.api.services.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingoftech.stream_video.api.components.ApiComponent;
import com.kingoftech.stream_video.api.enums.AuthEnum;
import com.kingoftech.stream_video.api.enums.VideoEnum;
import com.kingoftech.stream_video.api.exceptions.VideoException;
import com.kingoftech.stream_video.api.models.RequestModel;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.models.Token;
import com.kingoftech.stream_video.api.services.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

	private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

	@Value("${base.url.vimeo}")
	private String baseUrlVimeo;

	@Value("${token.private}")
	private String tokenPrivate;

	public ResponseModel progressUpload(String url) throws UnsupportedEncodingException, VideoException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpHead.METHOD_NAME);
		request.setUrl(url);
		request.setIsUpload(true);

		return ApiComponent.apiRequest(request);

	}

	public ResponseModel likesVideo(long id, Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpGet.METHOD_NAME);
		request.setUrl(new StringBuffer(baseUrlVimeo).append("/me/likes/").append(id).toString());
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel likeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPut.METHOD_NAME);
		request.setUrl(new StringBuffer(baseUrlVimeo).append("/me/likes/").append(id).toString());
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel unlikeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpDelete.METHOD_NAME);
		request.setUrl(new StringBuffer(baseUrlVimeo).append("/me/likes/").append(id).toString());
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel removeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpDelete.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/videos/" + Long.toString(id));
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel getMe(Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpGet.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/me");
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel getVideos(Token token) throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpGet.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/me/videos");
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setToken(token.getToken());
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);

		return ApiComponent.apiRequest(request);
	}

	public ResponseModel beginUploadVideo(JSONObject params, @NonNull Token token)
			throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPost.METHOD_NAME);
		request.setUrl(baseUrlVimeo + "/me/videos");
		request.setContentType(AuthEnum.CONTENT_TYPE_JSON);
		request.setTypeAuthorization(AuthEnum.AUTHORIZATION_BEARER);
		request.setParams(params);
		request.setToken(token.getToken());

		return ApiComponent.apiRequest(request);//
	}

	public boolean endUploadVideo(ResponseModel response, String uploadLink) throws IOException {

		long uploadLength = new Long(response.getHeaders().findValue(VideoEnum.UPLOAD_LENGTH.getValue()).textValue())
				.longValue();
		long uploadOffset = new Long(response.getHeaders().findValue(VideoEnum.UPLOAD_OFFSET.getValue()).textValue())
				.longValue();

		while (uploadLength > uploadOffset) {

			log.info("Validing CheckSum..");
			response = progressUpload(uploadLink);
		}
		log.info("Finish");
		return true;

	}

	public ResponseModel uploadVideo(File file, String uploadLink)
			throws VideoException, UnsupportedEncodingException, FileNotFoundException {
		return uploadVideo(new FileInputStream(file), uploadLink);
	}

	public ResponseModel uploadVideo(byte[] bytes, String uploadLink)
			throws VideoException, UnsupportedEncodingException {
		return uploadVideo(new ByteArrayInputStream(bytes), uploadLink);
	}

	public ResponseModel uploadVideo(InputStream inputStream, String uploadLink)
			throws VideoException, UnsupportedEncodingException {

		RequestModel request = new RequestModel();
		request.setMethodName(HttpPatch.METHOD_NAME);
		request.setUrl(uploadLink);
		request.setInputStream(inputStream);
		request.setContentType(AuthEnum.CONTENT_TYPE_STREAM);
		request.setIsUpload(true);

		return ApiComponent.apiRequest(request);//
	}

	public String addVideo(File file, boolean upgradeTo1080)
			throws VideoException, UnsupportedEncodingException, FileNotFoundException {

		return addVideo(new FileInputStream(file), file.length(), upgradeTo1080, null);
	}

	public String addVideo(byte[] bytes, long size, boolean upgradeTo1080, Token token)
			throws VideoException, UnsupportedEncodingException {

		return addVideo(new ByteArrayInputStream(bytes), size, upgradeTo1080, token);
	}

	public String addVideo(InputStream inputStream, long size, boolean upgradeTo1080, Token token)
			throws VideoException, UnsupportedEncodingException {

		if (token == null) {
			throw new VideoException("token can't null");
		}

		try {
			JSONObject params = new JSONObject();
			params.put(VideoEnum.APPROACH.getValue(), VideoEnum.VALUE_APPROACH_TUS.getValue());
			params.put(VideoEnum.SIZE.getValue(), size);
			JSONObject paramUpload = new JSONObject();
			paramUpload.put(VideoEnum.UPLOAD.getValue(), params);

			log.info("begin upload..");
			ResponseModel response = beginUploadVideo(paramUpload, token);

			if (response.getStatusCode() == HttpStatus.OK.value()) {

				JsonNode responseLink = response.getBody().findValue(VideoEnum.UPLOAD_LINK.getValue());
				String uploadLink = responseLink.textValue();

				if (uploadLink != null) {

					log.info("Uploading...");
					uploadVideo(inputStream, uploadLink);

					ResponseModel progressUploadResponse = progressUpload(uploadLink);

					if (progressUploadResponse.getStatusCode() == HttpStatus.OK.value()) {

						if (endUploadVideo(progressUploadResponse, uploadLink))
							return response.getBody().toString();
					}

				}
			}

			throw new VideoException("Error whne upload video,response code: " + response.getStatusCode());

		} catch (Exception e) {
			throw new VideoException("Error when Add Video" + e);
		}

	}

}
