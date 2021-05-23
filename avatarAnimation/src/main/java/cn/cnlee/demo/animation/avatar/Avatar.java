package cn.cnlee.demo.animation.avatar;

import android.os.SystemClock;
import android.util.Log;

public class Avatar {

    public void performAction(AvatarOption option, ActionCallback cb) {
        Log.d(Avatar.class.getSimpleName(), "[performanceAction]." + option.getAvatarName());
        SystemClock.sleep(800);
        cb.onCallback(option, 4, "some msg");
    }

    public interface ActionCallback {
        void onCallback(AvatarOption option, int status, String errMsg);
    }
}
