package com.example.carrec2.ui.main_ticket.pure_rec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PureRecViewTicketModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PureRecViewTicketModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}