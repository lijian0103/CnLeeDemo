package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();
    private ViewDataBinding mBinding;
    private UiViewModel mViewModel;

    @BindView(R.id.flow_layout)
    Flow flowLayout;

    @BindView(R.id.layer_layout)
    Layer layerLayout;

    @BindView(R.id.tv1)
    TextView tv1;

    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        ButterKnife.bind(this);
        mViewModel = new UiViewModel();
        mBinding.setVariable(BR.viewmodel, mViewModel);
    }

    /**
     * onBtnClick.
     *
     * @param view view
     */
    @OnClick({R.id.btn_do_anim, R.id.btn_change_txt})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_do_anim:
                Log.d(TAG, "btn_do_anim onClick");
//                layerLayout.setVisibility(View.INVISIBLE);
                layerLayout.setAlpha(0.3f);
//                flowLayout.setVisibility(View.INVISIBLE);
//                flowLayout.setAlpha(0.1f);
                break;
            case R.id.btn_change_txt:
                Log.d(TAG, "btn_change_txt onClick");
//                count++;
//                String txt = String.join("", Collections.nCopies(count, "文字"));
//                mViewModel.setMessage(txt);
//                Log.d(TAG, txt);
//                if (count > int20) {
//                    count = 0;
//                }
//                layerLayout.setVisibility(View.VISIBLE);
                layerLayout.setAlpha(1f);
//                flowLayout.setVisibility(View.VISIBLE);
//                flowLayout.setAlpha(1f);
                break;
            default:
                break;
        }
    }
}