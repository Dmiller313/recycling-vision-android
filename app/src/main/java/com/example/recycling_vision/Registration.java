package com.example.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class Registration extends AppCompatActivity {


    private TextView edtTxtFName, edtTxtLName, edtTxtPassword, edtTxtRepeatPass,
            edtTxtEmail, edtTxtPhone, edtTxtPostalAddress, edtTxtDate;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnRegister = findViewById(R.id.btnRegister);

        edtTxtFName = findViewById(R.id.edtTxtFName);
        edtTxtLName = findViewById(R.id.edtTxtLName);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        edtTxtRepeatPass = findViewById(R.id.edtTxtRepeatPass);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtPostalAddress = findViewById(R.id.edtTxtPostalAddress);
        edtTxtDate = findViewById(R.id.edtTxtDate);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User user = null;
                try {
                    user = new User(edtTxtFName.getText().toString()+" "+ edtTxtLName.getText().toString(),
                            edtTxtPhone.getText().toString(),edtTxtEmail.getText().toString(),edtTxtPassword.getText().toString(),
                            edtTxtPostalAddress.getText().toString(), edtTxtDate.getText().toString(), false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    sendUser(user);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                /*
                Intent login = new Intent(Registration.this, Login.class);
                startActivity(login);*/
            }
        });
    }

    public void sendUser(User user) throws MalformedURLException {
        URL url = new URL("https://recycling-vision.herokuapp.com/users");
        HttpURLConnection client = null;
        
    }
}