package cn.cnlee.demo.databindingrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

import cn.cnlee.demo.databindingrecyclerview.ui.MultiStateButton3;
import cn.cnlee.demo.databindingrecyclerview.ui.State;

public class TestMutiStateButtonActivity extends AppCompatActivity {

    private MultiStateButton3 multiStateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multiStateButton = findViewById(R.id.multi_btn);
        updateData();
    }

    Handler handler = new Handler(Looper.getMainLooper());
    AtomicInteger count = new AtomicInteger();
    private Runnable runnable = () -> {
        if (count.get() < 100) {
            count.addAndGet(5);
            multiStateButton.setProgressText(State.PAUSE_DOWNLOAD.getName(), count.get());
            handler.postDelayed(this.runnable, 500l);
        }
    };


    private void updateData() {
        multiStateButton.setState(State.PAUSE_DOWNLOAD);
        handler.postDelayed(runnable, 500l);
    }
}