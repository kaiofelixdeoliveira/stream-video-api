package com.kingoftech.stream_video.api.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingoftech.stream_video.api.dtos.in.VideoDtoIn;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.models.Token;
import com.kingoftech.stream_video.api.services.VideoService;
import com.kingoftech.stream_video.api.utils.MimeTypeUtil;

@RestController
@RequestMapping("/video")
public class VideoController {

	private static final Logger log = LoggerFactory.getLogger(VideoController.class);
	private VideoService videoService;

	@Autowired
	VideoController(VideoService videoService) {

		this.videoService = videoService;

	}

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addVideo(VideoDtoIn videoDtoIn) throws IOException, UnsupportedEncodingException {

		if (!MimeTypeUtil.mimeTypeValid(videoDtoIn.getData())) {
			return new ResponseEntity<String>(
					new JSONObject().put("message_error", "File type not permited").toString(), HttpStatus.BAD_REQUEST);
		}

		String response = null;
		try {

			byte[] bytes = videoDtoIn.getData().getBytes();
			boolean upgradeTo1080 = true;
			response = videoService.addVideo(bytes, bytes.length, upgradeTo1080, videoDtoIn.getToken());

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (response == null) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> getMe(@RequestBody @Valid Token token)
			throws ClientProtocolException, IOException {

		ResponseModel response = videoService.getMe(token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@DeleteMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> remove(@RequestParam @NotNull long id, @RequestParam @Valid Token token)
			throws ClientProtocolException, IOException {

		ResponseModel response = videoService.removeVideo(id, token);

		if (response.getStatusCode() != HttpStatus.NO_CONTENT.value()) {

			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> getAll(@RequestBody @Valid Token token)
			throws ClientProtocolException, IOException {

		ResponseModel response = videoService.getVideos(token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@GetMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> likes(@RequestParam @NotNull long id, @RequestParam @Valid Token token)
			throws ClientProtocolException, IOException {

		ResponseModel response = videoService.likesVideo(id, token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@PutMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> like(@RequestParam @NotNull long id, @RequestParam @Valid Token token)
			throws ClientProtocolException, IOException {

		ResponseModel response = videoService.likeVideo(id, token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}

	@DeleteMapping(value = "/unlike", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> unlikeVideo(@RequestParam @NotNull long id,
			@RequestParam @Valid Token token) throws ClientProtocolException, IOException {

		ResponseModel response = videoService.likeVideo(id, token);

		if (response.getBody() == null) {
			return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.valueOf(response.getStatusCode()));

	}
}
