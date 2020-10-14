package com.moviecatalog.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.model.CatalogItem;
import com.moviecatalog.model.UserRating;
import com.moviecatalog.service.MovieInfo;
import com.moviecatalog.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private MovieInfo movieInfo;
	
	@Autowired
	private UserRatingInfo userRatingInfo;
	
	/*@Autowired
	private WebClient.Builder webClientBuilder;*/

	@RequestMapping("/{userid}")	
	public List<CatalogItem> getCatalog(@PathVariable String userid){
		
		// get all rated movie IDs
		UserRating ratings = userRatingInfo.getUserRating(userid);
		
		return ratings.getUserRating().stream().map(rating ->{
			return movieInfo.getCatalogItem(rating);
		})
		.collect(Collectors.toList());
	}
	

	
}
