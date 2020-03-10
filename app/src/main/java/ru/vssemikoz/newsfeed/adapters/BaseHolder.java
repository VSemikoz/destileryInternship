package ru.vssemikoz.newsfeed.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public void onBind(T item, BaseAdapter.OnRecyclerItemClickListener listener){
    }

}
