package com.kingoftech.stream_video.api.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingoftech.stream_video.api.dtos.in.AuthorizationCodeDtoIn;
import com.kingoftech.stream_video.api.dtos.in.ClientCredentialsDtoIn;
import com.kingoftech.stream_video.api.dtos.in.DeviceCodeDtoIn;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private AuthenticationService authenticationService;

	@Autowired
	AuthenticationController(AuthenticationService authenticationService) {

		this.authenticationService = authenticationService;

	}

	/**
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@PostMapping(value = "/is/token/oauth2")
	public ResponseEntity<ResponseModel> isTokenOAuth2(@RequestParam String token)
			throws AuthenticationException, UnsupportedEncodingException {

		ResponseModel response = authenticationService.isTokenOAuth2(token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "/generate/token/client/credentials/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> generateTokenClientCredentialsGrant()
			throws ClientProtocolException, IOException {

		ResponseModel response = authenticationService.generateTokenClientCredentialsGrant();

		if (response.getBody() == null) {

			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/uri/authorization/code/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateUriAuthorizationCodeGrant() throws ClientProtocolException, IOException {

		String response = authenticationService.generateUriAuthorizationCodeGrant();

		if (response == null) {

			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/** generate uri implicit grant
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/uri/token/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateUriTokenGrant() throws ClientProtocolException, IOException {

		String response = authenticationService.generateUriTokenGrant();

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}
	
	/** generate token implicit grant
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/token/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> generateTokenGrant() throws ClientProtocolException, IOException {

		//ResponseModel response = authenticationService.generateTokenGrant();


		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/uri/device/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> generateUriDeviceGrant() throws ClientProtocolException, IOException {

		ResponseModel response = authenticationService.generateUriDeviceGrant();

		if (response.getBody() == null) {

			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	/**
	 * await received values code and state from API vimeo
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "grant/code/to/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> swapCodeGrantToToken(@NotNull @RequestParam String code,
			@NotNull @RequestParam String state) throws AuthenticationException, UnsupportedEncodingException {

		ResponseModel response = authenticationService.swapCodeGrantToToken(code, state);

		if (response.getBody() == null) {

			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@PostMapping(value = "/check/device/code/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> checkDeviceCodeGrant(
			@RequestParam(value = "user_code") @NotNull String userCode,
			@RequestParam(value = "device_code") @NotNull String deviceCode)
			throws AuthenticationException, UnsupportedEncodingException {

		ResponseModel response = authenticationService.checkDeviceGrant(userCode, deviceCode);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

}
