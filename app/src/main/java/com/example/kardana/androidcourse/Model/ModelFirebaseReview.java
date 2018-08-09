package com.example.kardana.androidcourse.Model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseReview {

    private static final String REVIEWS_KEY = "Reviews";
    private FirebaseDatabase database;
    private DatabaseReference reviewsReference;
    private StorageReference storageReference;

    public ModelFirebaseReview() {
        database = FirebaseDatabase.getInstance();
        reviewsReference = database.getReference(REVIEWS_KEY);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public interface IAddReview
    {
        public void onSuccess();
        public void onFail(String error);
    }
    // Add new Review
    public void AddNewReview(boolean uploadImage, final Review review, byte[] imageByteData, final IAddReview callback) {
        Log.d("TAG", "AddNewReview");
        final String reviewGeneratedKey;

        reviewGeneratedKey = reviewsReference.push().getKey();
        review.setReviewId(reviewGeneratedKey);
        reviewsReference.child(reviewGeneratedKey).setValue(review);

        if (uploadImage) {
            storageReference.child(reviewGeneratedKey).putBytes(imageByteData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                        reviewsReference.child(reviewGeneratedKey).setValue(review);
                    } else
                        callback.onFail(task.getException().getMessage().toString());
                }
            });
        }
        else {
            reviewsReference.child(reviewGeneratedKey).setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    callback.onSuccess();
                }
            });

        }
    }

    // Update review
    interface IUpdateReviewById {
        void onComplete(boolean success);
    }
    public void updateReviewById(Review review, final ModelFirebaseReview.IUpdateReviewById callback) {
        reviewsReference.child(review.getReviewId()).setValue(review, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }

    // Delete review
    interface IDeleteReviewCallback{
        void onComplete(boolean success);
    }
    public void deleteReview(Review review, final IDeleteReviewCallback callback)
    {
        reviewsReference.child(review.getReviewId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }

    // Get all reviews by roomId
    interface IGetReviewsForRoom{
        void onComplete(List<Review> reviews);
        void onCancel();
    }

    public void getReviewsForRoom(final String roomId, final IGetReviewsForRoom callback) {
        // Run over all the reviews on DB
        reviewsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Create list of reviews that relevant only form roomId
                List<Review> reviewsForRoom = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Review reviewRoom = snap.getValue(Review.class);
                    // Check if the current roomId is equal to the parameter
                    if (reviewRoom.getRoomId() == roomId) {
                        reviewsForRoom.add(reviewRoom);
                    }
                }

                callback.onComplete(reviewsForRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }


    interface IGetAllReviews{
        void onSuccess(List<Review> reviews);
    }

    public void getAllReviews(final ModelFirebaseReview.IGetAllReviews callback) {
        // Run over all the reviews on DB
        reviewsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Create list of reviews that relevant only form roomId
                List<Review> reviews = new ArrayList<>();
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    reviews.add(snap.getValue(Review.class));
                }

                callback.onSuccess(reviews);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
