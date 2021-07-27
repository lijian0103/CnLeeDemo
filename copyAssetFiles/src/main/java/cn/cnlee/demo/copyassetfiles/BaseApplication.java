package cn.cnlee.demo.copyassetfiles;

import android.app.Application;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/7/27
 * @Version 1.0
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApplication instance(){
        return sInstance;
    }
}
