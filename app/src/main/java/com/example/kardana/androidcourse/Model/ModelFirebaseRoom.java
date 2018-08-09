package com.example.kardana.androidcourse.Model;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseRoom {
    private static final String ROOMS_KEY = "Rooms";

    ValueEventListener eventListener;
    DatabaseReference roomsReference;
    private StorageReference storageReference;

    public ModelFirebaseRoom() {
        roomsReference = FirebaseDatabase.getInstance().getReference().child(ROOMS_KEY);
        storageReference = FirebaseStorage.getInstance().getReference();
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

    public interface IAddRoom
    {
        public void onSuccess();
        public void onFail(String error);
    }

    public void addRoom(boolean uploadImage, final Room room ,byte[] imageByteData,final IAddRoom callback) {
        final String roomGeneratedKey;

        if(room.getId().equals("1")) {
            roomGeneratedKey = roomsReference.push().getKey();
            room.setId(roomGeneratedKey);
            roomsReference.child(roomGeneratedKey).setValue(room);
        }
        else
        {
            roomGeneratedKey = room.getId();
        }

        if (uploadImage) {
            storageReference.child(roomGeneratedKey).putBytes(imageByteData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                        roomsReference.child(roomGeneratedKey).setValue(room);
                    } else
                        callback.onFail(task.getException().getMessage().toString());
                }
            });
        }
        else {
            roomsReference.child(roomGeneratedKey).setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    callback.onSuccess();
                }
            });

        }

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
