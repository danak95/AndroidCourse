package com.example.kardana.androidcourse.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;


public class UserViewModel extends ViewModel{

    LiveData<List<User>> data;

    public LiveData<List<User>> getData() {
        data = Model.instance.getAllUsers();
        return data;
    }

}
