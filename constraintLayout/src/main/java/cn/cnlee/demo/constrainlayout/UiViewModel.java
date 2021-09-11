package cn.cnlee.demo.constrainlayout;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

public class UiViewModel extends BaseObservable {

    private MutableLiveData<Integer> mLocation = new MutableLiveData<>(1);

    public MutableLiveData<Integer> getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(MutableLiveData<Integer> voiceType) {
        this.voiceType = voiceType;
    }

    private MutableLiveData<Integer> voiceType = new MutableLiveData<>(VoiceType.IDLE);
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

    interface VoiceType {
        int IDLE = 0;
        int TTS = 1;
        int LISTEN = 2;
    }
}
