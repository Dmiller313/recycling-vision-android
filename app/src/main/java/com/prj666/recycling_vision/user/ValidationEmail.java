package com.prj666.recycling_vision.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prj666.recycling_vision.Navigation;
import com.prj666.recycling_vision.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ValidationEmail extends AppCompatActivity {

    private Button backButton;
    private TextView userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validationemail);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        final User user;
        user = getIntent().getExtras().getParcelable("User");
        try {
            //temp user object until registration is made


            //Commented until activity_validationemail is made
            /*backButton.findViewById(R.id.back);

            backButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    startActivity(new Intent(ValidationEmail.this, Navigation.class));
                    finishAffinity();
                }
            });*/

            //Commented until Registration class/activity are made

            /*Intent intent = getIntent();
            String [] userData = intent.getStringArrayExtra("");

            User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);*/

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "https://recycling-vision.herokuapp.com/emailer";
            Map<String, String> jsonData = new HashMap<>();
            jsonData.put("username", user.getUserName());
            jsonData.put("email", user.getEmail());
            jsonData.put("password", user.getPassword());
            jsonData.put("phoneNum", user.getPhoneNum());
            jsonData.put("postalCode", user.getPostalCode());
            jsonData.put("dateOfBirth", (user.getDateOfBirth().getYear() + 1900) + "-0" +
                    (user.getDateOfBirth().getMonth() + 1) + "-" + user.getDateOfBirth().getDate() );
            JSONObject json = new JSONObject(jsonData);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, url, json, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    userMessage.setText(R.string.vemail_success);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    userMessage.setText(R.string.vemail_error);
                }
            });
            queue.add(request);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
