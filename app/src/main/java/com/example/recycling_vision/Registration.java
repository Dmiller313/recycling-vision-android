package com.example.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Registration extends AppCompatActivity {


    private TextView edtTxtFName, edtTxtLName, edtTxtPassword, edtTxtRepeatPass,
            edtTxtEmail, edtTxtPhone, edtTxtPostalAddress;
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

    }
}