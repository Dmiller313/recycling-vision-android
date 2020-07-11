package com.prj666.recycling_vision.user;

import java.util.Date;

public class User {

    // Class variables
    private int userID;
    private String userName;
    private String phoneNum;
    private String email;
    private String password;
    private String postalCode;
    private Date dateOfBirth;

    //Constructor

    public User() {
        this.userID = -1;
        this.userName = null;
        this.phoneNum = null;
        this.email = null;
        this.password = null;
        this.postalCode = null;
        this.dateOfBirth = null;
    }

    public User(int userID, String userName, String phoneNum, String email, String password, String postalCode, Date dateOfBirth) {
        this.userID = userID;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
        this.postalCode = postalCode;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String userName, String phoneNum, String email, String password, String postalCode, Date dateOfBirth) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
        this.postalCode = postalCode;
        this.dateOfBirth = dateOfBirth;
    }

    // Setters and Getters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

