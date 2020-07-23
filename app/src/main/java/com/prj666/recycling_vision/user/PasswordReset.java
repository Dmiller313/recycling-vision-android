package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prj666.recycling_vision.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordReset extends AppCompatActivity {

    private TextView email, password, newPassword;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        email = findViewById(R.id.edtTxtEmail);
        password = findViewById(R.id.edtTxtPassword);
        newPassword = findViewById(R.id.edtTxtNewPassword);
        button = findViewById(R.id.btnReset);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(PasswordReset.this);

                String url = "https://recycling-vision.herokuapp.com/passwordreset";
                Map<String, String> jsonData = new HashMap<>();
                jsonData.put("email", email.getText().toString());
                jsonData.put("password", password.getText().toString());
                jsonData.put("newPassword", newPassword.getText().toString());


                JSONObject json = new JSONObject(jsonData);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST, url, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());


                    }
                });
                queue.add(request);
                Intent login = new Intent(PasswordReset.this, Login.class);
                PasswordReset.this.startActivity(login);
            }
        });
    }
}