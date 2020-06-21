package com.example.recycling_vision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ValidationEmail extends AppCompatActivity {

    Button backButton;
    TextView userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validationemail);

        backButton.findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ValidationEmail.this, MainActivity.class));
                finishAffinity();
            }
        });

        Intent intent = getIntent();
        userMessage.findViewById(R.id.results);
        String [] userData = intent.getStringArrayExtra("");
        //Commented until User class is made
        /*User user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
        try {
            sendEmail(user);
            userMessage.setText(R.string.emailSuccessText);
        } catch(Exception e){
            System.out.println("The RV URL is currently unavailable");
            userMessage.setText(R.string.emailErrText);
        }*/
    }
//Commented until User class is made
/*
    public void sendEmail(User user) throws MalformedURLException {
        URL url = new URL("https://recycling-vision.herokuapp.com/emailer");
        HttpURLConnection client = null;
        try{
            //Setting up the connection properties
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/json; utf-8");
            client.setRequestProperty("Accept", "application/json");
            client.setDoOutput(true);

            //JSON string to be sent in the request - see templates for examples
            String json = "{ \"name\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() +
                    "\",\"password\":\"" + user.getPassword() + "\",\"phoneNum\":\"" + user.getPhoneNum() +
                    "\",\"postalCode\":\"" + user.getPostalCode() + "\",\"dateOfBirth\":\"" +
                    user.getDateOfBirth() + "\"}";
            OutputStream os = client.getOutputStream();
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            //get response back from the server: is it necessary?

        } catch(Exception e){
            System.out.println("Error making connection");
        }
    }*/
}
