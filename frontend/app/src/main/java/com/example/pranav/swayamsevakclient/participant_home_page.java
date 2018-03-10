package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class participant_home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_home_page);

        Button view_events = (Button) findViewById(R.id.list_events_button);
        view_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent show_available_events_intent = new Intent(getApplicationContext(), list_available_events.class);
                startActivity(show_available_events_intent);
            }
        });

        Button view_contributions = (Button) findViewById(R.id.view_contributions_button);
        view_contributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_contributions_intent = new Intent(getApplicationContext(), view_contributions.class);
                startActivity(view_contributions_intent);
            }
        });

        Button update_participant_details = (Button) findViewById(R.id.update_contributor_info_button);
        update_participant_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update_participant_info_intent = new Intent(getApplicationContext(), update_participant_details.class);
                startActivity(update_participant_info_intent);
            }
        });

        Button unsubscribe_participant = (Button) findViewById(R.id.delete_participation_button);
        unsubscribe_participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent unsubscribe_participant_intent = new Intent(getApplicationContext(), participant_unsubscribe.class);
                startActivity(unsubscribe_participant_intent);
            }
        });
    }
}
