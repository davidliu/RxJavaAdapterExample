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
        mBoundViewHolder.clearValue();


        // Add it to the set of disposables to be cleared upon unbind. This will sever any
        // events that come after the viewholder has been unbound.
        //
        // Do not set an observeOn here, as observeOn will force the observation
        // to be queued onto the looper rather than execute synchronously.
        mDisposables.add(
                mItemModel.getObservable()
                        .subscribe(value -> {
                            mBoundViewHolder.showValue(value);
                        }));
    }

    // Sever the connection between ViewHolder and Presenter.
    public void unbind(ItemViewHolder holder) {
        if (mBoundViewHolder == holder) {
            mDisposables.clear();
            mBoundViewHolder.bindPresenter(null);
            mBoundViewHolder = null;
        }
    }

}
