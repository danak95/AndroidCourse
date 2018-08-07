package com.example.kardana.androidcourse.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by Dana on 07-Aug-18.
 */

public class ReviewViewModel extends ViewModel {
    LiveData<List<Review>> data;

    public LiveData<List<Review>> getData() {
        data = Model.instance.getAllReviews();
        return data;
    }
}