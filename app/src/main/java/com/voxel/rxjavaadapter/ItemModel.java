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
    public ItemModel(int count) {
        mCount = count;
    }

    public Observable<Integer> getObservable() {
        if (mObservable == null) {

            // BehaviorSubject acts as a cache of size 1. It repeats the most recent
            // event back out to any new subscriber.
            mSubject = BehaviorSubject.create();

            // This observable subscribes on the io scheduler, meaning that it'll start there.
            //
            // It observesOn the main thread, such that when this source emits an event downstream,
            // it will be observed on the main thread.
            //
            // **However**, note that we subscribe to the observable with the subject. Subjects
            // act as a new source entirely, and sources by default subscribeOn/observeOn in the
            // thread that's kicking off anything.
            //
            // For example:
            // Source (io thread) -> event -> Subject -> event -> observes on io thread
            //
            // Subscriber (main thread) -> subscribe -> Subject -> event -> observes on main thread
            mObservable = Observable
                    .create((ObservableOnSubscribe<Integer>) e -> {
                        e.onNext(calculate());
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(mSubject);

        }

        return mSubject;
    }

    public int calculate() throws InterruptedException {
        Thread.sleep(2000);
        return mCount * 1000;
    }
}
