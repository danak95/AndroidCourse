package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Review {
    @PrimaryKey
    @NonNull
    private String reviewId;
    private String rommId;
    private String userId;
    private String date;
    private int    rank;
    private String content;

    // Ctors
    public Review() {
        super();
    }

    public Review (String reviewId, String roomId, String userId, String date, int rank, String content)
    {
        super();
        this.setReviewId(reviewId);
        this.setRommId(roomId);
        this.setUserId(userId);
        this.setDate(date);
        this.setRank(rank);
        this.setContent(content);
    }

    public Review(Review copy)
    {
        super();
        this.setReviewId(copy.getReviewId());
        this.setRommId(copy.getRommId());
        this.setUserId(copy.getUserId());
        this.setDate(copy.getDate());
        this.setRank(copy.getRank());
        this.setContent(copy.getContent());
    }

    // Getters and Setters
    @NonNull
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(@NonNull String reviewId) {
        this.reviewId = reviewId;
    }

    public String getRommId() {
        return rommId;
    }

    public void setRommId(String rommId) {
        this.rommId = rommId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
