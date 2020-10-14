package com.moviecatalog.model;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserRating {

	private List<Rating> userRating;
	private String userid;

	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}	
	
}

