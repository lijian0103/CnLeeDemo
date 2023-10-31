/*
 * Copyright © 2020 SAIC MOTOR Z-ONE SOFTWARE COMPANY. All rights reserved.
 */

package cn.cnlee.demo.windowanimation;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;



/**
 * Util class for WindowManager
 *
 * @author liangchen
 * @version 1.0.0
 * @date 20201211
 */
public class WindowManagerUtils {
    /**
     * add a view into WindowManager according to the layout params.
     *
     * @param context context
     * @param view    view to be added
     */
    public static void addViewIntoWindowManager(Context context, View view, WindowManager.LayoutParams params) {
        // 获取WindowManager
        final WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mWindowManager.addView(view, params);
    }

    /**
     * add a view into WindowManager according to the layout params.
     *
     * @param context context
     * @param view    view to be added
     */
    public static void addViewIntoWindowManager(Context context,
                                                int displayId, View view, WindowManager.LayoutParams params) {
        // 获取WindowManager
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager == null) {
            Toast.makeText(context,"DisplayManager is null", Toast.LENGTH_SHORT);
            return;
        }
        Display targetDisplay = displayManager
                .getDisplay(displayId);
        if (targetDisplay == null) {
            Toast.makeText(context,"display is null", Toast.LENGTH_SHORT);
            return;
        }
        Context targetContext = context.createDisplayContext(targetDisplay);
        if (targetContext == null) {
            Toast.makeText(context,"display context is null", Toast.LENGTH_SHORT);
            return;
        }
        WindowManager targetWindowManager = (WindowManager) targetContext
                .getSystemService(Context.WINDOW_SERVICE);
        if (targetWindowManager == null) {
            Toast.makeText(context,"window manager is null", Toast.LENGTH_SHORT);
            return;
        }

        targetWindowManager.addView(view, params);
    }

    /**
     * update window layout params.
     *
     * @param context context
     * @param view    view to be updated
     */
    public static void updateWindowLayoutParams(Context context,
                                                int displayId, View view, WindowManager.LayoutParams params) {
        // 获取WindowManager
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager == null) {
            Toast.makeText(context,"DisplayManager is null", Toast.LENGTH_SHORT);
            return;
        }
        Display targetDisplay = displayManager
                .getDisplay(displayId);
        if (targetDisplay == null) {
            Toast.makeText(context,"display is null", Toast.LENGTH_SHORT);
            return;
        }
        Context targetContext = context.createDisplayContext(targetDisplay);
        if (targetContext == null) {
            Toast.makeText(context,"display context is null", Toast.LENGTH_SHORT);
            return;
        }
        WindowManager targetWindowManager = (WindowManager) targetContext
                .getSystemService(Context.WINDOW_SERVICE);
        if (targetWindowManager == null) {
            Toast.makeText(context,"window manager is null", Toast.LENGTH_SHORT);
            return;
        }

        targetWindowManager.updateViewLayout(view, params);
    }


    /**
     * remove a view from WindowManager.
     *
     * @param context context
     * @param view    view to be removed
     */
    public static void removeViewFromWindowManager(Context context, View view) {
        // 获取WindowManager
        final WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.removeView(view);
    }

    /**
     * get a default layout params, a new instance will be created every time.
     *
     * @return a new copy of default layout params
     */
    public static WindowManager.LayoutParams getDefaultWindowLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = getDefaultOverlayWindowType();
        // 设置flag
        params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        return params;
    }

    /**
     * get a default window type that uses the permission android.permission.SYSTEM_ALERT_WINDOW.
     *
     * @return overlay window type
     */
    public static int getDefaultOverlayWindowType() {
        // 类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            return WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
    }

}
