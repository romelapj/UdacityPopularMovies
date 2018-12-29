package com.romelapj.movies.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.romelapj.movies.R;
import com.romelapj.movies.models.Trailer;
import com.romelapj.movies.ui.adapters.GenericAdapterRecyclerView;

public class TrailerView extends FrameLayout implements GenericAdapterRecyclerView.ItemView<Trailer> {

    private Trailer trailer;
    private TextView textView;

    public TrailerView(Context context) {
        super(context);
        init();
    }

    public TrailerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrailerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_trailer_movie, this, true);
        textView = findViewById(R.id.textViewTrailerTitle);
    }


    @Override
    public void bind(Trailer item, int position) {
        this.trailer = item;
        textView.setText(item.getName());
    }

    @Override
    public void setItemClickListener(final GenericAdapterRecyclerView.OnItemClickListener onItemClickListener) {
        textView.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClicked(TrailerView.this);

                    }
                }
        );
    }

    @Override
    public int getIdForClick() {
        return 0;
    }

    @Override
    public Trailer getData() {
        return trailer;
    }

}
