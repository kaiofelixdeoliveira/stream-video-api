package com.kingoftech.stream_video.api.controllers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingoftech.stream_video.api.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@Autowired
	UserController(UserService userService) {

		this.userService = userService;

	}

	@GetMapping(value = "/me/feed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFeeds(@RequestParam String token) throws ClientProtocolException, IOException {

		String response = userService.getFeeds(token);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	
}
