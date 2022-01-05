package cn.cnlee.demo.databindingrecyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.cnlee.demo.databindingrecyclerview.ui.MultiStateButton;
import cn.cnlee.demo.databindingrecyclerview.ui.State;

public class TestMutiStateButton3Activity extends AppCompatActivity {

    private MultiStateButton multiStateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        multiStateButton = findViewById(R.id.multi_btn);
        updateData();
    }

    private void updateData() {
//        multiStateButton.setState(State.NOT_ACTIVE);
//        multiStateButton.setState(State.IN_USE);
//        multiStateButton.setState(State.SWITCHING);
//        multiStateButton.setState(State.DOWNLOADED);
//        multiStateButton.setState(State.NOT_DOWNLOADED);
//        multiStateButton.setState(State.WAIT_DOWNLOAD);
//        multiStateButton.setState(State.DOWNLOADING);
        multiStateButton.setState(State.PAUSE_DOWNLOAD);
    }

}
    