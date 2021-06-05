package cn.cnlee.demo.rxjavacase.manager;

import android.util.Log;

import cn.cnlee.demo.rxjavacase.EventTask;
import cn.cnlee.demo.rxjavacase.WorkThread;
import cn.cnlee.demo.rxjavacase.display.ActionOption;
import cn.cnlee.demo.rxjavacase.display.callback.IEventCallback;

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
     * execute.
     */
    public void execute(ActionOption option) {
        WorkThread workThread = new WorkThread();
        EventTask task = new EventTask(option, mEventCallback);
        workThread.post(() -> task.doTask());
    }

    class EventCallback implements IEventCallback {

        @Override
        public void onCallback(String id, int status, String result, String msg) {
            Log.e(TAG, "notify event callback result. id: " + id + " status: " + status + " result: " + result + " msg: " + msg);
        }
    }
}
