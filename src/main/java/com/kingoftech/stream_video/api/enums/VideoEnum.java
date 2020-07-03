package com.kingoftech.stream_video.api.enums;

public enum VideoEnum {

	
	
	// FIELDS
	UPLOAD_LENGTH("Upload-Length"),
	TUS_RESUMABLE("Tus-Resumable"), //
	UPLOAD_OFFSET("Upload-Offset"), //
	APPROACH("approach"),
	SIZE("size"),
	UPLOAD("upload"),
	UPLOAD_LINK("upload_link"),
	
	// VALUES
	VALUE_TUS_RESUMABLE("1.0.0"), //
	VALUE_UPLOAD_OFFSET("0"),//
	VALUE_APPROACH_TUS("tus");

	
	private String value;

	VideoEnum(final String value) {
		this.value = value;
	}

	VideoEnum() {
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}
