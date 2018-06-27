package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pranav.swayamsevakclient.AppConfig.URL_LIST_EVENTS;

public class ListAvailableEventsActivity extends NavigationDrawerBaseClass {

    private String json_query_result;
    private EventListAdapter mEventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_events);
        access_event_data_and_show();
    }

    // generates DB request to access event data, once received it renders the content.
    public void access_event_data_and_show(){

        // Creates JSON object Post request
        StringRequest eventListRequest = new StringRequest(Method.POST, AppConfig.URL_LIST_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;
                display_event_list();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getParams(){
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                Map<String, String> params  = new HashMap<String, String>();
                params.put("loginToken", sessionManager.getLoginToken());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // Fetches session id
        //SessionManager sessionManager = new SessionManager(getApplicationContext());
        //eventListRequest.setLoginTokenInRequestHeader(sessionManager.getLoginToken()); // adds session information
        //eventListRequest.setLoginTokenInRequestHeader("2018-03-12%2018%3A23%3A1178");
        // Attaches the created request to Volley queue
        AppController.getInstance().addToRequestQueue(eventListRequest);
    }

    public void display_event_list(){
        // extract information from json result
        final ArrayList<Event> event_data_list = new ArrayList<Event>();
        try {
            JSONObject json_response = new JSONObject(json_query_result);
            JSONArray json_main_node = json_response.optJSONArray("eventlist");

            // extract event and initialize Event array list

            for (int j = 0; j < json_main_node.length(); j++) {
                Event event = new Event();
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                event.setEventTitle(json_child_node.optString("title"));
                event.setEventId(json_child_node.optInt("idEvent"));
                event_data_list.add(event);
            }
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        // bind it to the adapter
        mEventAdapter = new EventListAdapter(this, event_data_list);
        ListView event_list_view = findViewById(R.id.list_events_list_view);
        event_list_view.setAdapter(mEventAdapter);
        event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = event_data_list.get(i);
                Intent open_event_details_intent = new Intent(getApplicationContext(), ShowEventDetailsActivity.class);
                open_event_details_intent.putExtra("event_id", event.getEventId());
                startActivity(open_event_details_intent);
            }
        });
    }

}
