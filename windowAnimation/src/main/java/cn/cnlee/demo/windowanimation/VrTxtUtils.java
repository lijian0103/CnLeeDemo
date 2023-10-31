package cn.cnlee.demo.windowanimation;

import android.content.res.Resources;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/7/28
 * @Version 1.0
 */
public class VrTxtUtils {

    private static final String TAG = VrTxtUtils.class.getSimpleName();

    public static float getTxtWidth(TextView tv, String txt) {
        float length;
        TextPaint textPaint = tv.getPaint();
        length = textPaint.measureText(txt);
        Log.d(TAG, "getTxtWidth: " + length + " -- txt: " + txt);
        Log.d(TAG, "getDesiredWidth: " + Layout.getDesiredWidth(txt, textPaint) + " -- needDp: " + px2dp(Layout.getDesiredWidth(txt, textPaint)));
        return length;
    }

    public static float getTxtWidth2(String txt, int txtSize) {
        float length;
        TextPaint textPaint = new TextPaint();
        float size = spToPx(txtSize);
        textPaint.setTextSize(size);
        length = textPaint.measureText(txt);
        Log.d(TAG, "getTxtWidth2: " + length + " ,textSize: " + size + " -- txt: " + txt);
        Log.d(TAG, "getDesiredWidth2: " + Layout.getDesiredWidth(txt, textPaint) + " -- needDp: " + px2dp(Layout.getDesiredWidth(txt, textPaint)));
        return length;
    }

    public static float getSumOfTxt(TextView tv, String txt) {
        float length = 0;
        for (int i = 0; i < txt.length(); i++) {
            String ss = String.valueOf(txt.charAt(i));
            length += getTxtWidth(tv, ss);
//            Log.d(TAG, "getSumOfTxt: " + length + " ,ss: " + ss);
        }
        Log.d(TAG, "getSumOfTxt: " + length);
        return length;
    }

    public static float getSumOfTxt2(String txt, int txtSize) {
        float length = 0;
        for (int i = 0; i < txt.length(); i++) {
            String ss = String.valueOf(txt.charAt(i));
            length += getTxtWidth2(ss, txtSize);
//            Log.d(TAG, "getSumOfTxt2: " + length + " ,ss: " + ss);
        }
        Log.d(TAG, "getSumOfTxt2: " + length);
        return length;
    }

    public static float spToPx(int spValue) {
        float fontScale = AppApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    public static float dpToPx(float dp) {
        float density = AppApplication.getInstance().getResources().getDisplayMetrics().density;
        return (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    public static int px2dp(final float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
