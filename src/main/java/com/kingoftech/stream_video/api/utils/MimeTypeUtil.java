package com.kingoftech.stream_video.api.utils;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class MimeTypeUtil {
	
	public static boolean mimeTypeValid(MultipartFile fileWrapper) {
	
	MediaType mediaType = MediaType.parseMediaType(fileWrapper.getContentType());
	
	if("video".equals(mediaType.getType()))return true;
	
	return false;
	
	}
}
