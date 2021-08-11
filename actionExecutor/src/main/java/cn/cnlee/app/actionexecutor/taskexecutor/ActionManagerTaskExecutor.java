package cn.cnlee.app.actionexecutor.taskexecutor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Description Default Task Executor for executing common tasks in ActionManager.
 * @Author cnlee
 * @Date 2021/8/6
 * @Version 1.0
 */
public class ActionManagerTaskExecutor implements TaskExecutor {
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

    private final Executor mMainThreadExecutor = new Executor() {
        @Override
        public void execute(@NonNull Runnable command) {
            postToMainThread(command);
        }
    };

    // Avoiding synthetic accessor.
    volatile Thread mCurrentBackgroundExecutorThread;
    private final ThreadFactory mBackgroundThreadFactory = new ThreadFactory() {

        private int mThreadsCreated = 0;

        @Override
        public Thread newThread(@NonNull Runnable r) {
            // Delegate to the default factory, but keep track of the current thread being used.
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setName("ActionManagerTaskExecutor-thread-" + mThreadsCreated);
            mThreadsCreated++;
            mCurrentBackgroundExecutorThread = thread;
            return thread;
        }
    };

    private final ExecutorService mBackgroundExecutor =
            Executors.newSingleThreadExecutor(mBackgroundThreadFactory);

    @Override
    public void postToMainThread(Runnable r) {
        mMainThreadHandler.post(r);
    }

    @Override
    public Executor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    @Override
    public void executeOnBackgroundThread(Runnable r) {
        mBackgroundExecutor.execute(r);
    }

    @Override
    public Executor getBackgroundExecutor() {
        return mBackgroundExecutor;
    }

    @NonNull
    @Override
    public Thread getBackgroundExecutorThread() {
        return mCurrentBackgroundExecutorThread;
    }
}
