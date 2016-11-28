package com.alfred.learn.network.subscriber;

import com.alfred.learn.interf.ProgressCancelListener;

import rx.Subscriber;

/**
 * Created by Alfred on 16/10/20.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    @Override
    public void onCancelProgress() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }

    public abstract void next(T t);

}
