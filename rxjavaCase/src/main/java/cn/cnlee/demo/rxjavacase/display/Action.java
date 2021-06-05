package cn.cnlee.demo.rxjavacase.display;

import android.os.SystemClock;
import android.util.Log;

public class Action {

    private static Action mInstance;

    /**
     * get single instance.
     *
     * @return
     */
    public static synchronized Action getInstance() {
        if (mInstance == null) {
            mInstance = new Action();
        }
        return mInstance;
    }

    /**
     * display.
     *
     * @param option
     * @param callback
     */
    public void display(ActionOption option, ActionCallback callback) {
        Log.e(Action.class.getSimpleName(), "======display=====");
        SystemClock.sleep(2000);
        callback.onCallback(option, 0, "result", "msg");
    }

    public interface ActionCallback {
        void onCallback(ActionOption option, int status, String result, String msg);
    }
}
