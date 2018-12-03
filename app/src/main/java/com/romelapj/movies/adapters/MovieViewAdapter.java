package com.romelapj.movies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.romelapj.movies.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.ViewHolder> {

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    private List<Movie> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public MovieViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(BASE_IMAGE_URL + mData.get(position).getPosterPath()).into(holder.imageViewThumbnails);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewThumbnails;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewThumbnails = itemView.findViewById(R.id.imageViewThumbnails);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(mData.get(getAdapterPosition()));
        }
    }

    Movie getItem(int id) {
        return mData.get(id);
    }

    public void setItems(List<Movie> movies) {
        mData = movies;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Movie movie);
    }
}