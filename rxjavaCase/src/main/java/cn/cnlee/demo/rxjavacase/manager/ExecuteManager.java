package cn.cnlee.demo.rxjavacase.manager;

import android.util.Log;

import cn.cnlee.demo.rxjavacase.EventTask;
import cn.cnlee.demo.rxjavacase.WorkThread;
import cn.cnlee.demo.rxjavacase.bean.EventResult;
import cn.cnlee.demo.rxjavacase.display.ActionOption;
import cn.cnlee.demo.rxjavacase.display.callback.IEventCallback;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExecuteManager {

    private static final String TAG = ExecuteManager.class.getSimpleName();
    private IEventCallback mEventCallback;

    public static ExecuteManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private ExecuteManager() {
        mEventCallback = new EventCallback();
    }

    private static class InstanceHolder {
        public static final ExecuteManager INSTANCE = new ExecuteManager();
    }

    /**
     * execute by callback.
     */
    public void executeCb(ActionOption option) {
        WorkThread workThread = new WorkThread("executeCb");
        EventTask task = new EventTask(option, mEventCallback);
        workThread.post(() -> task.doTask());
    }

    /**
     * execute by Rxjava.
     */
    public void executeRx(ActionOption option) {
        Disposable disposable = Flowable.create(new FlowableOnSubscribe<EventResult>() {

            @Override
            public void subscribe(@NonNull FlowableEmitter<EventResult> emitter) throws Throwable {
                WorkThread workThread = new WorkThread("executeRx");
                EventTask task = new EventTask(option, new IEventCallback() {
                    @Override
                    public void onCallback(String id, int status, String result, String msg) {
                        emitter.onNext(EventResult.builder().id(id).status(status).result(result).msg(msg).build());
                    }
                });
                workThread.post(() -> {
                    try {
                        task.doTask();
                    } catch (Exception e) {
                        emitter.tryOnError(e);
                    }
                });
            }

        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(eventResult -> {
                    Log.e(TAG, "notify event callback result." + eventResult);
                }, throwable -> {
                    Log.e(TAG, "===throwable====" + throwable.getMessage());
                });
    }

    class EventCallback implements IEventCallback {

        @Override
        public void onCallback(String id, int status, String result, String msg) {
            Log.e(TAG, "notify event callback result. id: " + id + " status: " + status + " result: " + result + " msg: " + msg);
        }
    }
}
