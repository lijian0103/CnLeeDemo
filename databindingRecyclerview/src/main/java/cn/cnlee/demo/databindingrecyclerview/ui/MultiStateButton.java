package cn.cnlee.demo.databindingrecyclerview.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
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

    private int mProgress = 20; //当前进度
    private int mMaxProgress = 100; //最大进度：默认为100
    private int mMinProgress = 0;//最小进度：默认为0
    private GradientDrawable mProgressDrawable;// 加载进度时的进度颜色
    private GradientDrawable mProgressDrawableBg;// 加载进度时的背景色
    private StateListDrawable mNormalDrawable; // 按钮在不同状态的颜色效果
    private boolean isShowProgress;  //是否展示进度
    private boolean isFinish; // 结束状态
    private boolean isStop;// 停止状态
    private boolean isStart; // 刚开始的状态
    private OnStateListener onStateListener; //结束时的监听
    private float cornerRadius; // 圆角半径

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
        mNormalDrawable = new StateListDrawable();
        // 初始化进度条Drawable
        mProgressDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.rect_progress).mutate();
        // 初始化进度条背景Drawable
        mProgressDrawableBg = (GradientDrawable) getResources().getDrawable(R.drawable.rect_progress_bg).mutate();

        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.multi_state_button);
        try {

            // 默认的圆角大小
            float defValue = getResources().getDimension(R.dimen.corner_radius);
            // 获取圆角大小
            cornerRadius = attr.getDimension(R.styleable.multi_state_button_buttonCornerRadius, defValue);


            // 获取是否显示进度信息的属性
            isShowProgress = attr.getBoolean(R.styleable.multi_state_button_showProgressNum, true);

            // 给按钮的状态Drawable添加被点击时的状态
//            mNormalDrawable.addState(new int[]{android.R.attr.state_pressed}, getPressedDrawable(attr));

            // 给按钮的状态Drawable添加其他时候的状态
            mNormalDrawable.addState(new int[]{}, getNormalDrawable(attr));

            // 获取进度条颜色属性值
            int defaultProgressColor = getResources().getColor(R.color.use_blue);
            int progressColor = attr.getColor(R.styleable.multi_state_button_progressColor, defaultProgressColor);
            // 设置进度条Drawable的颜色
            mProgressDrawable.setColor(progressColor);

            // 获取进度条背景颜色属性值
            int defaultProgressBgColor = getResources().getColor(R.color.normal_gray);
            int progressBgColor = attr.getColor(R.styleable.multi_state_button_progressBgColor, defaultProgressBgColor);
            // 设置进度条背景Drawable的颜色
            mProgressDrawableBg.setColor(progressBgColor);
        } finally {
            attr.recycle();
        }

        // 初始化状态
        isFinish = false;
        isStop = false;
        isStart = false;

        // 设置圆角
        mProgressDrawable.setCornerRadius(cornerRadius);
        mProgressDrawableBg.setCornerRadius(cornerRadius);
        // 设置按钮背景为状态Drawable
        setBackgroundCompat(mNormalDrawable);
    }

    // 设置按钮背景
    private void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }

    // 获取状态Drawable的正常状态下的背景
    private Drawable getNormalDrawable(TypedArray attr) {
        GradientDrawable drawableNormal =
                (GradientDrawable) getResources().getDrawable(R.drawable.rect_progress).mutate();// 修改时就不会影响其它drawable对象的状态
        drawableNormal.setCornerRadius(cornerRadius); // 设置圆角半径
        int defaultNormal = getResources().getColor(R.color.use_blue);
        int colorNormal = attr.getColor(R.styleable.multi_state_button_buttonNormalColor, defaultNormal);
        drawableNormal.setColor(colorNormal);//设置颜色
        return drawableNormal;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TestMutiStateButtonActivity.class.getSimpleName(), "=======onDraw====");

        if (mProgress > mMinProgress && mProgress <= mMaxProgress && !isFinish) {

            // 更新进度：
            float scale = (float) getProgress() / (float) mMaxProgress;
            float indicatorWidth = (float) getMeasuredWidth() * scale;


            mProgressDrawable.setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());

            mProgressDrawable.draw(canvas);

            // 进度完成时回调方法，并更变状态
            if (mProgress == mMaxProgress) {
                setBackgroundCompat(mProgressDrawable);
                isFinish = true;
                if (onStateListener != null) {
                    onStateListener.onFinish();
                }

            }

        }
        super.onDraw(canvas);
    }

    // 获取进度
    public int getProgress() {
        return mProgress;
    }

    // 设置进度信息
    public void setProgress(int progress) {
        Log.d(MultiStateButton.class.getSimpleName(), "======progress== " + progress);
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

}

enum State {
    NOT_ACTIVE(0, "待激活"),       // 盲盒形象才有此状态 1，非游客，未激活 2，游客
    IN_USE(1, "使用中"),           // 正在使用
    SWITCHING(2, "切换中"),        // 正在切换中
    DOWNLOADED(3, "使用"),         // 盲盒形象，激活并下载完成
    NOT_DOWNLOADED(4, "下载"),     // 盲盒形象，已激活未下载
    WAIT_DOWNLOAD(5, "等待下载"),   // 等待下载，已点击下载但还没进度
    DOWNLOADING(6, "暂停下载"),     // 带进度条，开始下载，有进度出现时
    PAUSE_DOWNLOAD(7, "继续下载");  // 带进度条，暂停下载时

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    State(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
