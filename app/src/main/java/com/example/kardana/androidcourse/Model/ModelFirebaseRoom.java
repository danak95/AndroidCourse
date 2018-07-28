package com.example.kardana.androidcourse.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseRoom {
    private static final String ROOMS_KEY = "Rooms";
    private FirebaseDatabase database;
    private DatabaseReference usersReference;

    public ModelFirebaseRoom() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(ROOMS_KEY);
    }

    interface IAddRoom {
        void onComplete(Room room);
        void onError(String reason);
    }
    public void addRoom(final Room room, final IAddRoom callback) {

    }
}
