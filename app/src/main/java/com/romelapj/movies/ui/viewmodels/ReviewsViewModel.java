package com.romelapj.movies.ui.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.romelapj.movies.models.Review;
import com.romelapj.movies.models.ReviewsResponse;
import com.romelapj.movies.rest.MoviesApi;
import com.romelapj.movies.rest.RetrofitClientInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ReviewsViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MoviesApi moviesApi;

    public PublishSubject<List<Review>> actionsSubject = PublishSubject.create();

    public ReviewsViewModel() {
        moviesApi = RetrofitClientInstance.create();
    }

    public void populateReviewMovies(String idMovie) {
        compositeDisposable.add(moviesApi.getReviews(idMovie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReviewsResponse>() {
                    @Override
                    public void accept(ReviewsResponse reviewsResponse) {
                        actionsSubject.onNext(reviewsResponse.getReviews());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("Main", throwable.getMessage());
                    }
                }));
    }

}
