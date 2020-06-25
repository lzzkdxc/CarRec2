package com.example.carrec2.ui.main_fake.pure_rec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PureRecViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PureRecViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}