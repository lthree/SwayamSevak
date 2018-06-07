package com.example.pranav.swayamsevakclient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class UpdateParticipantDetailsActivity extends AppCompatActivity {

    String mParticipantName, mParticipantAddress, mParticipantEmailAddress, mParticipantContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_participant_details);

        // extract information from participant information form
        EditText participantNameEditText = (EditText) findViewById(R.id.participantNameEditText);
        mParticipantName = participantNameEditText.getText().toString();
        EditText participantAddressEditText = (EditText) findViewById(R.id.participantAddressEditText);
        mParticipantAddress = participantAddressEditText.getText().toString();
        EditText participantEmailAddressEditText = (EditText) findViewById(R.id.participantEmailAddressEditText);
        mParticipantEmailAddress = participantEmailAddressEditText.getText().toString();

        EditText participantContactNumberEditText = (EditText) findViewById(R.id.participantContactNumberEditText);
        mParticipantContactNumber = participantContactNumberEditText.getText().toString();

        // send that information to DB on Submit click button
        Button submit_participant_info_button = (Button) findViewById(R.id.submit_participant_details_button);
        submit_participant_info_button.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {
                                                                  // send that information to DB
                                                                  JSONObject updateParticipantDetails = new JSONObject();
                                                                  try {
                                                                      updateParticipantDetails.put("name", mParticipantName);
                                                                      updateParticipantDetails.put("address", mParticipantAddress);
                                                                      updateParticipantDetails.put("emailid", mParticipantEmailAddress);
                                                                      updateParticipantDetails.put("contactnumber", mParticipantContactNumber);
                                                                      JsonObjectRequest sendParticipantUpdatedDataRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_UPDATE_PARTICIPANT_DETAILS, updateParticipantDetails, new Response.Listener<JSONObject>() {
                                                                          @Override
                                                                          public void onResponse(JSONObject response) {
                                                                              Log.d("TAG", response.toString());
                                                                              Toast.makeText(UpdateParticipantDetailsActivity.this, "Details successfully updated!!", Toast.LENGTH_SHORT).show();

                                                                              // invoke status activity
                                                                              Toast.makeText(UpdateParticipantDetailsActivity.this, "Details updated successfully !!", Toast.LENGTH_SHORT).show();

                                                                          }
                                                                      }, new Response.ErrorListener() {
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
                                                                              params.put("participantID", sessionManager.getParticipantID());
                                                                              return params;
                                                                          }

                                                                          @Override
                                                                          public Map<String, String> getHeaders() throws AuthFailureError {
                                                                              Map<String, String> params = new HashMap<String, String>();
                                                                              params.put("Content-Type", "application/x-www-form-urlencoded");
                                                                              return params;
                                                                          }
                                                                      };
                                                                      ;


                                                                      AppController.getInstance().addToRequestQueue(sendParticipantUpdatedDataRequest); // adds the request to the queue
                                                                  } catch (JSONException e) {
                                                                      e.printStackTrace();
                                                                  }

                                                              }
                                                          }

        );

    }}


