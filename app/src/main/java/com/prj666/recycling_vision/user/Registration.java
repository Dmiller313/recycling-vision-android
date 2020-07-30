package com.prj666.recycling_vision.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validateData()) {



                    try {
                        User user = new User(edtTxtUserName.getText().toString(), edtTxtPhone.getText().toString(), edtTxtEmail.getText().toString(),
                                edtTxtPassword.getText().toString(), edtTxtPostalAddress.getText().toString(),
                                edtTxtDate.getText().toString(), false);


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
                                    if (validEmail.equals("available")) {
                                        Intent i = new Intent(getBaseContext(), ValidationEmail.class);
                                        i.putExtra("User", user);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(Registration.this, "A user has already used this email address", Toast.LENGTH_LONG).show();

                                    }
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
                edtTxtRepeatPass.getText().toString().isEmpty() &&
                !edtTxtRepeatPass.getText().toString().matches(PASSWORD_PATTERN)) {
            Toast.makeText(Registration.this, "Invalid Password, Must contain at least 1 digit, 1 lower case letter, 1 uppercase letter, 1 special character and" +
                            " be more than 8 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (edtTxtEmail.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(edtTxtEmail.getText().toString()).matches()) {
            Toast.makeText(Registration.this, "Invalid email", Toast.LENGTH_LONG).show();

            return false;
        }
        if (edtTxtPhone.getText().toString().isEmpty()) {
            Toast.makeText(Registration.this, "Invalid phone", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtTxtUserName.getText().toString().isEmpty() &&
        !(edtTxtUserName.getText().toString().length() <= 32) ) {
            Toast.makeText(Registration.this, "Invalid Username", Toast.LENGTH_LONG).show();
            return false;
        }

        if (edtTxtDate.getText().toString().isEmpty()) {
            Toast.makeText(Registration.this, "Invalid Date", Toast.LENGTH_LONG).show();

            return false;
        }
        if (edtTxtPostalAddress.getText().toString().isEmpty() ) {
            Toast.makeText(Registration.this, "Invalid Postal Code", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}