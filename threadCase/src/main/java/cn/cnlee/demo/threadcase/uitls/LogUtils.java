package cn.cnlee.demo.threadcase.uitls;

import android.util.Log;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public class LogUtils {
    private static final String LOG_PREFIX = "ThreadCase";

    /**
     * Print an info  message.
     *
     * @param tag     Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void i(String tag, String message) {
        Log.i(LOG_PREFIX, "[" + tag + "]" + message);
    }

    /**
     * Print a debug message.
     *
     * @param tag     Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void d(String tag, String message) {
        Log.d(LOG_PREFIX, "[" + tag + "]" + message);
    }

    /**
     * Print a warning message.
     *
     * @param tag     Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void w(String tag, String message) {
        Log.w(LOG_PREFIX, "[" + tag + "]" + message);
    }

    /**
     * Print an error message.
     *
     * @param tag     Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void e(String tag, String message) {
        Log.e(LOG_PREFIX, "[" + tag + "]" + message);
    }

    /**
     * Print a verbose message.
     *
     * @param tag     Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    public static void v(String tag, String message) {
        Log.v(LOG_PREFIX, "[" + tag + "]" + message);
    }
}
