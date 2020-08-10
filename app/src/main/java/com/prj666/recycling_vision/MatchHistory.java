package com.prj666.recycling_vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MatchHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history_recyclerview);

         ArrayList<MatchHistoryItem> matchHistoryItem = new ArrayList<MatchHistoryItem>();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://recycling-vision.herokuapp.com/matchhistoryitem";
        JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        try {
            jo.put("userID", "12");
            json.put(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, json, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                /*try {
                    for (int i = 0; i < response.length(); i++) {
                        matchHistoryItem.add(new MatchHistoryItem(response.get(i).,
                                response.getInt("objectID"),  response.getInt("userID"),
                                response.getString("foundRecyclingInstruction"),
                                (Date) response.get("matchDateTime")));
                    }

                    } catch (JSONException ex) {
                    ex.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        queue.add(request);
        ImageView backArrow = findViewById(R.id.left_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Navigation.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
