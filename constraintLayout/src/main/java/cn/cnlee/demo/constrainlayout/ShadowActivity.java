package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class ShadowActivity extends AppCompatActivity {

    private static final String TAG = ShadowActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}