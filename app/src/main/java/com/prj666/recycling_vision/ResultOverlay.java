package com.prj666.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResultOverlay extends AppCompatActivity {


    private ImageView image;
    private TextView matchProbability, objectName, instructions;
    private Bundle bundle = getIntent().getExtras();
    private String object, filename, percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_overlay);

        instructions = findViewById(R.id.instructions);
        objectName  = findViewById(R.id.objectName);
        matchProbability  = findViewById(R.id.matchProbability);
        image  = findViewById(R.id.objectImage);


       // percentage = bundle.getString("percentage");
       // matchProbability.setText(percentage);

        object = bundle.getString("object");
        objectName.setText(object);



        filename = bundle.getString("filename");
        File imgFile = new  File(filename);
        Uri uri = Uri.fromFile(imgFile);
        image.setImageURI(uri);




        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://recycling-vision.herokuapp.com/item/single";
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("itemName", object);
        JSONObject json = new JSONObject(jsonData);

        final String[] result = {""};

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        result[0] = response.getString("data");
                        instructions.setText(result[0]);
                    } else {
                        result[0] = "Error";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                result[0] = "error";
            }
        });
        queue.add(request);


    }

}