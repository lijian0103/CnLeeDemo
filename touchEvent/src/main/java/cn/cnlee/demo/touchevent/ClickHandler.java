package cn.cnlee.demo.touchevent;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.function.Consumer;

/**
 * @Description Multiple consecutive clicks count as one double click event.
 * @Author cnlee
 * @Date 2021/8/14
 * @Version 1.0
 */
public class ClickHandler {
    private static final String TAG = ClickHandler.class.getSimpleName();
    private long mFirstTime = 0;
    private long mClickTimes = 0;
    private final Consumer<Boolean> mConsumer;
    private static final int DOUBLE_TAP_TIMEOUT = 300;
    private boolean isDoubleClick = false;
    private boolean mQuickMultipleAsOnce = false;


    public ClickHandler(Consumer<Boolean> consumer) {
        this(consumer, false);
    }

    public ClickHandler(Consumer<Boolean> consumer, boolean quickMultipleAsOnce) {
        this.mConsumer = consumer;
        this.mQuickMultipleAsOnce = quickMultipleAsOnce;
    }

    private final CountDownTimer mClickTimer = new CountDownTimer(DOUBLE_TAP_TIMEOUT, DOUBLE_TAP_TIMEOUT) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Log.e(TAG, "=====onFinish====" + mClickTimes);
            if (mQuickMultipleAsOnce) {
                specialHandleDoubleClick();
            } else {
                normalHandleDoubleClick();
            }

        }
    };

    /**
     * special handle double click event.
     * quick multiple double click as one double click event.
     */
    private void specialHandleDoubleClick() {
        if (!isDoubleClick && mClickTimes == 1) {
            mConsumer.accept(false);
        } else if (isDoubleClick && mClickTimes == 0) {
            mConsumer.accept(true);
            isDoubleClick = false;
        }
        boolean needRestartTimer = (!isDoubleClick && mClickTimes > 1) || (isDoubleClick && mClickTimes > 0);
        cancelTimer();
        if (needRestartTimer) {
            isDoubleClick = true;
            startTimer();
        }
    }

    /**
     * normal handle double click event.
     */
    private void normalHandleDoubleClick() {
        mConsumer.accept(mClickTimes > 1);
        cancelTimer();
    }

    private void startTimer() {
        Log.e(TAG, "=====startTimer====");
        mFirstTime = System.currentTimeMillis();
        mClickTimer.start();
    }

    private void cancelTimer() {
        Log.e(TAG, "=====cancelTimer====");
        mFirstTime = 0;
        mClickTimes = 0;
        mClickTimer.cancel();
    }

    /**
     * onClick.
     */
    public void onClick() {
        if (mConsumer == null) {
            Log.e(TAG, "no callback function argument!");
            return;
        }
        mClickTimes++;
        if (mFirstTime == 0) {
            startTimer();
        }
    }
}
