package com.prj666.recycling_vision.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prj666.recycling_vision.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {


    private TextView edtTxtUserName, edtTxtPassword, edtTxtRepeatPass,
            edtTxtEmail, edtTxtPhone, edtTxtPostalAddress, edtTxtDate;
    private String validEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button btnRegister = findViewById(R.id.btnRegister);

        edtTxtUserName = findViewById(R.id.edtTxtUserName);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        edtTxtRepeatPass = findViewById(R.id.edtTxtRepeatPass);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtPostalAddress = findViewById(R.id.edtTxtPostalAddress);
        edtTxtDate = findViewById(R.id.edtTxtDate);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://recycling-vision.herokuapp.com/exists";
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("email", edtTxtEmail.getText().toString());

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
                if (validateData()) {


                    try {
                        User user = new User(edtTxtUserName.getText().toString(), edtTxtPhone.getText().toString(), edtTxtEmail.getText().toString(),
                                edtTxtPassword.getText().toString(), edtTxtPostalAddress.getText().toString(),
                                edtTxtDate.getText().toString(), false);

                        if (validEmail.equals("available")) {
                            Intent i = new Intent(getBaseContext(), ValidationEmail.class);
                            i.putExtra("User", user);
                            startActivity(i);

                        } else {
                            Toast.makeText(Registration.this, "A user has already used this email address", Toast.LENGTH_LONG).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Registration.this, "Missing Information", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private boolean validateData() {
        if (!edtTxtPassword.getText().toString().equals(edtTxtRepeatPass.getText().toString()) &&
                edtTxtPassword.getText().toString().isEmpty() &&
                edtTxtRepeatPass.getText().toString().isEmpty()) {
            return false;
        }

        if (edtTxtEmail.getText().toString().isEmpty()) {
            return false;
        }
        if (edtTxtPhone.getText().toString().isEmpty()) {
            return false;
        }
        if (edtTxtUserName.getText().toString().isEmpty()) {
            return false;
        }

        if (edtTxtDate.getText().toString().isEmpty()) {
            return false;
        }
        if (edtTxtPostalAddress.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }
}