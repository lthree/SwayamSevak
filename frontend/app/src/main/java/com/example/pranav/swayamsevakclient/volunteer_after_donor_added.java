package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class volunteer_after_donor_added extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_after_donor_added);

    // Shows current funds, accesses DB,
        // TODO
    TextView current_funds_textview = (TextView) findViewById(R.id.show_current_funds_text_view);
    // fetch from DB
    // show in textview

    // Asks add more in this locality
    Button ask_add_more_in_this_locality_button = findViewById(R.id.add_more_in_same_locality_button);
    ask_add_more_in_this_locality_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_add_donor_intent = new Intent(getApplicationContext(), volunteer_adds_donor.class);
            startActivity(open_add_donor_intent);
        }
    });

    // Leave
    Button volunteer_leaves_locality_button = findViewById(R.id.leave_button);
    volunteer_leaves_locality_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_volunteer_home_intent = new Intent(getApplicationContext(), volunteer_home.class);
            startActivity(open_volunteer_home_intent);
        }
    });
    }
}
