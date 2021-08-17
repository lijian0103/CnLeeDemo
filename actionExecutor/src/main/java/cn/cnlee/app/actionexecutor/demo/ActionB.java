package cn.cnlee.app.actionexecutor.demo;

import android.os.SystemClock;

import cn.cnlee.app.actionexecutor.action.Action;
import cn.cnlee.app.actionexecutor.util.LogUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/17
 * @Version 1.0
 */
public class ActionB extends Action {
    private static final String TAG = ActionB.class.getSimpleName();

    @Override
    public void doAction() {
        LogUtils.d(TAG, "[doActon] start." + Thread.currentThread());
        SystemClock.sleep(4000);
        LogUtils.d(TAG, "[doActon] end.");
    }
}
