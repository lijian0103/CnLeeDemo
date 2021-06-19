package cn.cnlee.test.javamethod.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 执行管理
 * @Author cnlee
 * @Date 2021/6/18
 * @Version 1.0
 */
public class ExecuteManager {
    private static ExecuteManager instance = new ExecuteManager();
    private ICallBack mCallBack;
    private int tempId = -1;
    public ExecutorService executors;

    private ExecuteManager() {
        executors = Executors.newSingleThreadExecutor();
        mCallBack = new CallBack();
    }

    public static ExecuteManager getInstance() {
        return instance;
    }

    public void singeThreadExecute(int id) {
        executors.submit(() -> execute(id));
    }

    public void execute(final int id) {
        System.err.println(String.format("execute. id: %d, temp: %d, thread: %s", id, tempId, Thread.currentThread().toString()));
        final Action action = new Action(id, mCallBack);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println(String.format("execute run. id: %d, temp: %d, thread: %s", id, tempId, Thread.currentThread().toString()));
                action.doAction();
            }
        }).start();
        tempId = id;
    }

    class CallBack implements ICallBack {

        @Override
        public void onCallBack(int id, String msg) {
            System.err.println(String.format("onCallBack. id: %d,temp: %d, thread: %s", id, tempId, Thread.currentThread().toString()));
        }
    }

}
