package com.romelapj.movies.ui.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.romelapj.movies.AppExecutors;
import com.romelapj.movies.R;
import com.romelapj.movies.database.AppDatabase;
import com.romelapj.movies.database.Movie;
import com.romelapj.movies.databinding.ViewHeaderDetailBinding;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;
import com.squareup.picasso.Picasso;

import io.reactivex.disposables.CompositeDisposable;

import static com.romelapj.movies.database.Movie.BASE_IMAGE_URL;

public class HeaderDetailView extends RelativeLayout implements GenericAdapterRecyclerView.ItemView<Movie> {

    CompositeDisposable disposable = new CompositeDisposable();

    private ViewHeaderDetailBinding binding;
    private AppDatabase database;

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
        database = AppDatabase.getInstance(getContext());

    }

    @Override
    public void bind(final Movie item, int position) {
        binding.setMovie(item);
        initListener(item);
        drawFavorite(isSelected());
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        binding.textViewFavorite.setSelected(selected);

        drawFavorite(selected);
    }

    private void drawFavorite(boolean selected) {
        binding.textViewFavorite.setBackgroundColor(ContextCompat.getColor(getContext(),
                selected ? R.color.colorPrimaryDark : R.color.colorAccent
        ));
        binding.textViewFavorite.setText(
                selected ? R.string.remove_favorite : R.string.add_favorite
        );
    }

    private void initListener(final Movie item) {
        binding.textViewFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppExecutors().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (isSelected()) {
                            database.movieDao().deleteMovie(item);
                        } else {
                            database.movieDao().insertMovie(item);
                        }
                    }
                });
            }
        });
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
