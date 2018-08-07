package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Review {
    @PrimaryKey
    @NonNull
    private String reviewId;
    private String roomId;
    private String userId;
    private String date;
    private double rank;
    private String content;
    private String imagePath;

    public final static String IMAGE_PATH="Reviews";

    // Ctors
    public Review() {
        super();
    }

    public Review (String reviewId, String roomId, String userId, String date, double rank, String content, String imagePath)
    {
        super();
        this.setReviewId(reviewId);
        this.setRoomId(roomId);
        this.setUserId(userId);
        this.setDate(date);
        this.setRank(rank);
        this.setContent(content);
        this.setImagePath(imagePath);
    }

    public Review(Review copy)
    {
        super();
        this.setReviewId(copy.getReviewId());
        this.setRoomId(copy.getRoomId());
        this.setUserId(copy.getUserId());
        this.setDate(copy.getDate());
        this.setRank(copy.getRank());
        this.setContent(copy.getContent());
        this.setImagePath(copy.imagePath);
    }

    // Getters and Setters
    @NonNull
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(@NonNull String reviewId) {
        this.reviewId = reviewId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
