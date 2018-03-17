package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateParticipantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_participant_details);

        // get participant information from edittext
        // TODO
        // send it to DB
        // TODO

        Button update_participant_details_button = findViewById(R.id.submit_participant_details_changes_button);
        update_participant_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DB message for updating participant information
                // TODO
                // invoke success screen
                Intent open_participant_details_updated_screen_intent = new Intent(getApplicationContext(), ParticipantDetailsUpdatedActivity.class);
                startActivity(open_participant_details_updated_screen_intent);
            }
        });
    }
}
