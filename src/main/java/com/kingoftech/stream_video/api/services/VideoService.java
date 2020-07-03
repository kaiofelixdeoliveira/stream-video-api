package com.kingoftech.stream_video.api.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.kingoftech.stream_video.api.exceptions.VideoException;
import com.kingoftech.stream_video.api.models.ResponseModel;
import com.kingoftech.stream_video.api.models.Token;

public interface VideoService {
	
	public ResponseModel progressUpload(String url) throws UnsupportedEncodingException, VideoException ;

	public ResponseModel unlikeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException;
	
	public ResponseModel likeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel likesVideo(long id, Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel removeVideo(long id, Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel getMe(Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel getVideos(Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel beginUploadVideo(JSONObject params, Token token) throws VideoException, UnsupportedEncodingException;

	public ResponseModel uploadVideo(File file, String uploadLinkSecure) throws VideoException, UnsupportedEncodingException, FileNotFoundException;

	public ResponseModel uploadVideo(byte[] bytes, String uploadLinkSecure) throws VideoException, UnsupportedEncodingException;

	public ResponseModel uploadVideo(InputStream inputStream, String uploadLinkSecure) throws VideoException, UnsupportedEncodingException;

	public String addVideo(File file, boolean upgradeTo1080) throws VideoException, UnsupportedEncodingException, FileNotFoundException;

	public String addVideo(byte[] bytes, long size, boolean upgradeTo1080, Token token)
			throws VideoException, UnsupportedEncodingException;

	public String addVideo(InputStream inputStream, long size, boolean upgradeTo1080, Token token)
			throws VideoException, UnsupportedEncodingException;
}