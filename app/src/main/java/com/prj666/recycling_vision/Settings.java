package com.prj666.recycling_vision;

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
}