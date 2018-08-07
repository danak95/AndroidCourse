package com.example.kardana.androidcourse.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Dana on 07-Aug-18.
 */

public class ReviewAsyncDao {
    public interface IGetAllReviewsByRoom
    {
        void onComplete(List<Review> review);
    }

    static public void getAllReviewsByRoom(final String roomId, final ReviewAsyncDao.IGetAllReviewsByRoom callback) {

        class GetAllReviewsAsyncTask extends AsyncTask<String, String, List<Review>> {

            @Override
            protected List<Review> doInBackground(String... strings) {
                List<Review> reviews = LocalDB.db.reviewDao().getReviewsByRoomId(roomId);
                return reviews;
            }

            @Override
            protected void onPostExecute(List<Review> reviews) {
                super.onPostExecute(reviews);
                callback.onComplete(reviews);
            }
        }

        GetAllReviewsAsyncTask task = new GetAllReviewsAsyncTask();
        task.execute();
    }

    interface IInsertAllReviews
    {
        void onComplete(Boolean isSuccessfull);
    }

    static void insertAllReviews(final List<Review> reviews, final ReviewAsyncDao.IInsertAllReviews callback) {

        class InsertAllReviewsAsyncTask extends AsyncTask<List<Review>, String, Boolean> {
            @Override
            protected Boolean doInBackground(List<Review>... reviews) {
                for (Review review : reviews[0]) {
                    LocalDB.db.reviewDao().insertAllReviews(review);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                callback.onComplete(success);
            }
        }

        InsertAllReviewsAsyncTask task = new InsertAllReviewsAsyncTask();
        task.execute(reviews);
    }

    interface IGetReviewById
    {
        void onComplete(Review review);
    }

    static public void getReviewById(final ReviewAsyncDao.IGetReviewById callback, final String id) {

        class GetReviewByIdAsyncTask extends AsyncTask<String, String, Review> {

            @Override
            protected Review doInBackground(String... strings) {
                Review review = LocalDB.db.reviewDao().getReviewById(id);
                return review;
            }

            @Override
            protected void onPostExecute(Review review) {
                super.onPostExecute(review);
                callback.onComplete(review);
            }
        }

        GetReviewByIdAsyncTask task = new GetReviewByIdAsyncTask();
        task.execute();
    }

    public interface IGetAllReviews
    {
        void onComplete(List<Review> review);
    }

    static public void getAllReviews(final ReviewAsyncDao.IGetAllReviews callback) {

        class GetAllReviewsAsyncTask extends AsyncTask<String, String, List<Review>> {

            @Override
            protected List<Review> doInBackground(String... strings) {
                List<Review> reviews = LocalDB.db.reviewDao().getAllReviews();
                return reviews;
            }

            @Override
            protected void onPostExecute(List<Review> reviews) {
                super.onPostExecute(reviews);
                callback.onComplete(reviews);
            }
        }

        GetAllReviewsAsyncTask task = new GetAllReviewsAsyncTask();
        task.execute();
    }
}
