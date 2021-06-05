package cn.cnlee.demo.rxjavacase;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WorkThread extends HandlerThread {

    private static final String TAG = WorkThread.class.getSimpleName();
    private static String DEFAULT_WORK_THREAD_NAME = "default_work_thread";
    private Handler mWorkHandler;
    private Lock mLock = new ReentrantLock();

    public WorkThread() {
        this(DEFAULT_WORK_THREAD_NAME);
    }

    public WorkThread(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        super.run();
        Log.e(TAG, "WorkThread: run will stop. ");
    }

    /**
     * get work handler.
     *
     * @return handler
     */
    public Handler getWorkHandler() {
        mLock.lock();
        if (mWorkHandler == null) {
            if (!this.isAlive()) {
                this.start();
                mWorkHandler = new Handler(this.getLooper());
            }
        }
        mLock.unlock();
        return mWorkHandler;
    }

    /**
     * post runnable.
     *
     * @param runnable
     */
    public void post(Runnable runnable) {
        getWorkHandler().post(runnable);
    }

    /**
     * delay post runnable.
     *
     * @param runnable
     * @param delay    delayMillis
     */
    public void postDelayed(Runnable runnable, long delay) {
        getWorkHandler().postDelayed(runnable, delay);
    }

}
