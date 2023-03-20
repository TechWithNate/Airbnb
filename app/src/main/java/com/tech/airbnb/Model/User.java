package com.tech.airbnb.Model;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String userID;
    private String firstname;
    private String lastname;
    private String birthday;
    private String email;
    private String imgUrl;


    public User() {
    }

    public User(String userID, String firstname, String lastname, String birthday, String email) {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
    }

    public User(String userID, String firstname, String lastname, String birthday, String email, String imgUrl) {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
        this.imgUrl = imgUrl;
    }



    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
