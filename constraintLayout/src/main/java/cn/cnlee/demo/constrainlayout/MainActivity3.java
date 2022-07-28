package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = MainActivity3.class.getSimpleName();
    private ViewDataBinding mBinding;
    private UiViewModel mViewModel;
    @BindView(R.id.vr_text)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main3);
        ButterKnife.bind(this);
        mViewModel = new UiViewModel();
        mBinding.setVariable(BR.viewmodel, mViewModel);
    }

    int count = 0;
    String txt = "";

    /**
     * onBtnClick.
     *
     * @param view view
     */
    @OnClick({R.id.btn_change_txt})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_txt:
                Log.d(TAG, "btn_change_txt onClick");
                count++;
                txt += "文字" + count;
                mViewModel.setMessage(txt);
                VrTxtUtils.getTxtWidth(tv, txt);
                VrTxtUtils.getSumOfTxt(tv, txt);
                VrTxtUtils.getTxtWidth2(txt, 28);
                VrTxtUtils.getSumOfTxt2(txt, 28);
                Log.d(TAG, txt);
            default:
                break;
        }
    }
}