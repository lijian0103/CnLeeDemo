package cn.cnlee.demo.copyassetfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import cn.cnlee.demo.util.AssetsHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandlerThread fileHandlerThread = new HandlerThread("FileThread");
        fileHandlerThread.start();
        Handler fileHandler = new Handler(fileHandlerThread.getLooper());
        fileHandler.post(this::copyAssetsToSdCard);
    }

    private void copyAssetsToSdCard() {
        Log.d(TAG, "copyAssetsToSdCard start.");
        AssetsHelper.copyFileOrDir("0");
//        AssetsHelper.copyAssetsToSdCard("1.jpg");
//        AssetsHelper.copyAssetsToSdCard("1000");
        Log.d(TAG, "copyAssetsToSdCard end.");
    }

}