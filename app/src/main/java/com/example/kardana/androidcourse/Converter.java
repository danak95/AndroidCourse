package com.example.kardana.androidcourse;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Dana on 31-Jul-18.
 */

public class Converter {
    // Room converters
    @TypeConverter
    public String roomTypeToString(ArrayList<RoomType> types) {
        String value = "";

        for (RoomType type : types)
            value += type.getName() + ";";

        return value;
    }

    @TypeConverter
    public ArrayList<RoomType> stringToRoomType(String value) {
        ArrayList<RoomType> types = new ArrayList<RoomType>();

        for (String typeName : Arrays.asList(value.split(";"))) {
            types.add(RoomType.valueOf(typeName));
        }

        return types;
    }
}
