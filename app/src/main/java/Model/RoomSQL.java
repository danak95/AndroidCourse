package Model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dana on 26-May-18.
 */

public class RoomSQL {

    final static String TABLE_NAME = "rooms";
    final static String ROOM_ID = "id";
    final static String ROOM_NAME = "name";
    final static String ROOM_ADDRESS = "address";
    final static String ROOM_DESCRIPTION = "description";
    final static String ROOM_IMAGE_PATH = "image_path";
    final static String ROOM_RANK = "rank";
    final static String ROOM_COMPANY_ID = "company_id";
    final static String ROOM_OWNER_ID = "owner_id";
    final static String ROOM_MIN_NUM_OF_PEOPLE = "min_num_of_people";
    final static String ROOM_MAX_NUM_OF_PEOPLE = "max_num_of_people";
    final static String ROOM_COMMENTS = "comments";
    final static String ROOM_SITE = "room_site";

    // CTOR and DTOR for DB
    static public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " ( " + ROOM_ID + " INTEGER PRIMARY KEY, " +
                ROOM_NAME + " TEXT, " +
                ROOM_ADDRESS + " TEXT, " +
                ROOM_DESCRIPTION + " TEXT, " +
                ROOM_IMAGE_PATH + " TEXT, " +
                ROOM_RANK + " FLOAT, " +
                ROOM_COMPANY_ID + " INTEGER, " +
                ROOM_OWNER_ID + " INTEGER, " +
                ROOM_MIN_NUM_OF_PEOPLE + " INTEGER, " +
                ROOM_MAX_NUM_OF_PEOPLE + " INTEGER, " +
                ROOM_COMMENTS + " TEXT, " +
                ROOM_SITE + " TEXT)");

    }

    static public void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table " + TABLE_NAME + ";");
    }
}
