package com.romelapj.movies.rest;

import com.romelapj.movies.models.MoviesResponse;
import com.romelapj.movies.models.TrailersResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MoviesApi {

    @GET("popular?api_key=906feb0183f849429a531024630562ab")
    Single<MoviesResponse> getPopularMovies();

    @GET("top_rated?api_key=906feb0183f849429a531024630562ab")
    Single<MoviesResponse> getToRatedMovies();

    @GET("{Id}/videos?api_key=906feb0183f849429a531024630562ab")
    Single<TrailersResponse> getTrailers(@Path("Id") String id);


//    @GET("top_rated?api_key=906feb0183f849429a531024630562ab")
//    Single<MoviesResponse> getVideodMovie();
//    https://api.themoviedb.org/3/movie/238/videos?api_key=906feb0183f849429a531024630562ab
//
//    Single<MoviesResponse> getVideodMovie();
//    https://api.themoviedb.org/3/movie/372058/reviews?api_key=906feb0183f849429a531024630562ab

}
