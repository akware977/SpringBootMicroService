package com.moviedata.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviedata.model.Rating;
import com.moviedata.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieid) {
		
		return new Rating(movieid, 1);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userid) {
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3)		
		);
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		
		return userRating;
	}
	
}
