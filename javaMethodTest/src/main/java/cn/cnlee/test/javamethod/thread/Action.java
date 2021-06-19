package cn.cnlee.test.javamethod.thread;

/**
 * @Description Action
 * @Author cnlee
 * @Date 2021/6/18
 * @Version 1.0
 */
public class Action {
    private int id;
    private ICallBack mCallBack;

    public Action(int id, ICallBack callBack) {
        this.id = id;
        this.mCallBack = callBack;
    }

    public void doAction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println(String.format("doAction. id: %d, msg: %s, thread: %s", id, "msg-" + id, Thread.currentThread().toString()));
                if (mCallBack != null) {
                    mCallBack.onCallBack(id, "msg-" + id);
                }
            }
        }).start();
    }
}
