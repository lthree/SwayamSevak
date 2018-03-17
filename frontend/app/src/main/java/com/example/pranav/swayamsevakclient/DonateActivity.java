package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DonateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_activity);

        // extract information from donor information form

        // send that information to DB on Submit click button
        Button submit_donor_info_button = (Button) findViewById(R.id.submit_donor_details_button);
        submit_donor_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send that information to DB
                // TODO
                // invoke status activity
                Intent submit_donor_info_intent = new Intent(getApplicationContext(), DonorDetailsAddedActivity.class);
                startActivity(submit_donor_info_intent);
            }
        });
        }
    }
