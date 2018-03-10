package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class volunteer_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        // Show total fund
        // Show current fund
        // Show maps with marker location
            // query markers for nearby locations
            // label buildings with those markers
        // Show suggestion for next location
        // Extract location selected by volunteer
        // send location to DB and

        // Go button
        Button volunteer_go_button = findViewById(R.id.volunteer_go_button);
        volunteer_go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_add_donor_intent = new Intent(getApplicationContext(), volunteer_adds_donor.class);
                startActivity(open_add_donor_intent);
            }
        });


        // Logout button
        Button volunteer_logout_button = findViewById(R.id.volunteer_logout_button);
        volunteer_logout_button.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           Intent volunteer_logout_intent = new Intent(getApplicationContext(), volunteer_logout.class);
                                                           startActivity(volunteer_logout_intent);
                                                       }
                                                   });


    }
}
