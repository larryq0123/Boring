package com.susion.boring.db.operate;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.susion.boring.db.model.SimpleSong;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by susion on 17/2/20.
 */
public class DbBaseOperate<T>  implements DataBaseOperateContract.BaseOperate<T>{

    private Class<T> mClass;
    protected LiteOrm mLiteOrm;
    protected Context mContext;

    private DbBaseOperate(LiteOrm mLiteOrm, Context mContext) {
        this.mLiteOrm = mLiteOrm;
        this.mContext = mContext;
    }

    public DbBaseOperate(LiteOrm mLiteOrm, Context mContext, Class c) {
        this(mLiteOrm, mContext);
        mClass = c;
    }

    @Override
    public Observable<List<T>> add(final List<T> objects) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                List<T> insertSongs = new ArrayList<>();
                for (T song : objects) {
                    mLiteOrm.save(song);
                }
                subscriber.onNext(objects);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> add(final T song) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                if (mLiteOrm.save(song) != -1) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> delete(final T song) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                if (mLiteOrm.delete(song) != -1) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> clearALLData() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                if (mLiteOrm.deleteAll(mClass) != -1) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> query(final  String id) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean > subscriber) {
                if (mLiteOrm.queryById(id,mClass) != null) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Long> getTotalCount() {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long > subscriber) {
                long count = mLiteOrm.queryCount(mClass);
                subscriber.onNext(count);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<T>> getAll() {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T> > subscriber) {
                subscriber.onNext(mLiteOrm.query(mClass));
                subscriber.onCompleted();
            }
        });
    }

}
