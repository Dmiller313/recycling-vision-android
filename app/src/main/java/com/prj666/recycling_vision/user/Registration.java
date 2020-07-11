package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prj666.recycling_vision.R;



import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

public class Registration extends AppCompatActivity {

    static final String salt = "salt";
    private TextView edtTxtFName, edtTxtLName, edtTxtPassword, edtTxtRepeatPass,
            edtTxtEmail, edtTxtPhone, edtTxtPostalAddress, edtTxtDate;
    private Button btnRegister;
    private String passwordHash;
    private Date userDate;
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


                try {
                    passwordHash = getSecurePassword(edtTxtPassword.getText().toString() + salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                User user = null;
                try {
                    user = new User(edtTxtFName.getText().toString()+" "+ edtTxtLName.getText().toString(),
                            edtTxtPhone.getText().toString(),edtTxtEmail.getText().toString(), passwordHash,
                            edtTxtPostalAddress.getText().toString(), edtTxtDate.getText().toString(), false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                sendUser(user);

                /*
                Intent login = new Intent(Registration.this, Login.class);
                startActivity(login);*/
            }
        });
    }
    //todo
    public void sendUser(User user)  {

        
    }

    public static String getSecurePassword(String password) throws  NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String sha_256hex = bytesToHex(hash);
        return  sha_256hex;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}