package com.example.kardana.androidcourse.Model;

/**
 * Created by Dana Koren and Karin Wasenstein on 26/05/2018.
 *
 * This class represents a User of ESC application
 */

public class User {

    private int userid;
    private String name;
    private String birthDate;
    private String gender;
    private int phone;
    private String email;
    private String password;
    private boolean isAdmin;

    // Ctors
    public User(){
        super();
    }

    public User(int userid, String name, String birthDate, String gender, int phone, String email, String password, boolean isAdmin){
        super();
        this.setUserid(userid);
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setGender(gender);
        this.setPhone(phone);
        this.setEmail(email);
        this.setPassword(password);
        this.setIsAdmin(isAdmin);
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
    }

    // Getters and Setters
    public int getUserid() {
        return userid;
    }

    public void setUserid(int user_id) {
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
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
}
