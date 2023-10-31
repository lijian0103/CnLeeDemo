package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class ShadowActivity extends AppCompatActivity {

    private static final String TAG = ShadowActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_shadow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}