package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class show_event_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);

        // DB query to fetch specific event details
        // TODO
        // invoke donate activity if donate button pressed
        Button donate_button = (Button) findViewById(R.id.donate_button);
        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_donate_page_intent = new Intent(getApplicationContext(), donate_activity.class);
                startActivity(open_donate_page_intent);
            }
        });
        // invoke volunteer activity if volunteer button pressed
        Button volunteer_button = (Button) findViewById(R.id.volunteer_button);
        volunteer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_volunteer_page_intent = new Intent(getApplicationContext(), volunteer_home.class);
                startActivity(open_volunteer_page_intent);
            }
        });
    }
}
