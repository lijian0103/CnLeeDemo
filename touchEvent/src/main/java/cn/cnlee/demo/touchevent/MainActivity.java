package cn.cnlee.demo.touchevent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.tv);
    }

    private ClickHandler mClickHandler = new ClickHandler(isDoubleClick -> {
        Log.e(TAG, "=====isDoubleClick=====" + isDoubleClick);
        mTv.setText(isDoubleClick ? "双击" : "单击");
    }, true);

    public void btnClick(View v) {
        Log.e(TAG, "=====btnClick=====");
        mClickHandler.onClick();
    }
}