package cn.cnlee.demo.windowanimation;

/**
 * @Description 渐变文字
 * @Author cnlee
 * @Date 2023/8/5
 * @Version 1.0
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

public class GradientTextView extends AppCompatTextView {
    private LinearGradient gradient;
    private TextPaint textPaint;

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setShader() {
        textPaint = getPaint();
        String text = getText().toString();
        float textWidth = textPaint.measureText(text);
        Log.d("setShader", "textWidth: " + textWidth);
        float startX = 0, startY = 0, endX = textWidth, endY = 0;
        gradient = new LinearGradient(startX, startY, endX, endY,
                new int[]{Color.TRANSPARENT, Color.TRANSPARENT, Color.argb(200, 255, 255, 255), Color.WHITE},
                null, Shader.TileMode.CLAMP);
        textPaint.setShader(gradient);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        setShader();
    }
}
