package cn.cnlee.demo.constrainlayout;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

public class UiViewModel extends BaseObservable {

    private MutableLiveData<Integer> mLocation = new MutableLiveData<>(1);
    private String message;

    public MutableLiveData<Integer> getLocation() {
        return mLocation;
    }

    public void setLocation(int mLocation) {
        this.mLocation.postValue(mLocation);
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyChange();
    }
}
