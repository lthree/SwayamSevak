package com.example.pranav.swayamsevakclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Safia on 2/6/18.
 */

public class ViewContributionsActivity extends AppCompatActivity {

    private ListView mListView;
    private String json_query_result;
    private String[] maintitle1 = {"Title 1", "Title 2", "Title 3", "Title 4", "Title 5"};
    private String[] subtitle1 = {"Sub Title 1", "Sub Title 2", "Sub Title 3", "Sub Title 4", "Sub Title 5"};
    private String[] maintitle = {};
    private Integer[] subtitle = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contributions);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.viewcontr_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // fetch the data from DB into maintitle and subtitle
        get_participant_event_data();


    }

    // Communicate with DB controller to get participant event list along with their contribution
    void get_participant_event_data() {

        // creating a static list of event names and their contribution
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> row_map = new HashMap<String, Integer>();
        row_map.put("Event_name1",1200);
        list.add(row_map);
        row_map.put("Event_name2",1400);
        list.add(row_map);
        row_map.put("Event_name3",1600);
        list.add(row_map);
        row_map.put("Event_name4",1800);
        list.add(row_map);




        final String PartID = "1234567";   // TODO Get participant ID first
        // create JSON object POST request
        StringRequest participantEventListRequest = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_PARTCIPANT_EVENT_AND_CONTRIBUTIONS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;
                display_participant_event_along_with_their_contr();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                Map<String, String> params = new HashMap<>();
                params.put("loginToken", sessionManager.getLoginToken());
                params.put("ParticipantID", PartID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        // Insert network list request into Volley queue
        AppController.getInstance().addToRequestQueue(participantEventListRequest);

    }
    public void display_participant_event_along_with_their_contr()
    {
        try {
            JSONObject json_response = new JSONObject(json_query_result);
            JSONArray json_main_node = json_response.getJSONArray("EventListAndContribution");
            // extract event and initialize Event array list

            for (int j = 0; j < json_main_node.length(); j++) {
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                maintitle[j]= json_child_node.getString("Event_name");
                subtitle[j]= json_child_node.getInt("Event_contr");
            }
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        // bind the obtained values to the adapter
        ViewContrAdapter adapter = new ViewContrAdapter(this, maintitle, subtitle);
        mListView = findViewById(R.id.list_event);
        mListView.setAdapter(adapter);

    }
}
