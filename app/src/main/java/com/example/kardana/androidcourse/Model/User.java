package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Dana Koren and Karin Wasenstein on 26/05/2018.
 *
 * This class represents a User of ESC application
 */

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String userid;
    private String name;
    private String birthDate;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private boolean isAdmin;
    private String imagePath;

    // Ctors
    public User(){
        super();
        this.setUserid("1");
    }

    public User(String name, String birthDate, String gender, String phone, String email, String password, boolean isAdmin, String imagePath){
        super();
        this.setUserid("1");
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setGender(gender);
        this.setPhone(phone);
        this.setEmail(email);
        this.setPassword(password);
        this.setIsAdmin(isAdmin);
        this.setImagePath(imagePath);
    }

    public User(User copy)
    {
        super();
        this.setUserid(copy.getUserid());
        this.setName(copy.getName());
        this.setBirthDate(copy.getBirthDate());
        this.setGender(copy.getGender());
        this.setPhone(copy.getPhone());
        this.setEmail(copy.getEmail());
        this.setPassword(copy.getPassword());
        this.setIsAdmin(copy.getIsAdmin());
        this.setImagePath(copy.getImagePath());
    }

    // Getters and Setters
    public String getUserid() {
        return userid;
    }

    public void setUserid(String user_id) {
        this.userid = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birth_Date) {
        this.birthDate = birth_Date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
