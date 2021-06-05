package cn.cnlee.demo.rxjavacase;

import com.jakewharton.rxrelay3.PublishRelay;
import com.jakewharton.rxrelay3.Relay;

import io.reactivex.rxjava3.core.Observable;


public class RxBus {

    private final Relay<Object> mBus;

    private RxBus() {
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public void post(Object event) {
        mBus.accept(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}
