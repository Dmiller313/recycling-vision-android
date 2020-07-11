package com.prj666.recycling_vision.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.prj666.recycling_vision.R;
import com.prj666.recycling_vision.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AccountSettings extends AppCompatActivity {

    private final String SETTINGS_FILE = "accountSettings.txt";
    Switch historySwitch;
    Button changePassword, changeLocation, saveButton;
    Settings userSettings;
    File userSettingsFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        /*historySwitch = findViewById(R.id.historySwitch);
        changePassword = findViewById(R.id.changePassword);
        changeLocation = findViewById(R.id.changeLocation);
        saveButton = findViewById(R.id.saveButton);
        userSettings = new Settings(null, null);
        userSettingsFile = new File(this.getFilesDir(), SETTINGS_FILE);
        try {
            if (!userSettingsFile.exists()) {
                if (userSettingsFile.createNewFile()) {
                    System.out.println("file created successfully");
                    AssetManager userSettingsAsset = getAssets();
                    InputStream is = userSettingsAsset.open(SETTINGS_FILE);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String fileContents = "";
                    FileWriter fw = null;
                    fw = new FileWriter(userSettingsFile);
                    while ((fileContents = br.readLine()) != null) {
                        fw.write(fileContents);
                    }
                    fw.close();
                    br.close();
                    is.close();
                    System.out.println("file written successfully");
                } else {
                    System.out.println("error creating the file");
                }
            } else {
                FileReader fr = new FileReader(userSettingsFile);
                BufferedReader br = new BufferedReader(fr);
                String loadContents = "", data = "";
                while ((loadContents = br.readLine()) != null) {
                    data = loadContents;
                }
                br.close();
                fr.close();
                String getBooleanValue = data.substring(data.indexOf("=") + 1);
                System.out.println(getBooleanValue);
                userSettings.objectHistoryEnabled = Boolean.parseBoolean(getBooleanValue);
                historySwitch.setChecked(userSettings.objectHistoryEnabled);
            }
        }
        catch(IOException e) {
            e.getMessage();
        }

        //when clicked, open a dialog window with an editable text field to enable the user to
        //update their postal code
        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });

        historySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userSettings.objectHistoryEnabled = historySwitch.isChecked();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File f = new File(getApplicationContext().getFilesDir(), SETTINGS_FILE);
                    BufferedWriter output = new BufferedWriter(new FileWriter(f));
                    output.write("objectHistoryEnabled="+ userSettings.objectHistoryEnabled);
                    output.flush();
                    output.close();
                }
                catch (IOException e){
                    e.getMessage();
                }
            }
        });
    }

    private void updateLocation() {
        //set layout parameters for the EditText to appear within the AlertDialog window
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText updatedPostalCode = new EditText(this);
        updatedPostalCode.setLayoutParams(lp);
        new AlertDialog.Builder(this)
                .setTitle("Enter a new postal code")
                .setView(updatedPostalCode) //embed the EditText into the AlertDialog
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(updatedPostalCode.length() == 6) {
                            userSettings.postalCode = updatedPostalCode.getText().toString();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(true) //close AlertDialog via the navigation bar's "back" button
                .create()
                .show();*/
    }
}