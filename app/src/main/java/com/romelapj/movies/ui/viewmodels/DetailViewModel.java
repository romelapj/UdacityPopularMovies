package com.romelapj.movies.ui.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.romelapj.movies.database.AppDatabase;
import com.romelapj.movies.database.Movie;

public class DetailViewModel extends AndroidViewModel {


    private final AppDatabase database;

    public DetailViewModel(Application application) {
        super(application);
        this.database = AppDatabase.getInstance(this.getApplication());
    }

    public LiveData<Movie> getMovie(String id) {
        return database.movieDao().loadMovieById(id);
    }

}
