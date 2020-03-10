package ru.vssemikoz.newsfeed.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    private List<T> items;
    private OnRecyclerItemClickListener listener;
    private Context context;

    interface OnRecyclerItemClickListener {
    }

    public BaseAdapter(Context context){
        this.context = context;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public OnRecyclerItemClickListener getListener() {
        return listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public abstract BaseHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        T item = items.get(position);
        holder.onBind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
