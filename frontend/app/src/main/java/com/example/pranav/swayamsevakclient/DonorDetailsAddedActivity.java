package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonorDetailsAddedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details_added);

        // Volunteer wishes to continue donation collection
        Button addMoreInThisLocalityButton = findViewById(R.id.add_more_in_this_locality_button);
        addMoreInThisLocalityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DonateActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Volunteer wishes to work in another location
        Button leaveThisLocality = findViewById(R.id.leave_this_locality_button);
        leaveThisLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), VolunteerHomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Volunteer wishes to check current fund status
        Button checkCurrentFundsButton = findViewById(R.id.current_funds_button);
        checkCurrentFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes DB query to get current funds

                final JSONObject currentFundsObject = new JSONObject();
                final JsonObjectRequest getCurrentFundsJSONObject = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_ADD_DONOR_DETAILS, currentFundsObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView showCurrentFundsTextView = findViewById(R.id.current_funds_text_view);
                        showCurrentFundsTextView.setText(response.toString());
                    }
                }, new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("TAG", "Error: " + error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getParams() {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("loginToken", sessionManager.getLoginToken());
                        params.put("participantId", sessionManager.getParticipantID());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }


                };

                AppController.getInstance().addToRequestQueue(getCurrentFundsJSONObject); // adds request to the queue
            }
        });
    }
}
