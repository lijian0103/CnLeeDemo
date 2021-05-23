package cn.cnlee.demo.animation.dispay;

public interface DisplayCallback {

    void onExecuteAction(String actionId, int status, String result, String errMsg);

    void onCancelAction(String actionId, int status, String errMsg);

}
