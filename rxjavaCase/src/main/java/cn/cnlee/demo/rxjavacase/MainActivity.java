package cn.cnlee.demo.rxjavacase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.cnlee.demo.rxjavacase.display.ActionOption;
import cn.cnlee.demo.rxjavacase.manager.ExecuteManager;

public class MainActivity extends AppCompatActivity {

    ExecuteManager mExecuteManager = ExecuteManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionOption option = ActionOption.builder().id("666").build();
        ActionOption option2 = ActionOption.builder().id("777").build();
        mExecuteManager.executeCb(option);
        mExecuteManager.executeRx(option2);
    }
}