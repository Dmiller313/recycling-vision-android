package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.prj666.recycling_vision.Navigation;
import com.prj666.recycling_vision.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordReset extends AppCompatActivity {

    private TextView email, password, newPassword;
    private Button reset, cancel;
    private String res, res1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        email = findViewById(R.id.edtTxtEmail);
        password = findViewById(R.id.edtTxtPassword);
        newPassword = findViewById(R.id.edtTxtNewPassword);
        reset = findViewById(R.id.btnReset);
        cancel = findViewById(R.id.cancel);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                String url = "https://recycling-vision.herokuapp.com/exists";
                Map<String, String> jsonData = new HashMap<>();

                jsonData.put("email", email.getText().toString());

                JSONObject json = new JSONObject(jsonData);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET, url, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            res = response.getString("status");
                            if(res.equals("exists")){
                                RequestQueue queue1 = Volley.newRequestQueue(getBaseContext());

                                String url1 = "https://recycling-vision.herokuapp.com/passwordreset";
                                Map<String, String> jsonData1 = new HashMap<>();
                                jsonData1.put("email", email.getText().toString());
                                jsonData1.put("password", password.getText().toString());
                                jsonData1.put("newPassword", newPassword.getText().toString());

                                JSONObject json1 = new JSONObject(jsonData1);

                                JsonObjectRequest request1 = new JsonObjectRequest(
                                        Request.Method.POST, url1, json1, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            res1 = response.getString("status");
                                            if(res1.equals("success")){
                                                Intent login = new Intent(PasswordReset.this, Login.class);
                                                PasswordReset.this.startActivity(login);
                                            }else if(res1.equals("error")){
                                                Toast.makeText(getApplicationContext(), "An Error has occurred try again", Toast.LENGTH_LONG).show();
                                            } else if(res1.equals("unauthorized")){
                                                Toast.makeText(getApplicationContext(), "You entered and Invalid password", Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(), "An Error has occurred try again", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                queue1.add(request1);
                            }else{
                                Toast.makeText(getApplicationContext(), "you entered an Invalid Email", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(PasswordReset.this, Navigation.class);
                PasswordReset.this.startActivity(nav);
            }
        });
    }
}