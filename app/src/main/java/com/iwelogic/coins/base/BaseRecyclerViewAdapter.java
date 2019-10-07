package com.iwelogic.coins.base;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    public BaseRecyclerViewAdapter(MutableLiveData<List<T>> items) {
        this.items = items;
    }

    protected static final String TAG = "myLog";
    private MutableLiveData<List<T>> items;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (items.getValue() == null)
            return 0;
        return items.getValue().size();
    }


    protected T getItem(int position) {
        if (items.getValue() == null)
            return null;
        return items.getValue().get(position);
    }
}
