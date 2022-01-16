package cn.cnlee.demo.threadcase;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.cnlee.demo.threadcase.bean.Action;
import cn.cnlee.demo.threadcase.mock.Haizi;
import cn.cnlee.demo.threadcase.task.ActionCallback;
import cn.cnlee.demo.threadcase.task.ActionTask;
import cn.cnlee.demo.threadcase.uitls.LogUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Haizi.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);

//        Action action = Action.builder().id("001").name("weixiao").loop(true).build();
//        LogUtils.d(TAG, "doAction start: " + action);
//        doAction(action);
//        LogUtils.d(TAG, "doAction end: " + action);
    }

    private void doAction(Action action) {
        ActionTask task = new ActionTask(action, new ActionCallback() {
            @Override
            public void onCallback(String actionId, int status, String errMsg) {
                LogUtils.d(TAG, "doAction onCallback: " + actionId + " ,loop: " + action.isLoop());
                if (action.isLoop()) {
                    LogUtils.d(TAG, "doLoop");
                    doAction(action);
                }
            }
        });
        task.doTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1: {
                Action action = Action.builder().id("001").name("huishou").loop(false).build();
                LogUtils.d(TAG, "doAction start: " + action);
                doAction(action);
                LogUtils.d(TAG, "doAction end: " + action);
                break;
            }
            case R.id.btn_2: {
                Action action = Action.builder().id("001").name("tiaoyue").loop(true).build();
                LogUtils.d(TAG, "doAction start: " + action);
                doAction(action);
                LogUtils.d(TAG, "doAction end: " + action);
                break;
            }
        }
    }
}