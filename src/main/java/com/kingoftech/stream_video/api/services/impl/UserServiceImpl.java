package com.kingoftech.stream_video.api.services.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kingoftech.stream_video.api.components.ApiComponent;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.exceptions.UserException;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${token.private}")
	private String tokenPrivate;

	@Value("${token.public}")
	private String tokenPublic;

	@Value("${secret}")
	private String secret;

	@Value("${id.client}")
	private String idClient;

	@Value("${base.url.vimeo}")
	private String baseUrlVimeo;

	@Override
	public String getFeeds(String token) throws UserException {

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(baseUrlVimeo + "/me/feed");

			httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
			httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.vimeo.*+json;version=3.4");
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token);

			CloseableHttpResponse response = client.execute(httpGet);

			log.info("status line: {}", response.getStatusLine());

			if (response.getStatusLine().getStatusCode() == 200) {
				String responseBody = EntityUtils.toString(response.getEntity());
				log.info("responseBody: {}", responseBody);
				return responseBody;
			}
			return null;

		} catch (Exception e) {

			throw new AuthenticationException("error when authenticable with token" + e);
		}
	}
	
	
	
	/*
	 * public String uploadVideoFile(String ticketId, File file) throws Exception {
	 * 
	 * try (CloseableHttpClient client = HttpClients.createDefault()) {
	 * 
	 * HttpPost postRequest = new HttpPost(baseUrlVimeo + "/me/videos");
	 * MultipartEntity multiPartEntity = new MultipartEntity();
	 * multiPartEntity.addPart("ticket_id", new StringBody(ticketId));
	 * multiPartEntity.addPart("chunk_id", new StringBody("0"));
	 * 
	 * FileBody fileBody = new FileBody(file, "application/octect-stream");
	 * multiPartEntity.addPart("file_data", fileBody);
	 * 
	 * postRequest.setEntity(multiPartEntity); CloseableHttpResponse response =
	 * client.execute(postRequest);
	 * 
	 * log.info("status line: {}", response.getStatusLine());
	 * 
	 * if (response.getStatusLine().getStatusCode() == 200) { String responseBody =
	 * EntityUtils.toString(response.getEntity()); log.info("responseBody: {}",
	 * responseBody); return responseBody; } return null;
	 * 
	 * } catch (Exception e) {
	 * 
	 * throw new AuthenticationException("error when authenticable with token" + e);
	 * } }
	 */
	 

}
