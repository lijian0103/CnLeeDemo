package cn.cnlee.demo.databindingrecyclerview.ui;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import java.text.DecimalFormat;

import cn.cnlee.demo.databindingrecyclerview.R;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/12/16
 * @Version 1.0
 */
public class MultiStateButton extends AppCompatTextView {

    //背景画笔
    private Paint mBackgroundPaint;
    //按钮文字画笔
    private volatile Paint mTextPaint;

    //背景颜色
    private int mBackgroundColor;
    //下载中后半部分后面背景颜色
    private int mBackgroundSecondColor;
    //文字颜色
    private int mTextColor;
    //覆盖后颜色
    private int mTextCoverColor;

    private int mTextSize;

    private float mButtonRadius;
    //边框宽度
    private float mBorderWidth;

    private float mProgress = -1;
    private float mToProgress;
    private int mMaxProgress;
    private int mMinProgress;
    private float mProgressPercent;

    //是否显示边框，默认是true
    private boolean showBorder;
    private RectF mBackgroundBounds;
    private LinearGradient mProgressTextGradient;

    //下载平滑动画
    private ValueAnimator mProgressAnimation;

    //记录当前文字
    private CharSequence mCurrentText;

    public static final int STATE_NORMAL = 0;//开始下载
    public static final int STATE_DOWNLOADING = 1;//下载之中
    public static final int STATE_PAUSE = 2;//暂停下载
    public static final int STATE_FINISH = 3;//下载完成

    private State mState;

    public MultiStateButton(Context context) {
        this(context, null);
    }

    public MultiStateButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initAttrs(context, attrs);
            init();
            setupAnimations();
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.multi_state_button);
        try {
            mBackgroundColor = a.getColor(R.styleable.multi_state_button_progressBtnColor, Color.parseColor("#3385FF"));
            mBackgroundSecondColor = a.getColor(R.styleable.multi_state_button_progressBtnBgColor, Color.parseColor("#E8E8E8"));
            mButtonRadius = a.getDimension(R.styleable.multi_state_button_progressBtnRadius, 0);
            mTextColor = a.getColor(R.styleable.multi_state_button_progressBtnTextColor, mBackgroundColor);
            mTextCoverColor = a.getColor(R.styleable.multi_state_button_progressBtnTextCoverColor, Color.WHITE);
            mTextSize = (int) a.getDimension(R.styleable.multi_state_button_progressBtnTextSize, 16);
            mBorderWidth = a.getDimension(R.styleable.multi_state_button_progressBtnBorderWidth, dp2px(2));
        } finally {
            a.recycle();
        }
    }

    private void init() {

        mMaxProgress = 100;
        mMinProgress = 0;
        mProgress = 0;

        showBorder = false;

        //设置背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        //设置文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(sp2px(mTextSize));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //解决文字有时候画不出问题
            setLayerType(LAYER_TYPE_SOFTWARE, mTextPaint);
        }

        //初始化状态设为NORMAL
        mState = State.NOT_ACTIVE;
        invalidate();
    }

    private void setupAnimations() {
        //ProgressBar的动画
        mProgressAnimation = ValueAnimator.ofFloat(0, 1).setDuration(500);
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float timePercent = (float) animation.getAnimatedValue();
                mProgress = ((mToProgress - mProgress) * timePercent + mProgress);
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            drawing(canvas);
        }
    }

    private void drawing(Canvas canvas) {
        drawBackground(canvas);
        drawTextAbove(canvas);
    }

    private void drawBackground(Canvas canvas) {

        mBackgroundBounds = new RectF();
        //根据Border宽度得到Button的显示区域
        mBackgroundBounds.left = showBorder ? mBorderWidth : 0;
        mBackgroundBounds.top = showBorder ? mBorderWidth : 0;
        mBackgroundBounds.right = getMeasuredWidth() - (showBorder ? mBorderWidth : 0);
        mBackgroundBounds.bottom = getMeasuredHeight() - (showBorder ? mBorderWidth : 0);

        if (showBorder) {
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setColor(mBackgroundColor);
            mBackgroundPaint.setStrokeWidth(mBorderWidth);
            canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
        }
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        //color
        switch (mState) {
            case DOWNLOADED:
            case NOT_DOWNLOADED:
            case SWITCHING:
            case WAIT_DOWNLOAD:
                mBackgroundPaint.setColor(mBackgroundColor);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
            case DOWNLOADING:
            case PAUSE_DOWNLOAD:
                //计算当前的进度
                mProgressPercent = mProgress / (mMaxProgress + 0f);
                mBackgroundPaint.setColor(mBackgroundSecondColor);
                canvas.save();
                //画出dst图层
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                //设置图层显示模式为 SRC_ATOP
                PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
                mBackgroundPaint.setColor(mBackgroundColor);
                mBackgroundPaint.setXfermode(porterDuffXfermode);
                //计算 src 矩形的右边界
                float right = mBackgroundBounds.right * mProgressPercent;
                //在dst画出src矩形
                canvas.drawRect(mBackgroundBounds.left, mBackgroundBounds.top, right, mBackgroundBounds.bottom, mBackgroundPaint);
                canvas.restore();
                mBackgroundPaint.setXfermode(null);
                break;
            case IN_USE:
                mBackgroundPaint.setColor(mBackgroundColor);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
            case NOT_ACTIVE:
                mBackgroundPaint.setColor(mBackgroundSecondColor);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
        }
    }

    private void drawTextAbove(Canvas canvas) {
        //计算Baseline绘制的Y坐标
        final float y = canvas.getHeight() / 2 - (mTextPaint.descent() / 2 + mTextPaint.ascent() / 2);
        mCurrentText = mState.getName();
        if (mCurrentText == null) {
            mCurrentText = "";
        }
        final float textWidth = mTextPaint.measureText(mCurrentText.toString());
        //color
        switch (mState) {
            case DOWNLOADED:
            case NOT_DOWNLOADED:
            case SWITCHING:
            case WAIT_DOWNLOAD:
                mTextPaint.setShader(null);
                mTextPaint.setColor(mTextCoverColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case DOWNLOADING:
            case PAUSE_DOWNLOAD:

                //进度条压过距离
                float coverLength = getMeasuredWidth() * mProgressPercent;
                //开始渐变指示器
                float indicator1 = getMeasuredWidth() / 2 - textWidth / 2;
                //结束渐变指示器
                float indicator2 = getMeasuredWidth() / 2 + textWidth / 2;
                //文字变色部分的距离
                float coverTextLength = textWidth / 2 - getMeasuredWidth() / 2 + coverLength;
                float textProgress = coverTextLength / textWidth;
                if (coverLength <= indicator1) {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextColor);
                } else if (indicator1 < coverLength && coverLength <= indicator2) {
                    //设置变色效果
                    mProgressTextGradient = new LinearGradient((getMeasuredWidth() - textWidth) / 2, 0, (getMeasuredWidth() + textWidth) / 2, 0,
                            new int[]{mTextCoverColor, mTextColor},
                            new float[]{textProgress, textProgress + 0.001f},
                            Shader.TileMode.CLAMP);
                    mTextPaint.setColor(mTextColor);
                    mTextPaint.setShader(mProgressTextGradient);
                } else {
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextCoverColor);
                }
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case IN_USE:
                mTextPaint.setColor(mTextCoverColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case NOT_ACTIVE:
                mTextPaint.setColor(mTextColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;

        }

    }


    public State getState() {
        return mState;
    }

    public void setState(State state) {
        if (mState != state) {//状态确实有改变
            this.mState = state;
            invalidate();
        }
    }

    /**
     * 设置当前按钮文字
     */
    public void setCurrentText(CharSequence charSequence) {
        mCurrentText = charSequence;
        invalidate();
    }


    /**
     * 设置带下载进度的文字
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setProgressText(String text, float progress) {
        if (progress >= mMinProgress && progress <= mMaxProgress) {
            DecimalFormat format = new DecimalFormat("##0.0");
            mCurrentText = text + format.format(progress) + "%";
            mToProgress = progress;
            if (mProgressAnimation.isRunning()) {
                mProgressAnimation.resume();
                mProgressAnimation.start();
            } else {
                mProgressAnimation.start();
            }
        } else if (progress < mMinProgress) {
            mProgress = 0;
        } else if (progress > mMaxProgress) {
            mProgress = 100;
            mCurrentText = text + progress + "%";
            invalidate();
        }
    }


    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int width) {
        this.mBorderWidth = dp2px(width);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
    }

    public float getButtonRadius() {
        return mButtonRadius;
    }

    public void setButtonRadius(float buttonRadius) {
        mButtonRadius = buttonRadius;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    public int getTextCoverColor() {
        return mTextCoverColor;
    }

    public void setTextCoverColor(int textCoverColor) {
        mTextCoverColor = textCoverColor;
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


    private int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getContext().getResources().getDisplayMetrics());
    }
//    public int sp2px(float spValue) {
//        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
}

