package com.example.kardana.androidcourse.Model;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseRoom {
    private static final String ROOMS_KEY = "Rooms";

    ValueEventListener eventListener;
    DatabaseReference roomsReference;

    public ModelFirebaseRoom() {
        roomsReference = FirebaseDatabase.getInstance().getReference().child(ROOMS_KEY);
    }

    public interface IGetAllRooms
    {
        void onSuccess(List<Room> rooms);
    }

    public void getAllRooms(final IGetAllRooms callback) {
        eventListener = roomsReference.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                List<Room> roomList = new ArrayList<Room>();

                for (DataSnapshot roomSnapshot: dataSnapshot.getChildren()) {
                    roomList.add(roomSnapshot.getValue(Room.class));
                }

                callback.onSuccess(roomList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
    public interface IGetRoomById
    {
        void onComplete(Room room);
    }

    public void getRoomById(String id, final IGetRoomById callback) {
        eventListener = roomsReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                callback.onComplete(room);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void cancelGetAllRooms() {
        roomsReference.removeEventListener(eventListener);
    }

    public void addRoom(Room room) {
        String roomGeneratedKey = roomsReference.push().getKey();
        room.setId(roomGeneratedKey);
        roomsReference.child(roomGeneratedKey).setValue(room);
    }

    interface IUpdateRoomCallback {
        void onComplete(boolean success);
    }

    public void updateRoom(Room room, final IUpdateRoomCallback callback) {
        roomsReference.child(room.getId()).setValue(room, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }
}
