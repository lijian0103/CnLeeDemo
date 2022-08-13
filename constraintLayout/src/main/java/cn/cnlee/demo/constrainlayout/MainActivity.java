package cn.cnlee.demo.constrainlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.TEXT_ALIGNMENT_TEXT_END;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewDataBinding mBinding;
    private UiViewModel mViewModel;

    @BindView(R.id.vr_layout)
    ConstraintLayout vrLayout;

    @BindView(R.id.tv)
    TextView tv;
    private int count = 0;
    String txt = "";
    private final int int20 = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        mViewModel = new UiViewModel();
        mBinding.setVariable(cn.cnlee.demo.constrainlayout.BR.viewmodel, mViewModel);
        mViewModel.getLocation().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer location) {
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(vrLayout);
                constraintSet.clear(R.id.iv, ConstraintSet.BOTTOM);
                constraintSet.clear(R.id.iv, ConstraintSet.START);
                constraintSet.clear(R.id.iv, ConstraintSet.END);
                constraintSet.clear(R.id.iv, ConstraintSet.TOP);
                Log.d(TAG, "location: " + location);
                switch (location) {
                    case 1: {
                        constraintSet.connect(R.id.iv, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                        constraintSet.connect(R.id.iv, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);

                        constraintSet.clear(R.id.tv, ConstraintSet.END);
                        constraintSet.connect(R.id.tv, ConstraintSet.TOP, R.id.iv, ConstraintSet.TOP);
                        constraintSet.connect(R.id.tv, ConstraintSet.START, R.id.iv, ConstraintSet.END);

                        constraintSet.clear(R.id.icon, ConstraintSet.END);
                        constraintSet.connect(R.id.icon, ConstraintSet.START, R.id.tv, ConstraintSet.END);
                        constraintSet.connect(R.id.icon, ConstraintSet.TOP, R.id.iv, ConstraintSet.TOP);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        break;
                    }
                    case 2: {
                        constraintSet.connect(R.id.iv, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                        constraintSet.connect(R.id.iv, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);

                        constraintSet.clear(R.id.tv, ConstraintSet.END);
                        constraintSet.connect(R.id.tv, ConstraintSet.TOP, R.id.iv, ConstraintSet.TOP);
                        constraintSet.connect(R.id.tv, ConstraintSet.START, R.id.iv, ConstraintSet.END);

                        constraintSet.clear(R.id.icon, ConstraintSet.END);
                        constraintSet.connect(R.id.icon, ConstraintSet.START, R.id.tv, ConstraintSet.END);
                        constraintSet.connect(R.id.icon, ConstraintSet.TOP, R.id.iv, ConstraintSet.TOP);
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        break;
                    }
                    case 3: {
                        constraintSet.connect(R.id.iv, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                        constraintSet.connect(R.id.iv, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

                        constraintSet.clear(R.id.tv, ConstraintSet.START);
                        constraintSet.connect(R.id.tv, ConstraintSet.END, R.id.iv, ConstraintSet.START);

                        constraintSet.clear(R.id.icon, ConstraintSet.START);
                        constraintSet.connect(R.id.icon, ConstraintSet.END, R.id.tv, ConstraintSet.START);
                        tv.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
                        break;
                    }
                    case 4: {
                        constraintSet.connect(R.id.iv, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                        constraintSet.connect(R.id.iv, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

                        constraintSet.clear(R.id.tv, ConstraintSet.START);
                        constraintSet.connect(R.id.tv, ConstraintSet.END, R.id.iv, ConstraintSet.START);

                        constraintSet.clear(R.id.icon, ConstraintSet.START);
                        constraintSet.connect(R.id.icon, ConstraintSet.END, R.id.tv, ConstraintSet.START);
                        tv.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
                        break;
                    }
                    default:
                        break;
                }
                constraintSet.applyTo(vrLayout);
            }
        });
    }

    /**
     * onBtnClick.
     *
     * @param view view
     */
    @OnClick({R.id.btn_change_pos, R.id.btn_change_txt, R.id.btn_change_icon})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_txt:
                Log.d(TAG, "btn_change_txt onClick");
                count++;
//                String txt = String.join("", Collections.nCopies(count, "文字" + count));
                txt = txt + "文字" + count;
                mViewModel.getMessage().postValue(txt);
                Log.d(TAG, txt);
//                if (count > int20) {
//                    count = 0;
//                }
                break;
            case R.id.btn_change_pos:
                Log.d(TAG, "btn_change_pos onClick");
                mViewModel.setLocation(new Random().nextInt(4) + 1);
                break;
            case R.id.btn_change_icon:
                Log.d(TAG, "btn_change_icon onClick");
                mViewModel.getVoiceType().setValue(new Random().nextInt(3));
                mBinding.setVariable(cn.cnlee.demo.constrainlayout.BR.viewmodel, mViewModel);
                mBinding.executePendingBindings();
                break;
            default:
                break;
        }
    }
}