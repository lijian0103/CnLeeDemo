package cn.cnlee.demo.windowanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * @Description window animal
 * @Author cnlee
 * @Date 2023/8/4
 * @Version 1.0
 */
public class FloatingWindowService extends Service {

    private final static String TAG = FloatingWindowService.class.getSimpleName();
    private WindowManager windowManager;
    private View floatingView;
    private ViewGroup container;
    private TextView textView;
    WindowManager.LayoutParams mLayoutParams;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate");
        // 初始化悬浮窗
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(this);
        floatingView = inflater.inflate(R.layout.floating_window_layout, null);
        container = floatingView.findViewById(R.id.container);
        textView = floatingView.findViewById(R.id.tv2);

        // 设置悬浮窗布局参数
        mLayoutParams = new WindowManager.LayoutParams(
                784,
                (int) VrTxtUtils.dpToPx(200),
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );
        mLayoutParams.gravity = Gravity.BOTTOM;
        mLayoutParams.x = 0;
        mLayoutParams.y = 250;

        disableAnimations();
        // 将悬浮窗视图添加到窗口管理器
        windowManager.addView(floatingView, mLayoutParams);

        new Handler(getMainLooper()).postDelayed(() -> {
            // 开始高度变化动画
            animateHeightDecrease();
        }, 2000);

//        animateTranslationYBy();
//        animateScaleXBy();
    }

    private void disableAnimations() {
        try {
            int currentFlags = (Integer) mLayoutParams.getClass().getField("privateFlags").get(mLayoutParams);
            mLayoutParams.getClass().getField("privateFlags").set(mLayoutParams, currentFlags | 0x00000040);
        } catch (Exception e) {
            //do nothing. Probably using other version of android
        }
    }

    private void disableAnimation(WindowManager.LayoutParams layoutParams) {
        String className = "android.view.WindowManager$LayoutParams";
        try {
            Class layoutParamsClass = Class.forName(className);
            Field privateFlags = layoutParamsClass.getField("privateFlags");
            Field noAnim = layoutParamsClass.getField("PRIVATE_FLAG_NO_MOVE_ANIMATION");
            int privateFlagsValue = privateFlags.getInt(layoutParams);
            int noAnimFlag = noAnim.getInt(layoutParams);
            privateFlagsValue |= noAnimFlag;
            privateFlags.setInt(layoutParams, privateFlagsValue);
            // Dynamically do stuff with this class
            // List constructors, fields, methods, etc.
        } catch (Exception exc) {
            Log.e("",exc.toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) {
            windowManager.removeView(floatingView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void animateTranslationYBy() {
//        WindowManager.LayoutParams layoutParams = floatingView.getLayoutParams();
        ViewPropertyAnimator animator = container.animate().translationYBy(200).setDuration(9000);
        int temp = mLayoutParams.height;
        animator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate:" + animation.getAnimatedFraction());

                mLayoutParams.height = temp - (int) (temp * animation.getAnimatedFraction());
//                WindowManagerUtils.updateWindowLayoutParams(FloatingWindowService.this, 0, floatingView, mLayoutParams);
            }
        });
        animator.start();//高度从上往下减少
//        textView.animate().translationYBy(-100).setDuration(9000).start();
//        floatingView.animate().translationYBy(-200).setDuration(2000).start();//高度从小往上减少
//        textView.animate().translationYBy(100).setDuration(2000).start();//高度从小往上减少
    }

    private void animateScaleXBy() {
        floatingView.animate().scaleXBy(200).setDuration(2000).start();

    }

    private void animateHeightDecrease() {
//         初始高度
        int startHeight = (int) VrTxtUtils.dpToPx(200);
        // 目标高度
        int targetHeight = startHeight - (int) VrTxtUtils.dpToPx(100); // 减少 100 像素

        Log.d("TAG", "animateHeightDecrease. startHeight: " + startHeight + " ,targetHeight: " + targetHeight);
        // 创建高度变化动画
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.setDuration(800); // 动画持续时间，单位为毫秒

        ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newHeight = (int) animation.getAnimatedValue();
                Log.d("TAG", "onAnimationUpdate: " + newHeight);
                layoutParams.height = newHeight;
                container.setLayoutParams(layoutParams);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                Log.d("TAG", "onAnimationEnd: " + isReverse);
                mLayoutParams.height = (int) VrTxtUtils.dpToPx(100);
//                floatingView.forceLayout();
                floatingView.invalidate();
                floatingView.requestLayout();

                new Handler(getMainLooper()).post(() -> {
                    // 重新布局悬浮窗
                    WindowManagerUtils.updateWindowLayoutParams(FloatingWindowService.this, 0, floatingView, mLayoutParams);

                });
                // 重新布局悬浮窗
//                WindowManagerUtils.updateWindowLayoutParams(FloatingWindowService.this, 0, floatingView, mLayoutParams);
            }
        });
        animator.start();
    }
}
