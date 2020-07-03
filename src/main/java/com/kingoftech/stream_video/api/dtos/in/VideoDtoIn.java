package com.kingoftech.stream_video.api.dtos.in;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.kingoftech.stream_video.api.models.Token;

public class VideoDtoIn {

	@NotNull
	Token token;
	@NotNull
	MultipartFile data;
	String name;
	String description;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public MultipartFile getData() {
		return data;
	}

	public void setData(MultipartFile data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "VideoDtoIn [token=" + token + ", data=" + data + ", name=" + name + ", description=" + description
				+ "]";
	}

}
