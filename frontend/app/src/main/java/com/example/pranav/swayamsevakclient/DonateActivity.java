package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DonateActivity extends AppCompatActivity {
    String mDonorName, mDonorAddress, mDonorEmailAddress, mDonorStar, mDonorRashi, mDonorContactNumber, mDonorDonationAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_activity);

        // extract information from donor information form
        EditText donorNameEditText = (EditText) findViewById(R.id.donorNameEditText);
        mDonorName = donorNameEditText.getText().toString();
        EditText donorAddressEditText = (EditText) findViewById(R.id.donorAddressEditText);
        mDonorAddress = donorAddressEditText.getText().toString();
        EditText donorEmailAddressEditText = (EditText) findViewById(R.id.donorEmailAddressEditText);
        mDonorEmailAddress = donorEmailAddressEditText.getText().toString();
        EditText donorStarEditText = (EditText) findViewById(R.id.donorStarEditText);
        mDonorStar = donorStarEditText.getText().toString();
        EditText donorRashiEditText = (EditText) findViewById(R.id.donorRashiEditText);
        mDonorRashi = donorRashiEditText.getText().toString();
        EditText donorContactNumberEditText = (EditText) findViewById(R.id.donorContactNumberEditText);
        mDonorContactNumber = donorContactNumberEditText.getText().toString();
        EditText donorDonationAmountEditText = (EditText) findViewById(R.id.donorDonationAmountEditText);
        mDonorDonationAmount = donorDonationAmountEditText.getText().toString();

        // send that information to DB on Submit click button
        Button submit_donor_info_button = (Button) findViewById(R.id.submit_donor_details_button);
        submit_donor_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send that information to DB
                JSONObject donorDetails = new JSONObject();
                try{
                    donorDetails.put("name", mDonorName);
                    donorDetails.put("address", mDonorAddress);
                    donorDetails.put("emailid", mDonorEmailAddress);
                    donorDetails.put("rashi", mDonorRashi);
                    donorDetails.put("star", mDonorStar);
                    donorDetails.put("contactnumber", mDonorContactNumber);
                    donorDetails.put("amount", mDonorDonationAmount);
                    JsonObjectRequest sendDonorDataRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_ADD_DONOR_DETAILS, donorDetails, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            Toast.makeText(DonateActivity.this, "Details successfully submitted!!", Toast.LENGTH_SHORT).show();

                            // invoke status activity
                            Intent submit_donor_info_intent = new Intent(getApplicationContext(), DonorDetailsAddedActivity.class);
                            startActivity(submit_donor_info_intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    });
                    AppController.getInstance().addToRequestQueue(sendDonorDataRequest); // adds the request to the queue
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }

        );

        }
    }
