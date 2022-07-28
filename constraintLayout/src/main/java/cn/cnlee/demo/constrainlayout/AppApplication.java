package cn.cnlee.demo.constrainlayout;

import android.app.Application;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/7/28
 * @Version 1.0
 */
public class AppApplication extends Application {

    private static AppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static AppApplication getInstance() {
        return mInstance;
    }
}
