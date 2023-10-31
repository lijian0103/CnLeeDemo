package cn.cnlee.demo.constrainlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/10/26
 * @Version 1.0
 */
public class CustomView extends View {
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 屏幕宽度
     */
    private int screen_width;
    /**
     * 屏幕高度
     */
    private int screen_height;
    /**
     * 刷频次数
     */
    private int count;

    /**
     * 构造方法：将自定义视图绑定到窗口
     *
     * @param context
     */
    public CustomView(Context context) {
        super(context);
        /* 通过窗口管理器对象获得手机屏幕尺寸 */
        screen_width = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth();
        screen_height = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getHeight();
    }

    /**
     * 绘制方法：利用画笔对象在画布上绘制文本、图形和图像
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 实例化画笔对象
        paint = new Paint();

        /* 将画布底色变成全黑 */
        // 设置画笔为黑色
        paint.setColor(Color.BLACK);
        canvas.drawRect(new Rect(0, 0, screen_width, screen_height), paint);

        /* 根据刷屏次数来设置画笔颜色 */
        switch (count) {
            case 0:
                // 画笔设置为绿色
                paint.setColor(Color.GREEN);
                break;
            case 1:
                // 画笔设置为白色
                paint.setColor(Color.WHITE);
                break;
            case 2:
                // 画笔设置为黄色
                paint.setColor(Color.YELLOW);
                break;
        }

        // 确保count只能取0、1、2
        count = ++ count % 3;

        for (int i = 0; i < 200; i++) {
            canvas.drawText("★", (int) (Math.random() * screen_width),
                    (int) (Math.random() * screen_height), paint);
        }

        // 绘制位图
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(new Rect(220, 90, 280, 150));
        drawable.draw(canvas);
    }
}
