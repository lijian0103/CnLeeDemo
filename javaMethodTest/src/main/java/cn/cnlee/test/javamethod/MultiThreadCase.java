package cn.cnlee.test.javamethod;

import cn.cnlee.test.javamethod.thread.ExecuteManager;

/**
 * @Description 验证多线程调用同一个对象的方法如何实现
 * @Author cnlee
 * @Date 2021/6/18
 * @Version 1.0
 */
public class MultiThreadCase {
    public static void main(String[] args) throws InterruptedException {
        final ExecuteManager executeManager = ExecuteManager.getInstance();
        Thread[] ts = new Thread[50];//同时开20个线程
        Thread prevTd = Thread.currentThread();
        for (int i = 0; i < ts.length; i++) {
            int finalI = i;
            JoinThread jt = new JoinThread(prevTd, new Runnable() {
                @Override
                public void run() {
                    executeManager.execute(finalI);
                }
            });
            jt.start();
            prevTd = jt;
        }
    }

    static class JoinThread extends Thread {
        Thread prevThread;

        public JoinThread(Thread prev, Runnable r) {
            super(r);
            this.prevThread = prev;
        }

        @Override
        public void run() {
            /**
             * thread.join的含义是当前线程需要等待previousThread线程终止之后才从thread.join返回。
             * 简单来说，就是线程没有执行完之前，会一直阻塞在join方法处。
             */
            try {
                prevThread.join();//线程顺序执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
}
