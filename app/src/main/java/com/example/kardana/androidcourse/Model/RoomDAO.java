package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Dana on 31-Jul-18.
 */

@Dao
public interface RoomDAO {
    @Query("select * from Room")
    List<Room> getAll();

    @Query("select * from Room where id = :roomId")
    Room getRoomById(String roomId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Room... rooms);

    @Delete
    void delete(Room room);
}
