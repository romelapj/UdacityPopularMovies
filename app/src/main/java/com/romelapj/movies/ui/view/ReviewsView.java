package com.romelapj.movies.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.romelapj.movies.R;
import com.romelapj.movies.databinding.ItemBaseDetailBinding;
import com.romelapj.movies.database.Movie;
import com.romelapj.movies.models.Review;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;
import com.romelapj.movies.ui.adapters.GenericItemModel;
import com.romelapj.movies.ui.adapters.ViewFactory;
import com.romelapj.movies.ui.viewmodels.ReviewsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ReviewsView extends RelativeLayout implements GenericAdapterRecyclerView.ItemView<Movie> {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ItemBaseDetailBinding binding;
    private ReviewsViewModel reviewsViewModel = new ReviewsViewModel();


    private GenericAdapterRecyclerView adapter;

    public ReviewsView(Context context) {
        super(context);
        init();
    }

    public ReviewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReviewsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        binding = ItemBaseDetailBinding.inflate(LayoutInflater.from(getContext()), this, true);
        binding.textViewSectionTitle.setText(R.string.reviews);
        initAdapter();
        initRecyclerView();

        compositeDisposable.add(reviewsViewModel.actionsSubject.subscribe(new Consumer<List<Review>>() {
            @Override
            public void accept(List<Review> reviews) {
                populateRecyclerView(reviews);
            }
        }));
    }

    private void populateRecyclerView(List<Review> reviews) {
        int limit = reviews.size();
        List<GenericItemModel> items = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            items.add(new GenericItemModel<>(reviews.get(i), 0));
        }
        adapter.addItems(items);
    }

    private void initRecyclerView() {
        binding.recyclerDetailItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerDetailItems.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new GenericAdapterRecyclerView(new ViewFactory<GenericAdapterRecyclerView.ItemView<Review>>() {
            @Override
            public GenericAdapterRecyclerView.ItemView<Review> getView(ViewGroup parent, int viewType) {
                return new ReviewView(parent.getContext());
            }
        });
    }

    @Override
    public void bind(Movie item, int position) {
        reviewsViewModel.populateReviewMovies(item.getId());
    }

    @Override
    public void setItemClickListener(GenericAdapterRecyclerView.OnItemClickListener onItemClickListener) {

    }

    @Override
    public int getIdForClick() {
        return 0;
    }

    @Override
    public Movie getData() {
        return null;
    }
}
