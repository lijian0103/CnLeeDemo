package cn.cnlee.app.actionexecutor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.cnlee.app.actionexecutor.demo.Test;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test.test();
//        new Thread(() -> {
//            TaskExecutor taskExecutor = new ActionManagerTaskExecutor();
//            taskExecutor.getBackgroundExecutor().execute(() -> {
//                LogUtils.e(TAG, Thread.currentThread() + " 11");
//            });
//            LogUtils.e(TAG, taskExecutor.getBackgroundExecutorThread() + "===" + taskExecutor.getMainThreadExecutor());
//        }).start();
//
//        new Thread(() -> {
//            TaskExecutor taskExecutor2 = new ActionManagerTaskExecutor();
//            taskExecutor2.getBackgroundExecutor().execute(() -> {
//                LogUtils.e(TAG, Thread.currentThread() + " 22");
//            });
//            LogUtils.e(TAG, taskExecutor2.getBackgroundExecutorThread() + "==2==" + taskExecutor2.getMainThreadExecutor());
//
//        }).start();

    }
}