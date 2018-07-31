package com.example.kardana.androidcourse.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Model {

    private ModelFirebaseUser modelFirebaseUser;
    private ModelFirebaseStorage modelFirebase;

    public static User user = null;
    public static Model instance = new Model();

    private Model(){
        modelFirebaseUser = new ModelFirebaseUser();
        modelFirebase = new ModelFirebaseStorage();
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

    // ******* Handle images *******
    public interface SaveImageListener{
        void onDone(String url);
    }

    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap,listener);
    }

    public interface GetImageListener{
        void onDone(Bitmap imageBitmap);
    }
    public void getImage(final String url, final GetImageListener listener ){
        String localFileName = URLUtil.guessFileName(url, null, null);
        final Bitmap image = loadImageFromFile(localFileName);
        if (image == null) {                                      //if image not found - try downloading it from parse
            modelFirebase.getImage(url, new GetImageListener() {
                @Override
                public void onDone(Bitmap imageBitmap) {
                    if (imageBitmap == null) {
                        listener.onDone(null);
                    }else {
                        //2.  save the image localy
                        String localFileName = URLUtil.guessFileName(url, null, null);
                        Log.d("TAG", "save image to cache: " + localFileName);
                        saveImageToFile(imageBitmap, localFileName);
                        //3. return the image using the listener
                        listener.onDone(imageBitmap);
                    }
                }
            });
        }else {
            Log.d("TAG","OK reading cache image: " + localFileName);
            listener.onDone(image);
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

}
