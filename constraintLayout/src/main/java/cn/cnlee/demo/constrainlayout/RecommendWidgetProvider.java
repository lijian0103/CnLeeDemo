package cn.cnlee.demo.constrainlayout;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/7/31
 * @Version 1.0
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class RecommendWidgetProvider extends AppWidgetProvider {

    private static String TAG = RecommendWidgetProvider.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive, intent: " + intent.getAction());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        Log.d(TAG, "onUpdate, N: " + N);
        updateMainScreenWidget(context);

    }

    private void updateMainScreenWidget(Context context) {
        Log.d(TAG, "updateMainScreenWidget ");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recommend_layout);
        ComponentName widget = new ComponentName(context, RecommendWidgetProvider.class);
        setupSceneBtn(context, remoteViews, R.id.widget_btn_1);
        setupSceneBtn(context, remoteViews, R.id.widget_btn_2);


        appWidgetManager.updateAppWidget(widget, remoteViews);
    }

    private void setupSceneBtn(Context context, RemoteViews remoteViews, int resId) {
        Intent intent = new Intent();
        intent.setClass(context, RecommendWidgetProvider.class); //通过intent把广播发给TestWidget本身，TestWidget接受到广播之后，会调用onReceive()方法进而刷新界面。
        intent.setAction(resId + "");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(resId, pendingIntent);//控件btn_widget的点击事件：点击按钮时，会发一个带action的广播。
    }

}

