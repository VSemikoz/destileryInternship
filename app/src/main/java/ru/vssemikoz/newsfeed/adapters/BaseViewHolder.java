package ru.vssemikoz.newsfeed.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(T item, BaseAdapter.OnRecyclerItemClickListener listener){
    }

}
