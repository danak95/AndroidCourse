package com.example.kardana.androidcourse.Model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseRoom {
    private static final String ROOMS_KEY = "Rooms";
    private static final String ROOMS_ID = "RoomsId";
    private static final String OWNER = "Owner";
    private FirebaseDatabase database;
    private DatabaseReference roomsReference;

    public ModelFirebaseRoom() {
        database = FirebaseDatabase.getInstance();
        roomsReference = database.getReference(ROOMS_KEY);
    }

    public void addRoom(final Room room) {
        getRoomId(new IGetRoomId() {
            @Override
            public void getId(int id) {
                room.setId(id + "");
                id++;

                setRoomId(id, new ISetRoomIdCallback() {
                    @Override
                    public void onComplete(boolean success) {

                    }
                });
                roomsReference.child(room.getId()).setValue(room);
            }
        });
    }

    interface ISetRoomIdCallback {
        void onComplete(boolean success);
    }

    public void setRoomId(int id, final ISetRoomIdCallback callback) {
        DatabaseReference reference = database.getReference(ROOMS_ID);
        reference.push().setValue(id, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }});
    }

    interface IGetRoomId {
        void getId(int id);
    }
    public void getRoomId(final IGetRoomId callback) {
        DatabaseReference reference = database.getReference(ROOMS_ID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id;

                try {
                    id = dataSnapshot.getValue(int.class);
                } catch (NullPointerException e) {
                    id = 0;
                }

                callback.getId(id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface IGetAllRoomsCallback {
        void onComplete(ArrayList<Room> rooms);
        void onCancel();
    }
    public void getAllRooms(final IGetAllRoomsCallback callback){
        roomsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Room> rooms = new ArrayList<Room>();

                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Room room = snap.getValue(Room.class);
                    rooms.add(room);
                }

                callback.onComplete(rooms);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    interface IGetRoomCallback{
        void onComplete(Room room);
        void onCancel();
    }
    public void getRoomByID (String roomID, final IGetRoomCallback callback){
        roomsReference.child(roomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                callback.onComplete(room);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    interface IUpdateRoomCallback {
        void onComplete(boolean success);
    }
    public void updateRoom(Room room, final IUpdateRoomCallback callback){
        roomsReference.child(room.getId()).setValue(room, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }

    interface IRemoveRoomCallback{
        void  onComplete(boolean isSuccess);
    }
    public void removeRoom(Room room, final IRemoveRoomCallback callback)
    {
        roomsReference.child(room.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });

    }

    interface IGetOwnerOfRoomCallback{
        void onComplete(User user);
        void onCancel();
    }

    public void getOwnerOfRoom(String roomID,final IGetOwnerOfRoomCallback callback) {
        roomsReference.child(roomID).child(OWNER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

}
