package com.romelapj.movies.ui.adapters;

import android.view.ViewGroup;

public abstract class ViewFactory<T extends GenericAdapterRecyclerView.ItemView> {

    public abstract T getView(ViewGroup parent, int viewType);

}