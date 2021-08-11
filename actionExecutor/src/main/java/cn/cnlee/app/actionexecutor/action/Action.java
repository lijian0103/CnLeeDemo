package cn.cnlee.app.actionexecutor.action;

import androidx.annotation.WorkerThread;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/6
 * @Version 1.0
 */
public abstract class Action {

    @WorkerThread
    public abstract void doAction();

    public final void startWork() {
        doAction();
    }
}
