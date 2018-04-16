package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Map;

public class ShowEventDetailsActivity extends AppCompatActivity {

    private String json_query_result;
    final ArrayList<Event> event_data_list = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);

        // extract event id
        Bundle extras = getIntent().getExtras();
        final int event_id = (Integer) extras.get("event_id");


        // DB query to fetch specific event details
        // Creates JSON object Post request
        StringRequest eventDetailsRequest = new StringRequest(Request.Method.POST, AppConfig.URL_EVENT_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;
                display_event_details();
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
                params.put("idEvent", Integer.toString(event_id));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(eventDetailsRequest);

        // Fetch event image TODO
        /* ImageView mImageView;
        String url = "http://i.imgur.com/7spzG.png";
        mImageView = (ImageView) findViewById(R.id.myImage);
...

// Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);
    */
    }


    public void display_event_details(){
        // extract information from json result

        try {
            JSONObject json_response = new JSONObject(json_query_result);
            JSONArray json_main_node = json_response.optJSONArray("eventlist");

            // extract event and initialize Event array list

            for (int j = 0; j < json_main_node.length(); j++) {
                Event event = new Event();
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                event.setEventTitle(json_child_node.optString("title"));
                event.setEventDetails(json_child_node.optString("eventDetails"));
                event_data_list.add(event);
            }
            // TODO initialize from event details
            String eventDetailsText = new String(); // TODO initialize from DB

            TextView eventDescriptionTextView  = findViewById(R.id.event_description_text_view);
            eventDescriptionTextView.setText(event_data_list.get(0).getEventDetails());


            // invoke donate activity if donate button pressed

            ImageView eventImageView = findViewById(R.id.event_image_view);
            // eventImageView.setImageResource(R.drawable.volunteering); // TODO get it from DB and store it in RAM
            // eventImageView.setVisibility(View.VISIBLE);


            // Handle buttons
            Button donate_button = (Button) findViewById(R.id.donate_button);
            donate_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent open_donate_page_intent = new Intent(getApplicationContext(), DonateActivity.class);
                    startActivity(open_donate_page_intent);
                }
            });
            // invoke volunteer activity if volunteer button pressed
            Button volunteer_button = (Button) findViewById(R.id.volunteer_button);
            volunteer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent open_volunteer_page_intent = new Intent(getApplicationContext(), VolunteerHomeActivity.class);
                    startActivity(open_volunteer_page_intent);
                }
            });



        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }


}

