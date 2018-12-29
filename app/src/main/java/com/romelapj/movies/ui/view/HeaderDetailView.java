package com.romelapj.movies.ui.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.romelapj.movies.databinding.ViewHeaderDetailBinding;
import com.romelapj.movies.models.Movie;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;
import com.squareup.picasso.Picasso;

import static com.romelapj.movies.models.Movie.BASE_IMAGE_URL;

public class HeaderDetailView extends RelativeLayout implements GenericAdapterRecyclerView.ItemView<Movie> {

    private ViewHeaderDetailBinding binding;

    public HeaderDetailView(Context context) {
        super(context);
        init();
    }

    public HeaderDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        binding = ViewHeaderDetailBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    @Override
    public void bind(Movie item, int position) {
        binding.setMovie(item);
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

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(BASE_IMAGE_URL + imageUrl).into(view);
    }
}
