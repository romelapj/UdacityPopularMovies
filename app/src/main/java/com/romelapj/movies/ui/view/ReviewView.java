package com.romelapj.movies.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.romelapj.movies.R;
import com.romelapj.movies.models.Review;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;

public class ReviewView extends FrameLayout implements GenericAdapterRecyclerView.ItemView<Review> {

    private TextView textViewTitle;
    private TextView textViewSubtitle;

    public ReviewView(Context context) {
        super(context);
        init();
    }

    public ReviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_review_movie, this, true);
        textViewTitle = findViewById(R.id.textViewReviewTitle);
        textViewSubtitle = findViewById(R.id.textViewReviewSubtitle);
    }


    @Override
    public void bind(Review item, int position) {
        textViewTitle.setText(item.getAuthor());
        textViewSubtitle.setText(item.getContent());
    }

    @Override
    public void setItemClickListener(final GenericAdapterRecyclerView.OnItemClickListener onItemClickListener) {
    }

    @Override
    public int getIdForClick() {
        return 0;
    }

    @Override
    public Review getData() {
        return null;
    }

}
