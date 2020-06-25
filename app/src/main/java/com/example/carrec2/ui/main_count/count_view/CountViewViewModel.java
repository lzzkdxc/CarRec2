package com.example.carrec2.ui.main_count.count_view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CountViewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CountViewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is parking fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}