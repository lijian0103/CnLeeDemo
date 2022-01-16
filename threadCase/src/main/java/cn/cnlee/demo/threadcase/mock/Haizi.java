package cn.cnlee.demo.threadcase.mock;

import android.os.SystemClock;

import cn.cnlee.demo.threadcase.uitls.LogUtils;
import cn.cnlee.demo.threadcase.uitls.ThreadUtils;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
public class Haizi {

    private static final String TAG = Haizi.class.getSimpleName();

    public static void play_anim_clip(String name, IPlayAsset callback) {
        ThreadUtils.getWorkHandler(ThreadUtils.TAG_HAIZI).post(() -> {
            LogUtils.e(TAG, "play_anim_clip start: " + name);
            SystemClock.sleep(2000);
            LogUtils.e(TAG, "play_anim_clip end: " + name);
            callback.onPlayEnd(true);
        });
    }

    public static void play_anim_body(String name, IPlayAsset callback) {

    }

    public static void play_anim_face(String name, IPlayAsset callback) {

    }

}
