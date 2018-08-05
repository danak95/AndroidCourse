package com.example.kardana.androidcourse.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by Dana on 04-Aug-18.
 */

public class RoomsViewModel extends ViewModel {
    LiveData<List<Room>> data;

    public LiveData<List<Room>> getData(){
        data = Model.instance.getAllRooms();
        return data;
    }



}
