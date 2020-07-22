package com.prj666.recycling_vision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TakePhoto extends AppCompatActivity {
    private static final int CAMERA_PERMISSION = 1;
    private SurfaceView preview;
    private SurfaceHolder previewHolder;
    private CameraCaptureSession camera;
    private byte[] img; //temp
    private String filename;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
    }

    private void sendPhoto(){//Bitmap photo){
        //if(photo.isTaken()){
            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "https://rv-tensorflow.herokuapp.com/upload";
            Map<String, String> jsonData = new HashMap<>();

            JSONObject json = new JSONObject(jsonData);

            MultipartRequest request = new MultipartRequest(
                    Request.Method.POST, url, jsonData, filename, img, new Response.Listener<NetworkResponse>() {

                @Override
                public void onResponse(NetworkResponse response) {
                    //insert results screen code here
                    Intent resultOverlay = new Intent(TakePhoto.this, ResultOverlay.class);
                    startActivity(resultOverlay);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: image sending error code goes here
                }
            });
            queue.add(request);
        //}
    }

}
