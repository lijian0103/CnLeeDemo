package cn.cnlee.demo.playvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SurfaceView mSurfaceView;
    private MediaPlayer mPlayer;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mSurfaceView = findViewById(R.id.sfv);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(new SurfaceCallback());
    }

    private void startPlayer() {
        mPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.video);
            mPlayer.setDataSource(afd);
            //设置播放区域
            mPlayer.setDisplay(mHolder);
            //播放时屏幕保持唤醒
            mPlayer.setScreenOnWhilePlaying(true);
            //异步准备播放视频
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(this);
        } catch (Exception exc) {
            Log.e(TAG, "startPlayer failed! err: " + exc.getMessage());
        }
    }

    private void stopPlayer() {
        Log.e(TAG, "stopPlayer.");
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private void releasePlayer() {
        Log.d(TAG, "releasePlayer.");
        if (mPlayer != null) {
            try {
                mPlayer.stop();
            } catch (Exception exc) {
                exc.printStackTrace();
            } finally {
                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, "onError. isPlaying: " + mp.isPlaying());
                return false;
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e(TAG, "onCompletion. isPlaying: " + mp.isPlaying());
                MainActivity.this.finish();
            }
        });
        mp.start();
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            startPlayer();
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy.");
        releasePlayer();
    }
}