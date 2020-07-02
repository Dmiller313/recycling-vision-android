package com.example.useraccountsettings;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/*
* First generic: String -> type of referenced passed to doInBackground
* Second generic: Void -> type of referenced passed to onProgressUpdate
* Third generic: String -> type of reference returned by doInBackground and its value passed to
*                           onPostExecute
* */
class LoadUserInfo extends AsyncTask<Object, Void, Void>
{
    private JSONArray jsa = new JSONArray();

    //first index of 'String...' parameter: table name to access
    //second index of 'String...' parameter: JSON object key name to get its associated value
    @Override
    protected Void doInBackground(Object... name)
    {
        try
        {
            // create a new url
            URL url = new URL("http://192.168.1.107:8080/"+name[0]);

            //open a connection to the url
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //check for the success server response
            if (httpURLConnection.getResponseCode() == 200)
            {
                System.out.println("connection to '" + name[0] + "' table established");

                readJsonInputStream(httpURLConnection);
            }
            else
            {
                throw new IOException();
            }
        }
        catch (IOException | JSONException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void userAccountData(JsonReader jsonReader, InputStreamReader isr) throws IOException, JSONException
    {

        //being reading the array (find the '[')
        jsonReader.beginArray();

        int i=0;
        //begin looping through each JSON object in the array
        while (jsonReader.hasNext())
        {
            //find the opening brace of the JSON object
            jsonReader.beginObject();

            JSONObject userData = new JSONObject();
            //begin looping through each key-value pair in the JSON object
            while (jsonReader.hasNext())
            {
                //get the current key name
                String key = jsonReader.nextName();

                //check for the matching key
                switch (key) {
                    case "userID":
                        userData.put(key, jsonReader.nextInt());
                        break;
                    case "username":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "email":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "password":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "phoneNum":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "postalCode":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "dateOfBirth":
                        userData.put(key, jsonReader.nextString());
                        break;
                    case "validationStatus":
                        userData.put(key, jsonReader.nextInt());
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            //close the object (read the closing brace '}')
            jsonReader.endObject();

            jsa.put(i, userData);
            i++;
        }

        //close the array (read the closing brace ']')
        jsonReader.endArray();

        //close the reading stream
        jsonReader.close();

        //close the stream
        isr.close();
    }

    private void readJsonInputStream(HttpURLConnection httpURLConnection) throws IOException, JSONException
    {
        //get input stream from the connection
        InputStream is = httpURLConnection.getInputStream();

        //convert the binary stream to a text stream in utf-8 encoding scheme
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");

        //parse the text stream (InputStreamReader) into JSON
        JsonReader jsonReader = new JsonReader(isr);

        this.userAccountData(jsonReader, isr);

        //disconnect from the api
        httpURLConnection.disconnect();
    }

    public Object getJsonFieldValue(int jsonObjectIndexNumber, String fieldName) throws JSONException
    {
        JSONObject targetObject = jsa.getJSONObject(jsonObjectIndexNumber);

        while(targetObject.length() > 0)
        {
            if (targetObject.has(fieldName))
            {
                return targetObject.get(fieldName);
            }
        }
        return null;
    }


}
