package com.kingoftech.stream_video.api.services;

import com.kingoftech.stream_video.api.exceptions.UserException;

public interface UserService {

	public String getFeeds(String token) throws UserException;


}
