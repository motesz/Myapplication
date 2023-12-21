package com.montesdiether.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeView extends ViewModel{


        private final MutableLiveData<String> mText;

    public HomeView() {
            mText = new MutableLiveData<>();
            mText.setValue("This is home fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }
    }
