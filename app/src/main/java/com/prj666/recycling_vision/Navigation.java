package com.prj666.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.prj666.recycling_vision.user.Login;

public class Navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //Display loading screen
        super.onCreate(savedInstanceState);
        //if(TOU.accepted){
            if(Login.isUserLoggedIn()){
                setContentView(R.layout.activity_navigation);
            }
            else {
                Intent i = new Intent(Navigation.this, Login.class);
                startActivity(i);
            }
        /*}
        else{
            setContentView(R.layout.activity_tou);
            Intent i
        }*/

        //The TOU use case will likely go here; need to have code bypassing TOU when already accepted
        //May also need to rename the activity/.xml when making that UC

    }
}
