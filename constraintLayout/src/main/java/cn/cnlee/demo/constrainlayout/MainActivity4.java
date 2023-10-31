package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity4 extends AppCompatActivity {

    private static final String TAG = MainActivity4.class.getSimpleName();
    /**
     * 自定义视图
     */
    private CustomView mCustomView;
    /**
     * 线程
     */
    private Thread thread;
    /**
     * 线程循环控制变量
     */
    private boolean isRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实例化自定义视图
        mCustomView = new CustomView(this);
        // 将自定义视图作为用户界面
        setContentView(mCustomView);

        // 创建线程，刷新视图
        isRunning = true;
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isRunning) {
                    try {
                        // 刷新自定义视图
                        mCustomView.postInvalidate();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 启动线程
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
        thread = null;
    }
}