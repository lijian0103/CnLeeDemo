package cn.cnlee.demo.animation.avatar;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 加载assets资源文件里的avatar_behaviors.json
 * 根据传入的avatarName找出对应一系列基本原子动作
 * 如果没有找到相应的原子动作，则直接将avatarName当作原子动作执行
 */
public class AvatarBehaviorEngine {

    private static final String TAG = AvatarBehaviorEngine.class.getSimpleName();
    private Map<String, BehaviorInfo> cacheAvatarBehaviors;
    private ArrayList<AvatarOption> mActionOptions = new ArrayList<>();
    private boolean isRepeat = false;
    private BehaviorExecuteManager mBehaviorExecuteManager;


    /**
     * constructor.
     *
     * @param context context
     */
    public AvatarBehaviorEngine(Context context) {
        mBehaviorExecuteManager = BehaviorExecuteManager.getInstance();
        String behaviorJson = JsonUtil.getJson(context, "avatar_behaviors.json");
        Log.d(TAG, "behaviorJson: " + behaviorJson);
        cacheAvatarBehaviors = new Gson().fromJson(behaviorJson, new TypeToken<HashMap<String, BehaviorInfo>>() {
        }.getType());
    }

    public void setActionCallback(Avatar.ActionCallback actionCallback) {
        mBehaviorExecuteManager.setActionCallback(actionCallback);
    }

    /**
     * do avatar behavior.
     */
    public void doAvatarBehavior(AvatarOption avatarOption) {
        transformAtomActions(avatarOption);
        Log.d(TAG, "[doAvatarBehavior] isRepeat: " + isRepeat + " action list: " + new Gson().toJson(mActionOptions));
        mBehaviorExecuteManager.addBehavior(mActionOptions, isRepeat);
    }

    /**
     * transform atom actions.
     *
     * @param AvatarOption avatarOption
     */
    private void transformAtomActions(AvatarOption avatarOption) {
        String actionId = avatarOption.getActionId();
        String avatarName = avatarOption.getAvatarName();
        int displayId = avatarOption.getDisplayId();
        int avatarType = avatarOption.getAvatarType();
        mActionOptions.clear();
        if (cacheAvatarBehaviors.containsKey(avatarName)) {
            BehaviorInfo behaviorInfo = cacheAvatarBehaviors.get(avatarName);
            assert behaviorInfo != null;
            isRepeat = behaviorInfo.isRepeat();
            mActionOptions.addAll(behaviorInfo.getList().stream().map(name ->
                    new AvatarOption(actionId, name, displayId, avatarType)
            ).collect(Collectors.toList()));
        } else {
            isRepeat = false;
            mActionOptions.add(new AvatarOption(actionId, avatarName, displayId, avatarType));
        }
    }

}
