package cn.cnlee.app.actionexecutor.util;

import android.util.Log;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/6
 * @Version 1.0
 */
public class LogUtils {
    private final static String TAG_PREFIX = "AM";

    public static void d(String tag, String message) {
        Log.d(TAG_PREFIX, "[" + tag + "]" + message);
    }

    public static void e(String tag, String message) {
        Log.e(TAG_PREFIX, "[" + tag + "]" + message);
    }
}
