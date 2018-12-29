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
import com.romelapj.movies.models.Trailer;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;
import com.romelapj.movies.ui.adapters.GenericItemModel;
import com.romelapj.movies.ui.adapters.ViewFactory;
import com.romelapj.movies.ui.viewmodels.TrailersViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TrailersView extends RelativeLayout implements GenericAdapterRecyclerView.ItemView<Movie>, GenericAdapterRecyclerView.OnItemClickListener {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ItemBaseDetailBinding binding;
    private TrailersViewModel trailersViewModel = new TrailersViewModel();

    private TrailersViewListener listener;


    private GenericAdapterRecyclerView adapter;

    public TrailersView(Context context) {
        super(context);
        init();
    }

    public TrailersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrailersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        binding = ItemBaseDetailBinding.inflate(LayoutInflater.from(getContext()), this, true);
        binding.textViewSectionTitle.setText(R.string.trailers);
        initAdapter();
        initRecyclerView();

        compositeDisposable.add(trailersViewModel.actionsSubject.subscribe(new Consumer<List<Trailer>>() {
            @Override
            public void accept(List<Trailer> trailers) {
                populateRecyclerView(trailers);
            }
        }));
    }

    private void populateRecyclerView(List<Trailer> trailers) {
        List<GenericItemModel> items = new ArrayList<>(trailers.size());
        int limit = trailers.size();
        for (int i = 0; i < limit; i++) {
            items.add(new GenericItemModel<>(trailers.get(i), 0));
        }
        adapter.addItems(items);
    }

    private void initRecyclerView() {
        binding.recyclerDetailItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerDetailItems.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new GenericAdapterRecyclerView(new ViewFactory<GenericAdapterRecyclerView.ItemView<Trailer>>() {
            @Override
            public GenericAdapterRecyclerView.ItemView<Trailer> getView(ViewGroup parent, int viewType) {
                TrailerView trailerView = new TrailerView(parent.getContext());
                trailerView.setItemClickListener(TrailersView.this);
                return trailerView;
            }
        });
    }

    @Override
    public void bind(Movie item, int position) {
        trailersViewModel.populateTrailerMovies(item.getId());
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

    @Override
    public void onItemClicked(GenericAdapterRecyclerView.ItemView itemView) {
        if (listener != null) {
            listener.onClickTrailer(((Trailer) itemView.getData()).getKey());
        }
    }

    @Override
    public void onItemLongClicked(GenericAdapterRecyclerView.ItemView itemView) {

    }

    public void setListener(TrailersViewListener listener) {
        this.listener = listener;
    }

    public interface TrailersViewListener {
        void onClickTrailer(String key);
    }
}
