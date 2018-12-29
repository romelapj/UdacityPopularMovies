package com.romelapj.movies.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.romelapj.movies.R;
import com.romelapj.movies.models.Movie;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;
import com.romelapj.movies.ui.adapters.GenericItemModel;
import com.romelapj.movies.ui.adapters.ViewFactory;
import com.romelapj.movies.ui.view.HeaderDetailView;
import com.romelapj.movies.ui.view.TrailersView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailersView.TrailersViewListener {

    private final static int ITEM_HEADER = 0;
    private final static int ITEM_TRAILERS = 1;
    private GenericAdapterRecyclerView adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recyclerView = findViewById(R.id.recyclerViewDetailMovie);
        initAdapter();
        initRecyclerView();
        populateRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initAdapter() {

        adapter = new GenericAdapterRecyclerView(new ViewFactory<GenericAdapterRecyclerView.ItemView<Movie>>() {
            @Override
            public GenericAdapterRecyclerView.ItemView<Movie> getView(ViewGroup parent, int viewType) {
                GenericAdapterRecyclerView.ItemView<Movie> view;
                Context context = parent.getContext();
                switch (viewType) {
                    case ITEM_TRAILERS:
                        view = new TrailersView(context);
                        ((TrailersView) view).setListener(DetailActivity.this);
                        break;
                    default:
                        view = new HeaderDetailView(context);
                }
                return view;
            }
        });
    }

    private void populateRecyclerView() {
        Movie movie = getIntent().getParcelableExtra("movie");
        List<GenericItemModel> items = new ArrayList<>(3);
        items.add(new GenericItemModel<>(movie, ITEM_HEADER));
        items.add(new GenericItemModel<>(movie, ITEM_TRAILERS));
        adapter.addItems(items);
    }

    @Override
    public void onClickTrailer(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
