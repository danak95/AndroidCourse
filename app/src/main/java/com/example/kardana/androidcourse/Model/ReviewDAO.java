package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReviewDAO {
    @Query("select * from Review where roomId = :room_id")
    List<Review> getReviewsByRoomId(String room_id);

    @Query("select * from Review where reviewId = :review_id")
    Review getReviewById(String review_id);

    @Query("select * from Review")
    List<Review> getAllReviews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllReviews(Review... reviews);

    @Delete
    public void deleteReview(Review reviews);

}
