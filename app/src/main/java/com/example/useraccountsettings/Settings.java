package com.example.useraccountsettings;

import java.util.Date;

public class Settings {
    //User variables
    Integer userID;
    String postalCode;

    //Setting variables
    Boolean objectHistoryEnabled;
    Date timestampDateTime;

    //Note that this class can never be instantiated without a userID, therefore there is no empty constructor
    public Settings(Integer userID, String postalCode) {
        this.userID = userID;
        this.postalCode = postalCode;
    }

    public Settings(Integer userID, String postalCode, Boolean objectHistoryEnabled, Date timestampDateTime) {
        //user
        this.userID = userID;
        this.postalCode = postalCode;
        //setting
        this.objectHistoryEnabled = objectHistoryEnabled;
        this.timestampDateTime =  timestampDateTime;
    }

    //setter and getter methods for the class data variables
    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }

    public Integer getUserID()
    {
        return this.userID;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getPostalCode()
    {
        return this.postalCode;
    }

    public void setObjectHistoryEnabled(Boolean objectHistoryEnabled)
    {
        this.objectHistoryEnabled = objectHistoryEnabled;
    }

    public Boolean getObjectHistoryEnabled()
    {
        return this.objectHistoryEnabled;
    }

    public void setTimestampDateTime(Date timestampDateTime)
    {
        this.timestampDateTime = timestampDateTime;
    }

    public Date getTimestampDateTime()
    {
        return this.timestampDateTime;
    }
}