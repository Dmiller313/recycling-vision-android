package com.prj666.recycling_vision;

import java.util.Calendar;
import java.util.Date;

public class MatchHistoryItem {
    //IDs
    Integer historyItemID;
    Integer objectID;
    Integer userID;

    //Data
    String foundRecyclingInstruction;
    Date matchDateTime;

    //Constructor for a new history item
    public MatchHistoryItem(Integer historyItemID, Integer objectID, Integer userID) {
        this.historyItemID = historyItemID;
        this.objectID = objectID;
        this.userID = userID;

        this.foundRecyclingInstruction = null;
        this.matchDateTime = Calendar.getInstance().getTime();//Set current time
    }

    //Constructor for an existing history item
    public MatchHistoryItem(Integer historyItemID, Integer objectID, Integer userID, String foundRecyclingInstruction, Date matchDateTime) {
        this.historyItemID = historyItemID;
        this.objectID = objectID;
        this.userID = userID;

        this.foundRecyclingInstruction = foundRecyclingInstruction;
        this.matchDateTime = matchDateTime;
    }
}
