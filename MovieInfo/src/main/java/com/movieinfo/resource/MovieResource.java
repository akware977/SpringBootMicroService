package com.movieinfo.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieinfo.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@RequestMapping("/{movieid}")
	public Movie getMovieInfo(@PathVariable String movieid) {
		
		return new Movie(movieid,"Test Movie Name");
	}
	
}
