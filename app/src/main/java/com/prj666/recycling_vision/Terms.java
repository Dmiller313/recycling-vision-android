package com.prj666.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //TODO: set button to activate/appear when scrollview hits bottom (https://stackoverflow.com/questions/4953692/android-detecting-when-scrollview-hits-bottom)
        //TODO: create text for TOU
        //TODO: create some method of local persistence to check if TOU accepted; see Login
        //TODO: change Navigation to accept TOU properly
        //TODO: create onClickListeners for the buttons, "Decline" closes the app, "Accept" persists the choice
        //TODO: set up onBackPressed()

    }
}