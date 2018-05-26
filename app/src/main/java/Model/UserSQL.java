package Model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dana Koren and Karin Wasenstein on 26/05/2018.
 *
 * This class represents a connection to the users table on DB
 */

public class UserSQL {

    final static String USER_TABLE = "users";
    final static String USER_ID = "id";
    final static String USER_NAME = "name";
    final static String USER_BIRTH_DATE = "birth_date";
    final static String USER_GENDER = "gender";
    final static String USER_PHONE = "phone";
    final static String USER_MAIL = "mail";
    final static String USER_PASSWORD = "password";

    // CTOR and DTOR for DB
    static public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE +
                   " ( " + USER_ID + " INTEGER PRIMARY KEY, " +
                            USER_NAME + " TEXT, " +
                            USER_BIRTH_DATE + " TIMESTAMP, " +
                            USER_GENDER + " TEXT, " +
                            USER_PHONE + " INTEGER, " +
                            USER_MAIL + " TEXT, " +
                            USER_PASSWORD + " TEXT)");

    }

    static public void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table " + USER_TABLE + ";");
    }

}
