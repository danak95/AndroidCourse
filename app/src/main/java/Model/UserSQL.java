package Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kardana.androidcourse.Model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dana Koren and Karin Wasenstein on 26/05/2018.
 *
 * This class represents a connection to the users table on DB
 */

public class UserSQL {

    /*final static String TABLE_NAME = "users";
    final static String USER_ID = "id";
    final static String USER_NAME = "name";
    final static String USER_BIRTH_DATE = "birth_date";
    final static String USER_GENDER = "gender";
    final static String USER_PHONE = "phone";
    final static String USER_MAIL = "mail";
    final static String USER_PASSWORD = "password";

    // CTOR and DTOR for DB
    static public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                   " ( " + USER_ID + " INTEGER PRIMARY KEY, " +
                            USER_NAME + " TEXT, " +
                            USER_BIRTH_DATE + " TEXT, " +
                            USER_GENDER + " TEXT, " +
                            USER_PHONE + " INTEGER, " +
                            USER_MAIL + " TEXT, " +
                            USER_PASSWORD + " TEXT)");

    }

    static public void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table " + TABLE_NAME + ";");
    }

    // This function add a new user to the table
    static public void addUser(User us, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(USER_ID, us.getUser_id());
        values.put(USER_NAME, us.getName());
        values.put(USER_BIRTH_DATE, us.getBirth_Date());
        values.put(USER_GENDER, us.getGender());
        values.put(USER_PHONE, us.getPhone());
        values.put(USER_MAIL, us.getMail());
        values.put(USER_PASSWORD, us.getPassword());
        long rowId = db.insert(TABLE_NAME, null ,values);
    }

    // This function returns all the users of ESC application
   static public List<User> getAllUsers(SQLiteDatabase db){
        Cursor cr = db.query(TABLE_NAME,null,null,null,null,null,null);

        List<User> data = new LinkedList<User>();
        if (cr.moveToFirst()){
            do {
                User us = new User();
                us.setUser_id(Integer.parseInt(cr.getString(cr.getColumnIndex(USER_ID))));
                us.setName(cr.getString(cr.getColumnIndex(USER_NAME)));
                us.setBirth_Date(cr.getString(cr.getColumnIndex(USER_BIRTH_DATE)));
                us.setGender(cr.getString(cr.getColumnIndex(USER_GENDER)));
                us.setPhone(Integer.parseInt(cr.getString(cr.getColumnIndex(USER_PHONE))));
                us.setMail(cr.getString(cr.getColumnIndex(USER_MAIL)));
                us.setPassword(cr.getString(cr.getColumnIndex(USER_PASSWORD)));
                data.add(us);
            }while (cr.moveToNext());
        }
        return data;
    }*/


}
