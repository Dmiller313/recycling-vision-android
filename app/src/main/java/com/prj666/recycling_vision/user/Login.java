package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import okhttp3.internal.platform.Platform;

public class Login extends AppCompatActivity
{
    private static boolean LOGGED_IN = false;

    private EditText username, password;
    private TextView loginErrorMessage;
    private Button signInButton, accountRecoveryButton;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        signInButton = findViewById(R.id.login_button);
        accountRecoveryButton = findViewById(R.id.recoverAccount);
        loginErrorMessage = findViewById(R.id.loginErrorMessage);
        loginProgressBar = findViewById(R.id.login_progress_bar);


        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //create JSON object using username and password from login form
                //and send it to the REST API for authentication
                JSONObject jsonLogin = new JSONObject();
                try
                {
                    jsonLogin.put("username", username.getText().toString());
                    jsonLogin.put("password", password.getText().toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                signInButton.setVisibility(View.INVISIBLE);
                accountRecoveryButton.setVisibility(View.INVISIBLE);
                loginProgressBar.setVisibility(View.VISIBLE);

                checkIfUserExists(jsonLogin);
            }
        });

        username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(loginErrorMessage != null)
                {
                    loginErrorMessage.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(loginErrorMessage != null)
                {
                    loginErrorMessage.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        accountRecoveryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent recoverAccount = new Intent(getApplicationContext(), AccountRecovery.class);
                startActivity(recoverAccount);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(username != null || password != null || loginErrorMessage != null)
        {
            assert username != null;
            username.setText("");
            password.setText("");
            loginErrorMessage.setText("");
        }
    }

    public void checkIfUserExists(JSONObject jsonLogin)
    {
        //use Volley to handle HTTP request to retrieve existing user from database
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String apiEndpointUrl = "https://recycling-vision.herokuapp.com/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                apiEndpointUrl,
                jsonLogin,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println("user exists!");

                        //clear any previous error messages on incorrect login details
                        loginErrorMessage.setText("");
                        //Toast.makeText(getApplicationContext(), "User exists!", Toast.LENGTH_SHORT).show();

                        //set authentication flag and redirect user to app's navigation menu
                        LOGGED_IN = true;
                        Intent toNavigationMenu = new Intent(getApplicationContext(), Navigation.class);
                        startActivity(toNavigationMenu);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //System.out.println("user doesn't exist!");

                        //if there is an error is comparing the user's login credentials against their
                        //stored credentials in the database, display an error message
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        signInButton.setVisibility(View.VISIBLE);
                        accountRecoveryButton.setVisibility(View.VISIBLE);
                        loginErrorMessage.setText(R.string.username_password_error_message);
                        Toast.makeText(getApplicationContext(), "No user found!", Toast.LENGTH_SHORT).show();
                    }
        });
        //add the current request to the RequestQueue
        queue.add(request);
    }

    //redirect the user to the registration screen if they selected the option to create an account
    public void setCreateAccount(View view)
    {
        Intent toRegistration = new Intent(this, Registration.class);
        startActivity(toRegistration);
    }

    //check if the user has been authenticated and logged into the app
    public static boolean isUserLoggedIn()
    {
        if(LOGGED_IN)
            return true;
        else
            return false;
    }
}
