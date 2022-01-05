package cn.cnlee.demo.databindingrecyclerview.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

import cn.cnlee.demo.databindingrecyclerview.R;
import cn.cnlee.demo.databindingrecyclerview.TestMutiStateButtonActivity;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/12/16
 * @Version 1.0
 */
public class MultiStateButton extends AppCompatButton {

    private static final String TAG = MultiStateButton.class.getSimpleName();
    private State mCurrentState;
    private int mProgress = 50; //当前进度
    private int mMaxProgress = 100; //最大进度：默认为100
    private int mMinProgress = 0;//最小进度：默认为0
    private GradientDrawable mProgressDrawable;// 加载进度时的进度颜色
    private GradientDrawable mProgressDrawableBg;// 加载进度时的背景色
    private Drawable mNormalDrawable; // 按钮在不同状态的颜色效果
    private boolean isShowProgress;  //是否展示进度
    private boolean isFinish; // 结束状态
    private boolean isStop;// 停止状态
    private boolean isStart; // 刚开始的状态
    private MultiStateButton2.OnStateListener onStateListener; //结束时的监听

    public MultiStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultiStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        // 初始化按钮状态Drawable
    }

    // 设置按钮背景
    private void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        setBackground(drawable);
        setPadding(pL, pT, pR, pB);
    }

    // 获取状态Drawable的正常状态下的背景
    private Drawable getBlueDrawable() {
        Drawable drawableNormal = getResources().getDrawable(R.drawable.normal_btn_selector, null);//
        // 修改时就不会影响其它drawable对象的状态
        return drawableNormal;
    }

    private Drawable getBlurDrawable() {
        Drawable drawableNormal = getResources().getDrawable(R.drawable.avatar_disable_btn, null);//
        // 修改时就不会影响其它drawable对象的状态
        return drawableNormal;
    }

    public void setState(State state) {
        Log.d(TAG, "state: " + state + " ,mCurrentState: " + mCurrentState);
        if (mCurrentState != state) {
            String stateTxt = state.getName();
            Log.d(TAG, "stateTxt: " + stateTxt);
            setButtonBg(state);
            setDrawableIcon(state);
            setText(stateTxt);
            mCurrentState = state;
        }
    }

    private void setDrawableIcon(State state) {
        Drawable drawableIcon = null;
        switch (state) {
            case DOWNLOADING:
                drawableIcon = getResources().getDrawable(R.drawable.icon_play, null);
                break;
            case PAUSE_DOWNLOAD:
                drawableIcon = getResources().getDrawable(R.drawable.icon_pause, null);
                break;
        }
        if (drawableIcon != null) {
            drawableIcon.setBounds(0, 0, drawableIcon.getMinimumWidth(), drawableIcon.getMinimumHeight());
        }
        setCompoundDrawables(drawableIcon, null, null, null);
    }

    private void setButtonBg(State state) {
        switch (state) {
            case SWITCHING:
            case DOWNLOADED:
            case NOT_DOWNLOADED:
                setBackgroundCompat(getBlueDrawable());
                break;
            case NOT_ACTIVE:
            case IN_USE:
            case WAIT_DOWNLOAD:
            case DOWNLOADING:
            case PAUSE_DOWNLOAD:
                setBackgroundCompat(getBlurDrawable());
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TestMutiStateButtonActivity.class.getSimpleName(), "=======onDraw====");

        if (mProgress > mMinProgress && mProgress <= mMaxProgress && !isFinish) {

//            // 更新进度：
//            float scale = (float) getProgress() / (float) mMaxProgress;
//            //计算 src 矩形的右边界
//            float indicatorWidth = (float) getMeasuredWidth() * scale;
//
//
//            mProgressDrawable.setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
//
//            mProgressDrawable.draw(canvas);

            RectF mBackgroundBounds = new RectF();
            mBackgroundBounds.left = 0;
            mBackgroundBounds.top = 0;
            mBackgroundBounds.right = getMeasuredWidth();
            mBackgroundBounds.bottom = getMeasuredHeight();
            Paint mBackgroundPaint = new Paint();
//
//            mBackgroundPaint.setColor(Color.GRAY);
            canvas.save();
//
            //计算 src 矩形的右边界
            float right = getMeasuredWidth() * mProgress / (mMaxProgress + 0f);

            //画出dst图层
            mBackgroundPaint.setColor(Color.parseColor("#00000000"));
            canvas.drawRoundRect(mBackgroundBounds, dp2px(20), dp2px(20), mBackgroundPaint);

            //设置图层显示模式为 SRC_ATOP
            PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
            mBackgroundPaint.setColor(Color.parseColor("#FF0088CC"));
            mBackgroundPaint.setXfermode(porterDuffXfermode);
            Log.d(TAG, "=======onDraw==== right: " + right + " === progress: " + mProgress + " , " + getMeasuredWidth());
            //在dst画出src矩形
            canvas.drawRect(mBackgroundBounds.left, mBackgroundBounds.top, right, mBackgroundBounds.bottom, mBackgroundPaint);
            canvas.restore();
            mBackgroundPaint.setXfermode(null);

//            //设置图层显示模式为 SRC_ATOP
//            PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
//            mBackgroundPaint.setColor(Color.parseColor("#FF0088CC"));
//            mBackgroundPaint.setXfermode(porterDuffXfermode);
//            //计算 src 矩形的右边界
//            float right = mBackgroundBounds.right * mProgress / (mMaxProgress + 0f);
//            //在dst画出src矩形
//            canvas.drawRect(mBackgroundBounds.left, mBackgroundBounds.top, right, mBackgroundBounds.bottom,
//                    mBackgroundPaint);
//            canvas.drawRoundRect(mBackgroundBounds.left, mBackgroundBounds.top, right, mBackgroundBounds.bottom,
//                    dp2px(20), dp2px(20),
//                    mBackgroundPaint);
//            canvas.restore();
//            mBackgroundPaint.setXfermode(null);

//            // 进度完成时回调方法，并更变状态
//            if (mProgress == mMaxProgress) {
//                setBackgroundCompat(mProgressDrawable);
//                isFinish = true;
//                if (onStateListener != null) {
//                    onStateListener.onFinish();
//                }
//
//            }

        }
        super.onDraw(canvas);
    }

    // 获取进度
    public int getProgress() {
        return mProgress;
    }

    // 设置进度信息
    public void setProgress(int progress) {
        Log.d(TAG, "======progress== " + progress);
        if (!isFinish && !isStop) {
            mProgress = progress;
            if (isShowProgress) setText(mProgress + " %");
            // 设置背景
            setBackgroundCompat(mProgressDrawableBg);
            invalidate();
        }

    }

    // 设置状态监听接口
    interface OnStateListener {

        void onFinish();

        void onStop();

        void onContinue();

    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}

