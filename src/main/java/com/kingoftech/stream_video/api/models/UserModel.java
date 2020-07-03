package com.kingoftech.stream_video.api.models;

import java.security.Timestamp;

public class UserModel {

	String uri;
	String name;
	String link;
	String location;
	String gender;
	String bio;
	String short_bio;
	Timestamp created_time; // "2020-06-25T13:49:25+00:00",

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getShort_bio() {
		return short_bio;
	}

	public void setShort_bio(String short_bio) {
		this.short_bio = short_bio;
	}

	public Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}

	@Override
	public String toString() {
		return "UserDTO [uri=" + uri + ", name=" + name + ", link=" + link + ", location=" + location + ", gender="
				+ gender + ", bio=" + bio + ", short_bio=" + short_bio + ", created_time=" + created_time + "]";
	}

}
