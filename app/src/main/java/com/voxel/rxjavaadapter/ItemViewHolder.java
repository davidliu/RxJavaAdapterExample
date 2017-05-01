package com.voxel.rxjavaadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by davidliu on 4/26/17.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;

    ItemPresenter mPresenter;

    private ItemViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }


    public static ItemViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    public void bindPresenter(ItemPresenter itemPresenter) {
        mPresenter = itemPresenter;
    }

    public void unbindPresenter() {
        if (mPresenter != null) {
            mPresenter.unbind(this);
        }

        mPresenter = null;
    }

    public void showValue(int value) {
        mTextView.setText(value + "");
    }

    public void clearValue() {
        mTextView.setText("");
    }
}
