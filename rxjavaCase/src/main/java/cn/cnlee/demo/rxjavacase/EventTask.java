package cn.cnlee.demo.rxjavacase;

import android.util.Log;

import cn.cnlee.demo.rxjavacase.display.Action;
import cn.cnlee.demo.rxjavacase.display.ActionOption;
import cn.cnlee.demo.rxjavacase.display.callback.IEventCallback;

public class EventTask {
    private IEventCallback mEventCallback;
    private ActionOption mOption;

    public EventTask(ActionOption option, IEventCallback mEventCallback) {
        this.mOption = option;
        this.mEventCallback = mEventCallback;
    }

    /**
     * execute task.
     */
    public void doTask() {
        Log.e(EventTask.class.getSimpleName(), "thread: " + Thread.currentThread() + " doTask: " + mOption);
        Action.getInstance().display(mOption, new Action.ActionCallback() {
            @Override
            public void onCallback(ActionOption option, int status, String result, String msg) {
                if (mEventCallback != null) {
                    mEventCallback.onCallback(option.getId(), status, result, msg);
                }
            }
        });
    }
}
