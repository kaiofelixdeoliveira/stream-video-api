package com.kingoftech.stream_video.api.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingoftech.stream_video.api.dto.AuthorizationCodeDTO;
import com.kingoftech.stream_video.api.dto.ClientCredentialsDTO;
import com.kingoftech.stream_video.api.dto.DeviceCodeDTO;
import com.kingoftech.stream_video.api.dto.TokenDTO;
import com.kingoftech.stream_video.api.exceptions.AuthenticationException;
import com.kingoftech.stream_video.api.models.Response;
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
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@PostMapping(value = "/is/token/oauth2")
	public ResponseEntity<Response<Boolean>> isTokenOAuth2(@Valid @RequestBody TokenDTO token)
			throws AuthenticationException {

		Response<Boolean> response = authenticationService.isTokenOAuth2(token);

		if (response.getData() == null) {
			return new ResponseEntity<Response<Boolean>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<Boolean>>(response, HttpStatus.OK);

	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "/generate/token/client/credentials/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<ClientCredentialsDTO>> generateTokenClientCredentialsGrant()
			throws ClientProtocolException, IOException {

		Response<ClientCredentialsDTO> response = authenticationService.generateTokenClientCredentialsGrant();

		if (response.getData() == null) {

			return new ResponseEntity<Response<ClientCredentialsDTO>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<ClientCredentialsDTO>>(response, HttpStatus.OK);

	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/uri/authorization/code/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> generateUriAuthorizationCodeGrant()
			throws ClientProtocolException, IOException {

		Response<String> response = new Response<String>();

		response.setData(authenticationService.generateUriAuthorizationCodeGrant());

		if (response.getData() == null) {

			return new ResponseEntity<Response<String>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<String>>(response, HttpStatus.OK);
	}

	/**
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

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "generate/uri/device/grant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<DeviceCodeDTO>> generateUriDeviceGrant()
			throws ClientProtocolException, IOException {

		Response<DeviceCodeDTO> response = authenticationService.generateUriDeviceGrant();

		if (response.getData() == null) {

			return new ResponseEntity<Response<DeviceCodeDTO>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<DeviceCodeDTO>>(response, HttpStatus.OK);
	}

	/**
	 * await received values code and state from API vimeo
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@GetMapping(value = "grant/code/to/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<AuthorizationCodeDTO>> swapCodeGrantToToken(@Valid @RequestParam String code,
			@Valid @RequestParam String state) throws AuthenticationException {

		Response<AuthorizationCodeDTO> response = authenticationService.swapCodeGrantToToken(code, state);

		if (response.getData() == null) {

			return new ResponseEntity<Response<AuthorizationCodeDTO>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<AuthorizationCodeDTO>>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/check/device/code/grant")
	public ResponseEntity<Response<DeviceCodeDTO>> checkDeviceCodeGrant(
			@RequestParam(value = "user_code") String userCode, @RequestParam(value = "device_code") String deviceCode)
			throws AuthenticationException {

		Response<DeviceCodeDTO> response = authenticationService.checkDeviceGrant(userCode, deviceCode);

		if (response.getData() == null) {
			return new ResponseEntity<Response<DeviceCodeDTO>>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<Response<DeviceCodeDTO>>(response, HttpStatus.OK);

	}

}
