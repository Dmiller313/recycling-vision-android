package com.example.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //Display loading screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The TOU use case will likely go here; need to have code bypassing TOU when already accepted
        //May also need to rename the activity/.xml when making that UC

    }
}
