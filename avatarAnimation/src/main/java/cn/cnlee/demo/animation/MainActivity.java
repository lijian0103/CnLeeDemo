package cn.cnlee.demo.animation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cnlee.demo.animation.avatar.Avatar;
import cn.cnlee.demo.animation.avatar.AvatarBehaviorEngine;
import cn.cnlee.demo.animation.avatar.AvatarOption;

public class MainActivity extends AppCompatActivity implements Avatar.ActionCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    AvatarBehaviorEngine mAvatarBehaviorEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAvatarBehaviorEngine = new AvatarBehaviorEngine(this);
        mAvatarBehaviorEngine.setActionCallback(this);
        AvatarOption avatarOption = new AvatarOption("100-1", "listen", 2, 1);
        mAvatarBehaviorEngine.doAvatarBehavior(avatarOption);
    }

    @Override
    public void onCallback(AvatarOption option, int status, String errMsg) {
        Log.e(TAG, "[onCallback] " + option + " status: " + status + " errMsg: " + errMsg);
    }

    @OnClick({R.id.btn_cancel_action, R.id.btn_new_action})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_action: {
                Log.d(TAG, "btn_cancel_action onClick");
                AvatarOption avatarOption = new AvatarOption("100-1", "sleep", 2, 1);
                mAvatarBehaviorEngine.doAvatarBehavior(avatarOption);
                break;
            }
            case R.id.btn_new_action: {
                Log.d(TAG, "btn_new_action onClick");
                AvatarOption avatarOption = new AvatarOption("101-2", "bixin", 1, 2);
                mAvatarBehaviorEngine.doAvatarBehavior(avatarOption);
                break;
            }
            default:
                break;
        }
    }
}