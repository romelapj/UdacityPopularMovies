package com.romelapj.movies.rest;

import com.romelapj.movies.models.MoviesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MoviesApi {

    @GET("popular?api_key=API_KEY")
    public Single<MoviesResponse> getPopularMovies();

    @GET("top_rated?api_key=API_KEY")
    public Single<MoviesResponse> getToRatedMovies();

}
