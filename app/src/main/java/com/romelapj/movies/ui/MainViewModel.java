package com.romelapj.movies.ui;

import android.util.Log;

import com.romelapj.movies.models.Movie;
import com.romelapj.movies.models.MoviesResponse;
import com.romelapj.movies.rest.MoviesApi;
import com.romelapj.movies.rest.RetrofitClientInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainViewModel {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MoviesApi moviesApi;

    PublishSubject<List<Movie>> actionsSubject = PublishSubject.create();

    public MainViewModel() {
        moviesApi = RetrofitClientInstance.create();
    }

    public void populatePopularMovies() {
        compositeDisposable.add(moviesApi.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponses) {
                        actionsSubject.onNext(moviesResponses.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("Main", throwable.getMessage());
                    }
                }));
    }

    public void populateToRateMovies() {
        compositeDisposable.add(moviesApi.getToRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) {
                        actionsSubject.onNext(moviesResponse.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("Main", throwable.getMessage());
                    }
                }));
    }


}
