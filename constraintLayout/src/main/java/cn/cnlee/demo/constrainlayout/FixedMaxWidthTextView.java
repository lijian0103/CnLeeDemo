package cn.cnlee.demo.constrainlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
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
    private String mEllipsStyle;
    private String mEllipsPos;
    private CharSequence mCacheTxt;

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
        float maxWidth = getMaxWidth();
        mEllipsStyle = array.getString(R.styleable.FixedMaxWidthTextView_ellipsize_style);
        mEllipsPos = array.getString(R.styleable.FixedMaxWidthTextView_ellipsize_pos);
        array.recycle();
        Log.d(TAG, "init maxWidth: " + maxWidth
                + " ,ellipsizeStyle: " + mEllipsStyle + " ,ellipsizePos: " + mEllipsPos);
        if (maxWidth == Integer.MAX_VALUE || maxWidth <= 0) {
            throw new IllegalArgumentException("maxWidth set illegal number!!");
        }
//        getNeedWidth("...");//长度3、60.0
//        getNeedWidth("…");//长度1、77.0
//        getNeedWidth("\u2026");//长度1、77.0
//        getNeedWidth("‥");//长度1、77.0
//        getNeedWidth("\u2025");//长度1、77.0
//        getNeedWidth("");//长度0、0
//        getNeedWidth("\uFEFF");//长度1、0
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.d(TAG, "setText text: " + text + " ,BufferType: " + type);
        mCacheTxt = text;
        text = getHandleText(text);
        super.setText(text, type);
    }

    private float getNeedWidth(CharSequence text) {
        TextPaint textPaint = this.getPaint();
        float desiredWidth = Layout.getDesiredWidth(text, textPaint);
//        Log.d(TAG, "getNeedWidth",
//                "text: " + text + " ,desiredWidth: " + desiredWidth + " ,length: " + text.length());
        return desiredWidth;
    }

    private CharSequence getHandleText(CharSequence originText) {
        CharSequence newText = originText;
        float desiredWidth = getNeedWidth(originText);
        float maxWidth = getMaxWidth();
        if (maxWidth < desiredWidth) {
            newText = getCharSequence(originText, maxWidth);
        }
        return newText;
    }

    private CharSequence getCharSequence(CharSequence originText, float maxWidth) {
        String ellipsPos = getEllipsPos();
        Log.d(TAG, "getCharSequence ellipsPos: " + ellipsPos + " ,originText: " + originText);
        if ("none".equals(ellipsPos)) {
            return originText;
        }
        CharSequence newText = originText;
        float sumWidth = 0f;
        int sumLen = originText.length();
        float beforeSumWidth = 0f;
        String ellipsStyle = getEllipsStyle();
        float ellipsWidth = getNeedWidth(ellipsStyle);
        if ("start".equals(ellipsPos)) {
            for (int i = sumLen - 1; i >= 0; i--) {
                String charTxt = String.valueOf(originText.charAt(i));
                float charWidth = getNeedWidth(charTxt);
                sumWidth += charWidth;
                if (sumWidth > maxWidth - ellipsWidth) {
                    newText = originText.subSequence(i + 1, sumLen);
                    SpannableStringBuilder builder = new SpannableStringBuilder(newText);
                    newText = builder.insert(0, ellipsStyle);
                    Log.d(TAG, "getCharSequence sumLen: " + sumLen + " ,i: " + i + " ,sumWidth: "
                            + sumWidth + " ,beforeSumWidth: " + beforeSumWidth + " ,maxWidth: " + maxWidth + " ,"
                            + " ,ellipsWidth: " + ellipsWidth
                            + " ,newText: " + newText);
                    break;
                }
                beforeSumWidth = sumWidth;
            }
        } else {
            for (int i = 0; i < sumLen; i++) {
                String charTxt = String.valueOf(originText.charAt(i));
                float charWidth = getNeedWidth(charTxt);
                sumWidth += charWidth;
                if (sumWidth > maxWidth - ellipsWidth) {
                    newText = originText.subSequence(0, i);
                    SpannableStringBuilder builder = new SpannableStringBuilder(newText);
                    newText = builder.append(ellipsStyle);
                    Log.d(TAG, "getCharSequence sumLen: " + sumLen + " ,i: " + i + " ,sumWidth: "
                            + sumWidth + " ,beforeSumWidth: " + beforeSumWidth + " ,maxWidth: " + maxWidth + " ,"
                            + " ,ellipsWidth: " + ellipsWidth
                            + " ,newText: " + newText);
                    break;
                }
                beforeSumWidth = sumWidth;
            }
        }
        return newText;
    }

    public void setEllipsConfig(String pos, String style) {
        if (!getEllipsPos().equals(pos) || !getEllipsStyle().equals(style)) {
            mEllipsPos = pos;
            mEllipsStyle = style;
            Log.d(TAG, "setEllipsConfig ellipsPos: " + pos + " ,ellipsStyle: " + style);
            if (!TextUtils.isEmpty(mCacheTxt)) {
                setText(mCacheTxt);
            }
        }
    }

    private String getEllipsStyle() {
        if (TextUtils.isEmpty(mEllipsStyle)) {
            return "";
        }
        return mEllipsStyle;
    }

    private String getEllipsPos() {
        if (TextUtils.isEmpty(mEllipsPos)) {
            return "none";
        }
        return mEllipsPos;
    }
}

