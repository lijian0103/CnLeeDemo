package cn.cnlee.demo.windowanimation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleRegistry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LifecycleRegistry(this);
        startService(new Intent(this, FloatingWindowService.class));
    }
}