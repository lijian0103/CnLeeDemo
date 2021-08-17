package cn.cnlee.app.actionexecutor.demo;

import androidx.annotation.NonNull;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import cn.cnlee.app.actionexecutor.util.LogUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/17
 * @Version 1.0
 */
public class Test {
    // Avoiding synthetic accessor.
    volatile Thread mCurrentBackgroundExecutorThread;
    private final ThreadFactory mBackgroundThreadFactory = new ThreadFactory() {

        private int mThreadsCreated = 0;

        @Override
        public Thread newThread(@NonNull Runnable r) {
            // Delegate to the default factory, but keep track of the current thread being used.
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setName("task-thread-" + mThreadsCreated);
            mThreadsCreated++;
            mCurrentBackgroundExecutorThread = thread;
            return thread;
        }
    };

    //    private final ExecutorService mBackgroundExecutor = Executors.newCachedThreadPool(mBackgroundThreadFactory);
    private final ExecutorService mBackgroundExecutor = Executors.newFixedThreadPool(3, mBackgroundThreadFactory);


    private void testExecute() {
        ActionA actionA = new ActionA();
        ActionB actionB = new ActionB();
        ActionC actionC = new ActionC();
        ActionD actionD = new ActionD();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            LogUtils.d(Test.class.getSimpleName(), "==all finish==" + Thread.currentThread());
            mBackgroundExecutor.execute(() -> {
                actionD.doAction();
            });
            mBackgroundExecutor.execute(() -> {
                actionC.doAction();
            });
            mBackgroundExecutor.execute(() -> {
                actionB.doAction();
            });
            mBackgroundExecutor.execute(() -> {
                actionA.doAction();
            });
        });

        mBackgroundExecutor.execute(() -> {
            actionA.doAction();
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mBackgroundExecutor.execute(() -> {
            actionB.doAction();
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mBackgroundExecutor.execute(() -> {
            actionC.doAction();
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public static void test() {
        new Test().testExecute();
    }


}
