package cn.cnlee.demo.threadcase.task;

import cn.cnlee.demo.threadcase.bean.Action;
import cn.cnlee.demo.threadcase.mock.Haizi;
import cn.cnlee.demo.threadcase.uitls.LogUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public class ActionExecutor {
    private static final String TAG = Haizi.class.getSimpleName();
    private static ActionExecutor mInstance;
    private Action mAction;

    private ActionExecutor() {
    }

    public static ActionExecutor getInstance() {
        if (null == mInstance) {
            synchronized (ActionExecutor.class) {
                if (null == mInstance) {
                    mInstance = new ActionExecutor();
                }
            }
        }
        return mInstance;
    }

    private void doAction(Action action) {
        ActionTask task = new ActionTask(action, new ActionCallback() {
            @Override
            public void onCallback(String actionId, int status, String errMsg) {
                LogUtils.d(TAG, "doAction onCallback: " + actionId + " ,loop: " + action.isLoop());
                if (mAction == action && action.isLoop()) {
                    LogUtils.d(TAG, "doLoop");
                    doAction(action);
                }
            }
        });
        task.doTask();
    }

    public void performAction(Action action) {
        this.mAction = action;
        doAction(action);
    }
}
