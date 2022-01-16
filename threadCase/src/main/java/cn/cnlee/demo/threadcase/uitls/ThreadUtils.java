package cn.cnlee.demo.threadcase.uitls;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public class ThreadUtils {

    public static final String TAG_HAIZI = "haizi";
    public static final String TAG_ACTION = "action";
    public static Map<String, Handler> cacheHandler = new HashMap<>();

    public static Handler getWorkHandler(String tag) {
        Handler handler = cacheHandler.get(tag);
        if (handler == null) {
            //创建异步HandlerThread
            HandlerThread handlerThread = new HandlerThread(tag);
            //必须先开启线程
            handlerThread.start();
            //子线程Handler
            handler = new Handler(handlerThread.getLooper());
            cacheHandler.put(tag, handler);
        }
        return handler;
    }

    public static Handler getUiHandler() {
        Handler handler = cacheHandler.get("ui");
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            cacheHandler.put("ui", handler);
        }
        return handler;
    }
}
