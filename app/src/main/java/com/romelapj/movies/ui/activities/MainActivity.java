package com.romelapj.movies.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.romelapj.movies.R;
import com.romelapj.movies.adapters.MovieViewAdapter;
import com.romelapj.movies.databinding.ActivityMainBinding;
import com.romelapj.movies.models.Movie;
import com.romelapj.movies.ui.viewmodels.MainViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements MovieViewAdapter.ItemClickListener {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ActivityMainBinding binding;
    MainViewModel viewModel = new MainViewModel();

    MovieViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initAdapter();
        binding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieViewAdapter(this);
        adapter.setClickListener(this);
        binding.recyclerViewMovies.setAdapter(adapter);
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
        loadMovies(binding.spinnerSort.getSelectedItemPosition());
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private void loadMovies(int position) {
        if (position == 0) {
            viewModel.populatePopularMovies();
        } else {
            viewModel.populateToRateMovies();
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}