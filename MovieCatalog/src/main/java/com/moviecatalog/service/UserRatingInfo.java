package com.moviecatalog.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.model.Rating;
import com.moviecatalog.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallBackUserRating",
		commandProperties = {
				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
				@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="6"),
				@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
				@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value ="5000" )
			}
		)
	public UserRating getUserRating(String userid) {
		return restTemplate.getForObject("http://movie-data-service/ratingsdata/users/"+userid, UserRating.class);
	}
	
	public UserRating getFallBackUserRating(String userid) {
		UserRating userRating = new UserRating();
		userRating.setUserid(userid);
		userRating.setUserRating(Arrays.asList(new Rating("0", 0) ));
		
		return userRating;
	}
}
