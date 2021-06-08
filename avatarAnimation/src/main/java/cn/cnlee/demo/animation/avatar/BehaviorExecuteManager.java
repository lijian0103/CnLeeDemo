package cn.cnlee.demo.animation.avatar;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class BehaviorExecuteManager {
    private static final String TAG = BehaviorExecuteManager.class.getSimpleName();
    private static BehaviorExecuteManager sBehaviorExecuteManager;
    private LinkedList<AvatarOption> mAvatarOptions = new LinkedList<>();
    private final LinkedList<AvatarOption> mExecutedAvatarOptions = new LinkedList<>();
    private boolean mNeedRepeat = false;
    private Avatar.ActionCallback mActionCallback;
    private ActionStateMachine mActionStateMachine;

    private BehaviorExecuteManager() {
        mActionStateMachine = ActionStateMachine.makeStateMachine();
    }

    /**
     * static get single instance.
     *
     * @return instance
     */
    public static BehaviorExecuteManager getInstance() {
        if (null == sBehaviorExecuteManager) {
            synchronized (BehaviorExecuteManager.class) {
                if (null == sBehaviorExecuteManager) {
                    sBehaviorExecuteManager = new BehaviorExecuteManager();
                }
            }
        }
        return sBehaviorExecuteManager;
    }

    public void setActionCallback(Avatar.ActionCallback actionCallback) {
        mActionCallback = actionCallback;
    }

    /**
     * add a series avatar options.
     *
     * @param options AvatarOption
     */
    public void addBehavior(List<AvatarOption> options, boolean needRepeat) {
        Log.e(TAG, "===addBehavior==");
        cancel();
        mAvatarOptions.addAll(options);
        mNeedRepeat = needRepeat;
        start();
    }

    public void notifyAddBehavior(List<AvatarOption> options, boolean needRepeat) {
        mActionStateMachine.addBehavior(options, needRepeat);
    }

    /**
     * get need execute action option.
     *
     * @return AvatarOption
     */
    public AvatarOption getNextAction() {
        Log.e(TAG, "===getNextAction==");
        AvatarOption option;
        if (mAvatarOptions.isEmpty() && mNeedRepeat && !mExecutedAvatarOptions.isEmpty()) {
            mAvatarOptions.addAll(mExecutedAvatarOptions);
            mExecutedAvatarOptions.clear();
        }
        option = mAvatarOptions.poll();
        if (option != null) {
            mExecutedAvatarOptions.add(option);
        }
        return option;
    }

    public void start() {
        mActionStateMachine.turnToWork();
    }

    public void cancel() {
        mAvatarOptions.clear();
        mExecutedAvatarOptions.clear();
    }

    /**
     * notify action execute result when all actions execute finish.
     *
     * @param option avatarOption
     * @param status status
     * @param errMsg errMsg
     */
    public void notifyActionExecuteRes(AvatarOption option, int status, String errMsg) {
        if (mAvatarOptions.isEmpty() && mNeedRepeat && !mExecutedAvatarOptions.isEmpty()) {
            mAvatarOptions.addAll(mExecutedAvatarOptions);
            mExecutedAvatarOptions.clear();
        }
        status = 1;//TODO status need transform 1_running,0_finish
        if (!mNeedRepeat && mAvatarOptions.isEmpty()) {
            status = 0;
        }
        mActionCallback.onCallback(option, status, errMsg);
    }


}
