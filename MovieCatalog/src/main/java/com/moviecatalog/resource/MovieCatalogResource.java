package com.moviecatalog.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.moviecatalog.model.CatalogItem;
import com.moviecatalog.model.Movie;
import com.moviecatalog.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userid}")
	public List<CatalogItem> getCatalog(@PathVariable String userid){
		
		// get all rated movie IDs
		UserRating ratings = restTemplate.getForObject("http://movie-data-service/ratingsdata/users/foo", UserRating.class);
		
		return ratings.getUserRating().stream().map(rating ->{
			//For each Movie ID, call movie info service and get details
			//Synchronous - RestTeamplate
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieid(), Movie.class);
			
			//ASynchronous - WebClient
			/*Movie movie = webClientBuilder.build()
				.get()
				.uri("http://localhost:8082/movies/"+rating.getMovieid())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();  ==> Convert ASynchronous to Synchronous
				
			*/
			
			//Put them all together	
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
		})
		.collect(Collectors.toList());
		
		
		
		
		
				
	}
}
