package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowEventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);

        // extract event id
        Bundle extras = getIntent().getExtras();
        int event_id = (Integer) extras.get("event_id");

        // DB query to fetch specific event details
        // TODO
        // invoke donate activity if donate button pressed

        ImageView eventImageView = findViewById(R.id.event_image_view);
       // eventImageView.setImageResource(R.drawable.volunteering); // TODO get it from DB and store it in RAM
       // eventImageView.setVisibility(View.VISIBLE);

        // TODO initialize from event details
        String eventDetailsText = new String(); // TODO initialize from DB

        TextView eventDescriptionTextView  = findViewById(R.id.event_description_text_view);
        eventDescriptionTextView.setText(eventDetailsText);

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
}
