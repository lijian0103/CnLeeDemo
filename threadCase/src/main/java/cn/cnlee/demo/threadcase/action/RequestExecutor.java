package cn.cnlee.demo.threadcase.action;

import android.os.Handler;

import cn.cnlee.demo.threadcase.uitls.ThreadUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/26
 * @Version 1.0
 */
public class RequestExecutor {

    private static RequestExecutor mInstance;
    private final Handler mRequestHandler;

    private RequestExecutor() {
        mRequestHandler = ThreadUtils.getWorkHandler(ThreadUtils.TAG_ACTION);
    }

    /**
     * single instance.
     *
     * @return RequestExecutor
     */
    public static RequestExecutor getInstance() {
        if (null == mInstance) {
            synchronized (RequestExecutor.class) {
                if (null == mInstance) {
                    mInstance = new RequestExecutor();
                }
            }
        }
        return mInstance;
    }

    public void doRequest() {
        prepareForRequest();
    }


    public void release() {
        //TODO 释放资源
    }

    public Handler getRequestHandler() {
        return mRequestHandler;
    }

    /**
     * doRequest前的准备
     * 比如前期的参数转换工作
     */
    private void prepareForRequest() {

    }
}
