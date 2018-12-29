package com.romelapj.movies.rest;

import com.romelapj.movies.models.MoviesResponse;
import com.romelapj.movies.models.ReviewsResponse;
import com.romelapj.movies.models.TrailersResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MoviesApi {

    @GET("popular?api_key=KEY")
    Single<MoviesResponse> getPopularMovies();

    @GET("top_rated?api_key=KEY")
    Single<MoviesResponse> getToRatedMovies();

    @GET("{Id}/videos?api_key=KEY")
    Single<TrailersResponse> getTrailers(@Path("Id") String id);

    @GET("{Id}/reviews?api_key=KEY")
    Single<ReviewsResponse> getReviews(@Path("Id") String id);

}
