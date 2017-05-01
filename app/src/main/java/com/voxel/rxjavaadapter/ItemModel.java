package com.voxel.rxjavaadapter;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by davidliu on 4/26/17.
 */
public class ItemModel {

    private final int mCount;
    private Observable<Integer> mObservable;
    private BehaviorSubject<Integer> mSubject;
    private Observable<Integer> mSubjectObservable;

    public ItemModel(int count) {
        mCount = count;
    }

    public Observable<Integer> getObservable() {
        if (mObservable == null) {

            mSubject = BehaviorSubject.create();
            mObservable = Observable
                    .create((ObservableOnSubscribe<Integer>) e -> {
                        e.onNext(calculate());
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(mSubject);
            mSubjectObservable = mSubject;
        }
        return mSubjectObservable;
    }

    public int calculate() throws InterruptedException {
        Thread.sleep(2000);
        return mCount * 1000;
    }
}
