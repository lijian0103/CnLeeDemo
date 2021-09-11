package cn.cnlee.demo.constrainlayout;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/9/11
 * @Version 1.0
 */
public class ViewBinderAdapter {

    private final static String TAG = ViewBinderAdapter.class.getSimpleName();

    @BindingAdapter("setVoiceIcon")
    public static void setVoiceIcon(ImageView iv, int voiceType) {
        Log.e(TAG, "setVoiceIcon: " + voiceType);
        switch (voiceType) {
            case UiViewModel.VoiceType.IDLE: {
                Log.e(TAG, "===IDLE==");
                return;
            }
            case UiViewModel.VoiceType.TTS: {
                Log.e(TAG, "===TTS==");
                iv.setImageResource(R.mipmap.sound_wave);
                break;
            }
            case UiViewModel.VoiceType.LISTEN: {
                Log.e(TAG, "===LISTEN==");
                iv.setImageResource(R.mipmap.listen);
                break;
            }
            default:
                break;
        }
        iv.setVisibility(View.VISIBLE);
    }
}
