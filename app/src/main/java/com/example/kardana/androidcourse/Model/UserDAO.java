package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("select * from User where userid = :id")
    User getUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNewUser(User... user);
}
