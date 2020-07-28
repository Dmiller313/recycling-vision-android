package com.prj666.recycling_vision;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TakePhoto extends AppCompatActivity implements ConfirmPictureFragment.ConfirmPictureListener {
    private static final int CAMERA_PERMISSION = 1;
    //private SurfaceView preview;
    //private SurfaceHolder previewHolder;
    //private CameraCaptureSession camera;
    private boolean camera2Capable;
    private byte[] img; //temp
    private String filename;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bmp;
    private ImageView previewImage;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        previewImage = findViewById(R.id.image_preview);
        Button takePhoto = findViewById(R.id.takephoto);
        final Button sendPhoto = findViewById(R.id.sendphoto);
        Button back = findViewById(R.id.back_takephoto);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(TakePhoto.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
                else{
                    ActivityCompat.requestPermissions(TakePhoto.this, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    if(ContextCompat.checkSelfPermission(TakePhoto.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        takePicture();
                    }
                    else{
                        Toast.makeText(TakePhoto.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        sendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bmp != null){
                    pictureConfirmation();
                }
                else{
                    Toast.makeText(TakePhoto.this, "Please take a photo first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //takePicture();
        //pictureConfirmation();
    }

    private void takePicture(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(i.resolveActivity(getPackageManager()) != null){
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(bmp != null){
                bmp.recycle();
            }
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp = Bitmap.createScaledBitmap(bmp, 512, 512, true);
            bmp.compress(Bitmap.CompressFormat.JPEG, 0, stream);

            previewImage.setImageBitmap(bmp);
            img = stream.toByteArray();
            File storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File image = File.createTempFile("rv-photo_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), ".jpg", storage);
                filename = image.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPhoto() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String url = "https://rv-tensorflow.herokuapp.com/upload";

                OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file",filename, RequestBody.create(MediaType.parse("image/jpeg"), img))
                         .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Response response = null;
                System.out.println("alright");
                try {
                    response = client.newCall(request).execute();
                    System.out.println("executing");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.isSuccessful()){
                    System.out.println("good");
                    Intent resultOverlay = new Intent(TakePhoto.this, ResultOverlay.class);
                    startActivity(resultOverlay);
                }
                else{
                    System.out.println("no response from server in time");
                }
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        threadPool.execute(runnable);
        Map<String, String> jsonData = new HashMap<>();

        JSONObject json = new JSONObject(jsonData);
        //TODO: Add processing wait screen Threading + fragment
        //startActivity(resultOverlay) must be a blocked thread before processing is done



        /*MultipartRequest request = new MultipartRequest(
                Request.Method.POST, url, jsonData, filename, img, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                //insert results screen code here
                System.out.println("WORKS");
                Intent resultOverlay = new Intent(TakePhoto.this, ResultOverlay.class);
                startActivity(resultOverlay);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: image sending error code goes here
                System.out.println("DOESN'T WORK");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(60000, 3, 3.0f));
        queue.add(request);
        System.out.println("END");*/
    }

    private void pictureConfirmation(){
        FragmentManager fm = getSupportFragmentManager();
        ConfirmPictureFragment dialog = ConfirmPictureFragment.newInstance("Send photo");
        dialog.show(fm, "confirm_picture");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) throws IOException {
        Toast.makeText(this, "Works", Toast.LENGTH_SHORT).show();
        sendPhoto();
        //sendPhoto();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
        //don't sendPhoto();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Navigation.class));
        finishAffinity();
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setCameraFragment() {
        String camera = chooseCamera();

        Fragment fragment;
        if(camera2Capable){

        }
        else{

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String chooseCamera(){
        final CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            int length = manager.getCameraIdList().length;
            boolean proceed = true;
            int selectedCamera = 0;
            for (int i = 0; i < length; i++){
                final CameraCharacteristics characteristics = manager.getCameraCharacteristics(manager.getCameraIdList()[i]);

                final Integer direction = characteristics.get(CameraCharacteristics.LENS_FACING);
                if(direction != null && direction == CameraCharacteristics.LENS_FACING_FRONT && proceed){
                    i = length;
                    proceed = false;
                    selectedCamera = i;
                }

                final StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                if(map == null && proceed){
                    i = length;
                    proceed = false;
                    selectedCamera = i;
                }

                camera2Capable = direction == CameraCharacteristics.LENS_FACING_EXTERNAL ||
                        getHardwareSupportLevel(characteristics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL);
            }
            return manager.getCameraIdList()[selectedCamera];
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Cannot access camera", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean getHardwareSupportLevel(CameraCharacteristics c, int required){
        int device = c.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        if(device == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY){
            return required == device;
        }
        return required <= device;
    }
*/
}
