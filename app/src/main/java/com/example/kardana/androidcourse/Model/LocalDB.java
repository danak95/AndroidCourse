package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.kardana.androidcourse.Converter;
import com.example.kardana.androidcourse.MainActivity;

/**
 * Created by Dana on 31-Jul-18.
 */
@Database(entities = {com.example.kardana.androidcourse.Model.Room.class, User.class}, version = 5)
@TypeConverters({Converter.class})
abstract class LocalDBRepository extends RoomDatabase {
    public abstract RoomDAO roomDao();

}

public class LocalDB {
    static public LocalDBRepository db = Room.databaseBuilder(MainActivity.context,
            LocalDBRepository.class,
            "ESC.db").fallbackToDestructiveMigration().build();
}
