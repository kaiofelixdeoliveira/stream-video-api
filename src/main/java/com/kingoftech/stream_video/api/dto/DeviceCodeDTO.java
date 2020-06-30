package com.kingoftech.stream_video.api.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class DeviceCodeDTO extends GrantCodeDTO {
	@NotNull
	@JsonProperty("device_code")
	String deviceCode;
	@NotNull
	@JsonProperty("user_code")
	String userCode;
	@NotNull
	@JsonProperty("authorize_link")
	String authorizeLink;
	@NotNull
	@JsonProperty("activate_link")
	String activateLink;
	@NotNull
	@JsonProperty("expires_in")
	String expiresIn;
	@NotNull
	String interval;
	@NotNull
	JsonNode user;

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAuthorizeLink() {
		return authorizeLink;
	}

	public void setAuthorizeLink(String authorizeLink) {
		this.authorizeLink = authorizeLink;
	}

	public String getActivateLink() {
		return activateLink;
	}

	public void setActivateLink(String activateLink) {
		this.activateLink = activateLink;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public JsonNode getUser() {
		return user;
	}

	public void setUser(JsonNode user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DeviceCodeDTO [deviceCode=" + deviceCode + ", userCode=" + userCode + ", authorizeLink=" + authorizeLink
				+ ", activateLink=" + activateLink + ", expiresIn=" + expiresIn + ", interval=" + interval + ", user="
				+ user + "]";
	}

}
