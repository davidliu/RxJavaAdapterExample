package com.voxel.rxjavaadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidliu on 4/26/17.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private List<ItemPresenter> mPresenterList = new ArrayList<>();

    public ItemAdapter(int count) {
        for (int i = 0; i < count; i++) {
            mPresenterList.add(new ItemPresenter(i));
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemPresenter.createViewForPresenter(parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemPresenter presenter = mPresenterList.get(position);
        presenter.bind(holder);
    }

    @Override
    public int getItemCount() {
        return mPresenterList.size();
    }

    @Override
    public void onViewRecycled(ItemViewHolder holder) {
        holder.unbindPresenter();
    }
}
