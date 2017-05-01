package com.voxel.rxjavaadapter;

import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by davidliu on 4/26/17.
 */
public class ItemPresenter {

    ItemModel mItemModel;
    ItemViewHolder mBoundViewHolder;
    CompositeDisposable mDisposables = new CompositeDisposable();

    public ItemPresenter(int count) {
        mItemModel = new ItemModel(count);
    }

    public static ItemViewHolder createViewForPresenter(ViewGroup parent) {
        return ItemViewHolder.onCreateViewHolder(parent);
    }

    public void bind(ItemViewHolder holder) {
        mBoundViewHolder = holder;
        mBoundViewHolder.bindPresenter(this);
        mBoundViewHolder.clear();

        mDisposables.add(
                mItemModel.getObservable()
                        .subscribe(value -> {
                            mBoundViewHolder.showValue(value);
                        }));
    }

    public void unbind(ItemViewHolder holder) {
        if (mBoundViewHolder == holder) {
            mDisposables.clear();
            mBoundViewHolder.bindPresenter(null);
            mBoundViewHolder = null;
        }
    }

}
