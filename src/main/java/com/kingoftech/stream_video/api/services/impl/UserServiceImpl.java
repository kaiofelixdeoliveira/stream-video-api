package com.kingoftech.stream_video.api.services.impl;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.exceptions.UserException;
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

}
