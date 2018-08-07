package com.example.kardana.androidcourse.Model;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Dana on 02-Aug-18.
 */

public class UserAsyncDao {

    public interface IGetAllUsers
    {
        void onComplete(List<User> user);
    }

    static public void getAllUsers(final UserAsyncDao.IGetAllUsers callback) {

        class GetAllUsersAsyncTask extends AsyncTask<String, String, List<User>> {

            @Override
            protected List<User> doInBackground(String... strings) {
                List<User> users = LocalDB.db.userDao().getAllUsers();
                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                callback.onComplete(users);
            }
        }

        GetAllUsersAsyncTask task = new GetAllUsersAsyncTask();
        task.execute();
    }

    public interface IInsertAllUsers
    {
        void onComplete(Boolean isSuccessfull);
    }

    static void insertAllUsers(final List<User> users, final IInsertAllUsers callback) {

        class InsertAllUsersAsyncTask extends AsyncTask<List<User>, String, Boolean> {
            @Override
            protected Boolean doInBackground(List<User>... users) {
                for (User user : users[0]) {
                    LocalDB.db.userDao().insertAllUsers(user);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                callback.onComplete(success);
            }
        }

        InsertAllUsersAsyncTask task = new InsertAllUsersAsyncTask();
        task.execute(users);
    }

    interface IGetUserById
    {
        void onComplete(User user);
    }

    static public void getUserById(final String id, final UserAsyncDao.IGetUserById callback) {

        class getUserByIdAsyncTask extends AsyncTask<String, String, User> {

            @Override
            protected User doInBackground(String... strings) {
                User user = LocalDB.db.userDao().getUserById(id);
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                callback.onComplete(user);
            }
        }

        getUserByIdAsyncTask task = new getUserByIdAsyncTask();
        task.execute();
    }
}
