package com.romelapj.movies.rest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MoviesApi {

    @GET("popular?api_key=906feb0183f849429a531024630562ab")
    public Single<List<String>> getPopularMovies();

    @GET("top_rated?api_key=906feb0183f849429a531024630562ab")
    public Single<List<String>> getToRatedMovies();

}
