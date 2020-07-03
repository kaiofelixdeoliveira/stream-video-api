package com.kingoftech.stream_video.api.models;

import java.security.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoModel {

	String name;
	MultipartFile data;
	long size;
	String description;
	String uri;
	String type;
	String link;
	long duration;
	int width;
	String language;
	int height;
	String license;
	List<String> tags;
	List<String> categories;

	@JsonProperty(value = "created_time") // example "2020-07-02T16:43:47+00:00"
	Timestamp createdTime;

	@JsonProperty(value = "modified_time")
	Timestamp modifiedTime; // "2020-07-02T16:43:47+00:00

	@JsonProperty(value = "release_time")
	Timestamp releaseTime; // 2020-07-02T16:43:47+00:00

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Override
	public String toString() {
		return "VideoDTO [name=" + name + ", size=" + size + ", description=" + description + ", uri=" + uri + ", type="
				+ type + ", link=" + link + ", duration=" + duration + ", width=" + width + ", language=" + language
				+ ", height=" + height + ", license=" + license + ", tags=" + tags + ", categories=" + categories
				+ ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", releaseTime=" + releaseTime
				+ "]";
	}

	public MultipartFile getData() {
		return data;
	}

	public void setData(MultipartFile data) {
		this.data = data;
	}

}
