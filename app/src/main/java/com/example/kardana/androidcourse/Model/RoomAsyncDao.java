package com.example.kardana.androidcourse.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Dana on 31-Jul-18.
 */

public class RoomAsyncDao {
    public interface IGetAllRooms
    {
        void onComplete(List<Room> room);
    }

    static public void getAllRooms(final IGetAllRooms callback) {

        class GetAllRoomsAsyncTask extends AsyncTask<String, String, List<Room>> {

            @Override
            protected List<Room> doInBackground(String... strings) {
                List<Room> rooms = LocalDB.db.roomDao().getAllRooms();
                return rooms;
            }

            @Override
            protected void onPostExecute(List<Room> rooms) {
                super.onPostExecute(rooms);
                callback.onComplete(rooms);
            }
        }

        GetAllRoomsAsyncTask task = new GetAllRoomsAsyncTask();
        task.execute();
    }

    interface IInsertAllRooms
    {
        void onComplete(Boolean isSuccessfull);
    }

    static void insertAllRooms(final List<Room> rooms, final IInsertAllRooms callback) {

        class InsertAllRoomsAsyncTask extends AsyncTask<List<Room>, String, Boolean> {
            @Override
            protected Boolean doInBackground(List<Room>... rooms) {
                for (Room room : rooms[0]) {
                    Log.d("ROOM",room.getId());
                    LocalDB.db.roomDao().insertAllRooms(room);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                callback.onComplete(success);
            }
        }

        InsertAllRoomsAsyncTask task = new InsertAllRoomsAsyncTask();
        task.execute(rooms);
    }

    interface IGetRoomById
    {
        void onComplete(Room room);
    }

    static public void getRoomById(final IGetRoomById callback, final String id) {

        class getRoomByIdAsyncTask extends AsyncTask<String, String, Room> {

            @Override
            protected Room doInBackground(String... strings) {
                Room room = LocalDB.db.roomDao().getRoomById(id);
                return room;
            }

            @Override
            protected void onPostExecute(Room room) {
                super.onPostExecute(room);
                callback.onComplete(room);
            }
        }

        getRoomByIdAsyncTask task = new getRoomByIdAsyncTask();
        task.execute();
    }
}
