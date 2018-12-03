package com.romelapj.movies.ui;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.romelapj.movies.R;
import com.romelapj.movies.databinding.ActivityDetailBinding;
import com.romelapj.movies.models.Movie;
import com.squareup.picasso.Picasso;

import static com.romelapj.movies.models.Movie.BASE_IMAGE_URL;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setMovie((Movie) getIntent().getParcelableExtra("movie"));
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(BASE_IMAGE_URL + imageUrl).into(view);
    }
}
