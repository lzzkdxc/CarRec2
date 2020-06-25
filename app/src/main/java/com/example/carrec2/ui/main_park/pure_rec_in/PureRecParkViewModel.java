package com.example.carrec2.ui.main_park.pure_rec_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PureRecParkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PureRecParkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}