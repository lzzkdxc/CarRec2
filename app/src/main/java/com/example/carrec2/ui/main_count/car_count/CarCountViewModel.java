package com.example.carrec2.ui.main_count.car_count;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarCountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CarCountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}