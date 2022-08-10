package cn.cnlee.demo.constrainlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/8/10
 * @Version 1.0
 */
public class FixedMaxWidthTextView extends TextView {

    private static final String TAG = FixedMaxWidthTextView.class.getSimpleName();
    private float mLimitWidth;

    public FixedMaxWidthTextView(Context context) {
        this(context, null);
    }

    public FixedMaxWidthTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedMaxWidthTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FixedMaxWidthTextView);
        mLimitWidth = array.getDimension(R.styleable.FixedMaxWidthTextView_limit_max_width, 0f);
        String ellipsizeStyle = array.getString(R.styleable.FixedMaxWidthTextView_ellipsize_style);
        String ellipsizePos = array.getString(R.styleable.FixedMaxWidthTextView_ellipsize_pos);
        Log.d(TAG, "limitWidth: " + mLimitWidth
                + " ,ellipsizeStyle: " + ellipsizeStyle + " ,ellipsizePos: " + ellipsizePos);
        if (mLimitWidth <= 0) {
            throw new IllegalArgumentException("limit width don't less than zero!!");
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.d(TAG, "setText. text: " + text + " ,BufferType: " + type);
        text = getHandleText(text);
        super.setText(text, type);
    }

    private float getNeedWidth(CharSequence text) {
        TextPaint textPaint = this.getPaint();
        float needWidth = textPaint.measureText(text.toString());
        float desiredWidth = Layout.getDesiredWidth(text, textPaint);
        Log.d(TAG, "getNeedWidth. text: " + text + " ,needWidth: " + needWidth + " ,desiredWidth: " + desiredWidth);
        return desiredWidth;
    }

    private CharSequence getHandleText(CharSequence originText) {
        CharSequence newText = originText;
        float desiredWidth = getNeedWidth(originText);
        mLimitWidth = 770.f;
        if (mLimitWidth < desiredWidth) {
            float sumWidth = 0f;
            int sumLen = originText.length();
            for (int i = sumLen - 1; i >= 0; i--) {
                String charTxt = String.valueOf(originText.charAt(i));
                float charWidth = getNeedWidth(charTxt);
                sumWidth += charWidth;
                if (sumWidth >= mLimitWidth) {
                    newText = originText.subSequence(i, sumLen);
                    Log.d(TAG, "getHandleText. sumLen: " + sumLen + " ,i: " + i + " ,sumWidth: "
                            + sumWidth + " ,maxWidth: " + getMaxWidth() + " ,newText: " + newText);
                    break;
                }
            }
        }
        return newText;
    }
}
