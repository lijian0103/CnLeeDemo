package cn.cnlee.demo.threadcase.task;

import cn.cnlee.demo.threadcase.bean.Action;
import cn.cnlee.demo.threadcase.mock.Haizi;
import cn.cnlee.demo.threadcase.mock.IPlayAsset;
import cn.cnlee.demo.threadcase.uitls.LogUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public class ActionTask {

    private static final String TAG = Haizi.class.getSimpleName();
    private Action mAction;
    private ActionCallback mActionCallback;

    public ActionTask(Action action, ActionCallback callback) {
        this.mAction = action;
        this.mActionCallback = callback;
    }

    public void doTask() {
        LogUtils.i(TAG, "doTask start: " + mAction);
        Haizi.play_anim_clip(mAction.getName(), new IPlayAsset() {
            @Override
            public void onPlayEnd(boolean state) {
                LogUtils.i(TAG, "doTask end: " + mAction);
                mActionCallback.onCallback(mAction.getId(), 0, null);
            }
        });
    }

}
