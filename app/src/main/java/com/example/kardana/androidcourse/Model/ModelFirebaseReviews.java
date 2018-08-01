package com.example.kardana.androidcourse.Model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 28-Jul-18.
 */

public class ModelFirebaseReviews {

    private static final String REVIEWS_KEY = "Reviews";
    private FirebaseDatabase database;
    private DatabaseReference reviewsReference;

    public ModelFirebaseReviews() {
        database = FirebaseDatabase.getInstance();
        reviewsReference = database.getReference(REVIEWS_KEY);
    }

    // Add new Review
    public void AddNewReview(final Review review) {
        Log.d("TAG", "AddNewReview");
        review.setReviewId(reviewsReference.push().getKey());
        reviewsReference.child(review.getReviewId()).setValue(review);
    }

    // Update review
    interface IUpdateReviewById {
        void onComplete(boolean success);
    }
    public void updateReviewById(Review review, final ModelFirebaseReviews.IUpdateReviewById callback) {
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
        void onComplete(ArrayList<Review> reviews);
        void onCancel();
    }

    public void getReviewsForRoom(final String roomId, final IGetReviewsForRoom callback) {
        // Run over all the reviews on DB
        reviewsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Create list of reviews that relevant only form roomId
                ArrayList<Review> reviewsForRoom = new ArrayList<>();
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Review reviewRoom = snap.getValue(Review.class);
                    // Check if the current roomId is equal to the parameter
                    if (reviewRoom.getRommId() == roomId)
                    {
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
}
