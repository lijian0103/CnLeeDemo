package cn.cnlee.demo.threadcase.task;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public interface ActionCallback {
    void onCallback(String actionId, int status, String errMsg);
}
