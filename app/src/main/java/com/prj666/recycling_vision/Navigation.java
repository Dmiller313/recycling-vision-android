package com.prj666.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.prj666.recycling_vision.recycling_references.RecyclingReference;
import com.prj666.recycling_vision.user.AccountSettings;
import com.prj666.recycling_vision.user.Login;
import com.prj666.recycling_vision.user.Settings;

public class Navigation extends AppCompatActivity {

    private Button takePhoto;
    private Button settings;
    private Button reference;
    private Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //Display loading screen
        super.onCreate(savedInstanceState);
        //if(TOU.accepted){
            if(Login.isUserLoggedIn()){
                setContentView(R.layout.activity_navigation);
                takePhoto = findViewById(R.id.takephoto);
                settings = findViewById(R.id.settings);
                reference = findViewById(R.id.reference);
                history = findViewById(R.id.history);

                takePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

                        //Intent i = new Intent(Navigation.this, TakePhoto.class);
                        //startActivity(i);
                    }
                });

                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Navigation.this, AccountSettings.class);
                        startActivity(i);
                    }
                });

                reference.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Navigation.this, RecyclingReference.class);
                        startActivity(i);
                    }
                });

                history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

                        //Intent i = new Intent(Navigation.this, .class);
                        //startActivity(i);

                    }
                });

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
