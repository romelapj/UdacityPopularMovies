package com.romelapj.movies.models;

import com.google.gson.annotations.SerializedName;
import com.romelapj.movies.adapters.Movie;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public MoviesResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
