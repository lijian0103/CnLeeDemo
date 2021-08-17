package cn.cnlee.app.actionexecutor.action;

import androidx.lifecycle.LiveData;
import androidx.work.WorkManager;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/6
 * @Version 1.0
 */
public class ActionManager {

    public static ActionManager sInstance;

    public static ActionManager getInstance() {
        if (sInstance == null) {
            synchronized (ActionManager.class) {
                if (sInstance == null) {
                    sInstance = new ActionManager();
                }
            }
        }
        return sInstance;
    }

    public void enqueue(ActionRequest request) {
//        WorkManager.getInstance().beginWith(null);
    }

    public void enqueueLoopWork(ActionRequest request) {

    }

    /**
     * 注意并不是所有的任务都可以取消，当任务正在执行时是不能取消的，当然任务执行完成了,取消也是没有意义的.
     * 也就是说当任务加入到ManagerWork的队列中但是还没有执行时才可以取消.
     *
     * @param actionId actionId
     */
    public void cancelActionById(int actionId) {

    }

    public LiveData<ActionInfo> getActionInfoByIdLiveData(int actionId) {
        return null;
    }
}
