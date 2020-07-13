package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prj666.recycling_vision.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    static final String salt = "salt";
    private TextView edtTxtFName, edtTxtLName, edtTxtPassword, edtTxtRepeatPass,
            edtTxtEmail, edtTxtPhone, edtTxtPostalAddress, edtTxtDate;
    private String passwordHash;
    private String validEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button btnRegister = findViewById(R.id.btnRegister);

        edtTxtFName = findViewById(R.id.edtTxtFName);
        edtTxtLName = findViewById(R.id.edtTxtLName);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        edtTxtRepeatPass = findViewById(R.id.edtTxtRepeatPass);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtPostalAddress = findViewById(R.id.edtTxtPostalAddress);
        edtTxtDate = findViewById(R.id.edtTxtDate);



        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://recycling-vision.herokuapp.com/exists";
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("email", "dmiller@myseneca.ca");

        JSONObject json = new JSONObject(jsonData);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, json, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    validEmail = response.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());



            }
        });
        queue.add(request);




        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(validateData()){

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
                    if (validEmail.equals("available")){
                        Intent i = new Intent();
                        i.putExtra("User",user);

                    }else {
                        Toast.makeText(Registration.this, "A user has already used this email address", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(Registration.this, "Missing Information", Toast.LENGTH_LONG).show();

                }








            }
        });
    }

    private boolean validateData(){
        if(!edtTxtPassword.getText().toString().equals(edtTxtRepeatPass.getText().toString())){
            return false;
        }

        if(edtTxtEmail.getText().toString().isEmpty()){
            return false;
        }
        if(edtTxtPhone.getText().toString().isEmpty()){
            return false;
        }
        if(edtTxtFName.getText().toString().isEmpty()){
            return false;
        }
        if(edtTxtLName.getText().toString().isEmpty()){
            return false;
        }
        if (edtTxtDate.getText().toString().isEmpty()){
            return false;
        }
        if (edtTxtPostalAddress.getText().toString().isEmpty()){
            return false;
        }
        if (edtTxtPassword.getText().toString().isEmpty()){
            return false;
        }

        return !edtTxtRepeatPass.getText().toString().isEmpty();
    }
    public static String getSecurePassword(String password) throws  NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}