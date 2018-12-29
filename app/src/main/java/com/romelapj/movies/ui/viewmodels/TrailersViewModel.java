package com.romelapj.movies.ui.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.romelapj.movies.models.Trailer;
import com.romelapj.movies.models.TrailersResponse;
import com.romelapj.movies.rest.MoviesApi;
import com.romelapj.movies.rest.RetrofitClientInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class TrailersViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MoviesApi moviesApi;

    public PublishSubject<List<Trailer>> actionsSubject = PublishSubject.create();

    public TrailersViewModel() {
        moviesApi = RetrofitClientInstance.create();
    }

    public void populateTrailerMovies(String idMovie) {
        compositeDisposable.add(moviesApi.getTrailers(idMovie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrailersResponse>() {
                    @Override
                    public void accept(TrailersResponse trailersResponse) {
                        actionsSubject.onNext(trailersResponse.getTrailers());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("Main", throwable.getMessage());
                    }
                }));
    }

}
