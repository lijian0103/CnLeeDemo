package cn.cnlee.demo.rxjavacase.display.callback;

public interface IEventCallback {
    void onCallback(String id, int status, String result, String msg);
}
