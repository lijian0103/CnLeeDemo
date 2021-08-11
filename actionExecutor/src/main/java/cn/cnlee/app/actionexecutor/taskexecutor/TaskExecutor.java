package cn.cnlee.app.actionexecutor.taskexecutor;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * @Description Interface for executing common tasks in ActionManager.
 * @Author cnlee
 * @Date 2021/8/6
 * @Version 1.0
 */
public interface TaskExecutor {

    /**
     * @param runnable {@link Runnable} to post to the main thread
     */
    void postToMainThread(Runnable runnable);

    /**
     * @return The {@link Executor} for main thread task processing
     */
    Executor getMainThreadExecutor();

    /**
     * @param runnable {@link Runnable} to execute on a background thread pool
     */
    void executeOnBackgroundThread(Runnable runnable);

    /**
     * @return the {@link Thread} being used by WorkManager's background task executor.
     */
    @NonNull
    Thread getBackgroundExecutorThread();

    /**
     * @return The {@link Executor} for background task processing
     */
    Executor getBackgroundExecutor();
}
