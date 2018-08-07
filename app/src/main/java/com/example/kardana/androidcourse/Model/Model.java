package com.example.kardana.androidcourse.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;

public class Model {

    private ModelFirebaseUser modelFirebaseUser;
    private ModelFirebaseRoom modelFirebaseRoom;
    private ModelFirebaseStorage modelFirebase;
    private RoomsLiveData roomsLiveData = new RoomsLiveData();
    private UsersLiveData usersLiveData = new UsersLiveData();
    private ReviewsLiveData reviewsLiveData = new ReviewsLiveData();
    private ModelFirebaseReview modelFirebaseReviews;

    public static User user = null;
    public static Model instance = new Model();

    private Model(){
        modelFirebaseUser = new ModelFirebaseUser();
        modelFirebaseRoom = new ModelFirebaseRoom();
        modelFirebase = new ModelFirebaseStorage();
        modelFirebaseReviews = new ModelFirebaseReview();

    }

    public static Model getInstance() {
        return instance;
    }

    // ******* Handle users *******
    public interface IUpdateUserCallback{
        void onComplete(boolean success);
    }
    public void updateUser(final User user, final IUpdateUserCallback callback){
        modelFirebaseUser.updateUser(user, new ModelFirebaseUser.IUpdateUserCallback() {
            @Override
            public void onComplete(boolean success) {
                callback.onComplete(success);
            }
        });
    }

    public interface IGetUserLoginCallback {
        void onComplete(User user);
    }
    public void userLogin(String email, String password , final IGetUserLoginCallback callback) {
        modelFirebaseUser.userLogin(email, password, new ModelFirebaseUser.IGetUserLoginCallback() {
            @Override
            public void onComplete(User user) {
                Log.d("dev","onComplete Model userLogin");
                callback.onComplete(user);
            }
        });
    }

    // Get user by ID
    public interface IGetUserByIdCallback {
        void onComplete(User user);
        void onCancel();
    }
    public void getUserById(String id, final IGetUserByIdCallback callback) {
        modelFirebaseUser.getUserById(id, new ModelFirebaseUser.IGetUserByIdCallback() {
            @Override
            public void onComplete(User user) {
                Log.d("dev","onComplete Model getUserById");
                callback.onComplete(user);
            }

            @Override
            public void onCancel() {
                Log.d("dev","onCancel Model getUserById");
                callback.onCancel();
            }
        });
    }

    // Add new member
    public interface IAddNewUser {
        void onComplete(User user);
    }
    public void AddNewMember(User newUser , final IAddNewUser callback) {
        modelFirebaseUser.AddNewMember(newUser, new ModelFirebaseUser.IAddNewUser() {
            @Override
            public void onComplete(User user) {
                Log.d("dev","onComplete Model userLogin");
                callback.onComplete(user);
            }
        });
    }

    // Get current user
    public interface IGetCurrentUserCallback {
        void onComplete(User user);
    }

    public void getCurrentUser(final IGetCurrentUserCallback callback) {
        modelFirebaseUser.getCurrentUser(new ModelFirebaseUser.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }
        });
    }

    // User sign out
    public void signOut()
    {
        modelFirebaseUser.signOut();
        Log.d("dev","User signed out");
    }

    // ******* Handle images *******
    public interface SaveImageListener{
        void onDone(String url);
    }


    public void saveImage(String path, String name, Bitmap imageBitmap, SaveImageListener listener) {
        modelFirebase.saveImage(path, name, imageBitmap,listener);
    }

    public interface GetImageListener{
        void onDone(Bitmap imageBitmap);
    }
    public void getImage(final String url, final GetImageListener listener ){
        if(!url.isEmpty()) {
            String localFileName = URLUtil.guessFileName(url, null, null);
            final Bitmap image = loadImageFromFile(localFileName);
            if (image == null) {                                      //if image not found - try downloading it from parse
                modelFirebase.getImage(url, new GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        if (imageBitmap == null) {
                            listener.onDone(null);
                        } else {
                            //2.  save the image localy
                            String localFileName = URLUtil.guessFileName(url, null, null);
                            Log.d("TAG", "save image to cache: " + localFileName);
                            saveImageToFile(imageBitmap, localFileName);
                            //3. return the image using the listener
                            listener.onDone(imageBitmap);
                        }
                    }
                });
            } else {
                Log.d("TAG", "OK reading cache image: " + localFileName);
                listener.onDone(image);
            }
        }
    }

    // Store / Get from local memory
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        if (imageBitmap == null) return;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public RoomsLiveData getAllRooms(){
        return roomsLiveData;
    }

    public void cancelGetAllRooms() {
        modelFirebaseRoom.cancelGetAllRooms();
    }

    public void addRoom(Room room)
    {
        modelFirebaseRoom.addRoom(room);
    }

    public void updateRoom(Room room)
    {
        modelFirebaseRoom.updateRoom(room, new ModelFirebaseRoom.IUpdateRoomCallback() {
            @Override
            public void onComplete(boolean success) {

            }
        });
    }

    public class RoomsLiveData extends MutableLiveData<List<Room>> {

        private RoomsLiveData(){
            this.onActive();
        }
        @Override
        protected void onActive() {
            super.onActive();

            RoomAsyncDao.getAllRooms(new RoomAsyncDao.IGetAllRooms() {

                @Override
                public void onComplete(List<Room> data) {
                    setValue(data);

                    modelFirebaseRoom.getAllRooms(new ModelFirebaseRoom.IGetAllRooms() {
                        @Override
                        public void onSuccess(List<Room> roomlist) {
                            setValue(roomlist);

                            RoomAsyncDao.insertAllRooms(roomlist, new RoomAsyncDao.IInsertAllRooms() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public UsersLiveData getAllUsers()
    {
        return usersLiveData;
    }

    public String getCurrentUserId()
    {
        return modelFirebaseUser.getCurrentUserId();
    }

    public class UsersLiveData extends MutableLiveData<List<User>> {
        private UsersLiveData(){
            this.onActive();
        }
        @Override
        protected void onActive() {
            super.onActive();

            UserAsyncDao.getAllUsers(new UserAsyncDao.IGetAllUsers() {

                @Override
                public void onComplete(List<User> data) {
                    setValue(data);

                    modelFirebaseUser.getAllUsers(new ModelFirebaseUser.IGetAllUsers() {
                        @Override
                        public void onSuccess(List<User> userList) {
                            setValue(userList);

                            UserAsyncDao.insertAllUsers(userList, new UserAsyncDao.IInsertAllUsers() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public class ReviewsLiveData extends MutableLiveData<List<Review>> {

        private ReviewsLiveData(){
            this.onActive();
        }
        @Override
        protected void onActive() {
            super.onActive();

            ReviewAsyncDao.getAllReviews(new ReviewAsyncDao.IGetAllReviews() {

                @Override
                public void onComplete(List<Review> data) {
                    setValue(data);

                    modelFirebaseReviews.getAllReviews(new ModelFirebaseReview.IGetAllReviews() {
                        @Override
                        public void onSuccess(List<Review> reviewList) {
                            setValue(reviewList);

                            ReviewAsyncDao.insertAllReviews(reviewList, new ReviewAsyncDao.IInsertAllReviews() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }
    }

    // ******* Handle Reviews *******

    // Add new review
    public void AddReview(Review review)
    {
        modelFirebaseReviews.AddNewReview(review);
        Log.d("dev","Model- Add new review");
    }

    // Update exist review
    interface IUpdateReviewById {
        void onComplete(boolean success);
    }

    public void updateReviewById(Review review , final IUpdateReviewById callback)
    {
        modelFirebaseReviews.updateReviewById(review, new ModelFirebaseReview.IUpdateReviewById() {
            @Override
            public void onComplete(boolean success) {
                Log.d("dev","Model- update review by ID success is " + success);
                callback.onComplete(success);
            }
        });
    }

    // Delete Review
    interface IDeleteReviewCallback{
        void onComplete(boolean success);
    }

    public void deleteReview(Review review, final IDeleteReviewCallback callback)
    {
        modelFirebaseReviews.deleteReview(review, new ModelFirebaseReview.IDeleteReviewCallback() {
            @Override
            public void onComplete(boolean success) {
                Log.d("dev","Model- delete review success is " + success);
                callback.onComplete(success);
            }
        });
    }

    public ReviewsLiveData getAllReviews()
    {
        return reviewsLiveData;
    }

    // Get all reviews by roomId
    interface IGetReviewsForRoom{
        void onComplete(List<Review> reviews);
        void onCancel();
    }

    public void getReviewsForRoom(final String roomId, final IGetReviewsForRoom callback)
    {
        modelFirebaseReviews.getReviewsForRoom(roomId, new ModelFirebaseReview.IGetReviewsForRoom() {
            @Override
            public void onComplete(List<Review> reviews) {
                Log.d("dev","Model- the reviews for room id " + roomId + " are " + reviews);
                callback.onComplete(reviews);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });
    }

}
