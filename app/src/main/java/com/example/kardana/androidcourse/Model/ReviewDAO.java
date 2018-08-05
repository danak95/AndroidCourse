package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface ReviewDAO {
    @Query("select * from Review where rommId = :room_id")
    Review getReviewByRoomId(String room_id);

    @Query("select * from Review")
    Review getAllReview();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNewReview(Review... reviews);

    @Delete
    public void deleteReview(Review reviews);

}
