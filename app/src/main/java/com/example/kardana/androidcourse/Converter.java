package com.example.kardana.androidcourse;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dana on 31-Jul-18.
 */

public class Converter {
    // Room converters
    @TypeConverter
    public String roomTypeToString(List<RoomType> types) {
        String value = "";
        if (types != null) {
            for (RoomType type : types)
                value += type.getName() + ";";
        }

        return value;
    }

    @TypeConverter
    public List<RoomType> stringToRoomType(String value) {
        List<RoomType> types = new ArrayList<RoomType>();

        if (!value.isEmpty()) {
            for (String typeName : Arrays.asList(value.split(";"))) {
                types.add(RoomType.valueOf(typeName));
            }
        }

        return types;
    }
}
