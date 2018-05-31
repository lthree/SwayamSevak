package com.example.pranav.swayamsevakclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticipantUnsubscribeActivity extends AppCompatActivity {

    private String json_query_result;
    private EventListAdapter mEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_unsubscribe);
        access_participant_event_data_and_show();
    }

    // generates DB request to access event data, once received it renders the content.
    public void access_participant_event_data_and_show() {

        // Creates JSON object Post request
        StringRequest participantEventListRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LIST_PARTICIPANT_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;
                display_participant_event_list();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginToken", sessionManager.getLoginToken());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        // Attaches the created request to Volley queue
        AppController.getInstance().addToRequestQueue(participantEventListRequest);
    }

    public void display_participant_event_list() {
        // extract information from json result
        final ArrayList<Event> participant_event_data_list = new ArrayList<Event>();
        try {
            JSONObject json_response = new JSONObject(json_query_result);
            JSONArray json_main_node = json_response.optJSONArray("eventlist");

            // extract event and initialize Event array list

            for (int j = 0; j < json_main_node.length(); j++) {
                Event event = new Event();
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                event.setEventTitle(json_child_node.optString("title"));
                event.setEventId(json_child_node.optInt("idEvent"));
                participant_event_data_list.add(event);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        // bind it to the adapter
        mEventAdapter = new EventListAdapter(this, participant_event_data_list);
        ListView participant_event_list_view = findViewById(R.id.list_events_list_view);
        participant_event_list_view.setAdapter(mEventAdapter);
        participant_event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = participant_event_data_list.get(i);

                // send event id and volunteer id to DB controller
                JSONObject participantDetails = new JSONObject();
                String participantID, eventID; // TODO initialise
                participantID = "";
                eventID = "";
                try {
                    participantDetails.put("participantid", participantID);
                    participantDetails.put("eventid", eventID);
                    // initialize unsubscribe request object
                    JsonObjectRequest participantUnsubscriptionRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_UNSUBSCRIBE_PARTICIPANT_FROM_EVENT, participantDetails, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(ParticipantUnsubscribeActivity.this, "You are unsubscribed from the event!!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("TAG", "Error in unsubscription: " + error.getMessage());
                        }
                    }
                    );
                    AppController.getInstance().addToRequestQueue(participantUnsubscriptionRequest);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } });


        }
    }

