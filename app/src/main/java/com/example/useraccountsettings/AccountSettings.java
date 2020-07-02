//rest api access tutorial: https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
//JSON-parsing android docs: https://developer.android.com/reference/android/util/JsonReader
//NOTE: reload-'accountSettings.json' file from emulator's storage to view latest changes

package com.example.useraccountsettings;

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
import android.widget.Toast;

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
    Button changePassword, changeLocation, saveButton, logoutButton;

    Settings userSettings;
    File userSettingsFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historySwitch = findViewById(R.id.historySwitch);
        changePassword = findViewById(R.id.changePassword);
        changeLocation = findViewById(R.id.changeLocation);
        saveButton = findViewById(R.id.saveButton);
        logoutButton = findViewById(R.id.logout_button);

        /*creates or updates a text file stored on the user's device that holds a boolean value
        indicating if they enabled or disabled object history tracking*/
        createOrUpdateObjectHistoryFile();

        /*when clicked, open a dialog window with an editable text field to enable the user to
        update their postal code*/
        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });

        //enable the user to reset their password
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch the password reset activity
            }
        });

        //enables/disables the option to track history of previously identified objects
        historySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(historySwitch.isChecked())
                {
                    userSettings.setObjectHistoryEnabled(true);
                }
                else
                {
                    userSettings.setObjectHistoryEnabled(false);
                }
            }
        });

        //updates the user's preference for object history tracking
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {

                    File f = new File(getApplicationContext().getFilesDir(), SETTINGS_FILE);
                    BufferedWriter output = new BufferedWriter(new FileWriter(f));

                    output.write("objectHistoryEnabled="+ userSettings.getObjectHistoryEnabled());
                    output.flush();
                    output.close();

                    Toast.makeText(getApplicationContext(), "Settings saved successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.getMessage();
                }
            }
        });

        //allows user to exit their current session and redirects to the login screen
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display logout confirmation dialog
            }
        });
    }

    private void createOrUpdateObjectHistoryFile()
    {
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
                    FileWriter fw;
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

                //System.out.println(getBooleanValue);
                userSettings.setObjectHistoryEnabled(Boolean.parseBoolean(getBooleanValue));
                historySwitch.setChecked(userSettings.getObjectHistoryEnabled());
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void updateLocation()
    {
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
                        if(updatedPostalCode.length() == 6)
                        {
                            userSettings.setPostalCode(updatedPostalCode.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(true) //close AlertDialog via the navigation bar's "back" button
                .create()
                .show();
    }

}
