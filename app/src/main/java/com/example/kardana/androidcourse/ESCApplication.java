package com.example.kardana.androidcourse;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.User;

/**
 * Created by Dana on 01-Aug-18.
 */

public class ESCApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
