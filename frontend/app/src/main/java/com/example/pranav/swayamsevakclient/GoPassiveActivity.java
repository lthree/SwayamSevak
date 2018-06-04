package com.example.pranav.swayamsevakclient;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Safia on 04-06-2018.
 */

public class GoPassiveActivity extends AppCompatActivity {

    private String json_query_result;


    public void onRadioButtonClicked(View view) {

        // check if the button is checked
        boolean checked = ((RadioButton) view).isChecked();

        // check which radio button was clicked
        switch (view.getId()) {

            case R.id.start_volunteering:
                start_volunteering();
                break;
            case R.id.stop_volunteering:
                stop_volunteering();
                break;
        }
    }


    public void stop_volunteering() {
        // set is_active flag on server to False stating that the participant is no longer volunteering and is just navigating through the app
        // TODO can use it to see number of Active Volunteers

        final String PartID = "1234567";   // TODO Get participant ID first
        // create JSON object POST request
        StringRequest participantEventListRequest = new StringRequest(Request.Method.POST, AppConfig.URL_CHECK_PARTCIPANT_INTO_PASSIVE_MODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;

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

    public void onStartVolunteeringRadioButtonClicked(View view) {

    }

    public void start_volunteering() {

    }
}



