package com.moviecatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.model.CatalogItem;
import com.moviecatalog.model.Movie;
import com.moviecatalog.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;

	//BulkHead Pattern --> create separate thread pool to avoid one call affecting other
	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem",
			threadPoolKey = "movieInfoPool",
			threadPoolProperties = {
					@HystrixProperty(name="coreSize", value="20"),
					@HystrixProperty(name="maxQueueSize", value="10")
			})
	public CatalogItem getCatalogItem(Rating rating) {
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
	}
	
	public CatalogItem getFallBackCatalogItem(Rating rating) { 
		return new CatalogItem("Movie name not found", "", 0);
	}
}
