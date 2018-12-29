package com.romelapj.movies.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.romelapj.movies.R;
import com.romelapj.movies.adapters.MovieViewAdapter;
import com.romelapj.movies.database.Movie;
import com.romelapj.movies.ui.viewmodels.MainViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements MovieViewAdapter.ItemClickListener {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MainViewModel viewModel;

    MovieViewAdapter adapter;

    private Spinner spinnerSort;
    private RecyclerView recyclerViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerSort = findViewById(R.id.spinnerSort);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        initAdapter();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                loadMovies(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initAdapter() {
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieViewAdapter(this);
        adapter.setClickListener(this);
        recyclerViewMovies.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(viewModel.actionsSubject.subscribe(new Consumer<List<Movie>>() {
            @Override
            public void accept(List<Movie> movies) {
                adapter.setItems(movies);
            }
        }));
        loadMovies(spinnerSort.getSelectedItemPosition());
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private void loadMovies(int position) {
        switch (position) {
            case 0:
                viewModel.populatePopularMovies();
                break;
            case 1:
                viewModel.populateToRateMovies();
                break;
            case 2:
                populateFavoriteMovies();
                break;
        }
    }

    private void populateFavoriteMovies() {
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.setItems(movies);
            }
        });
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}