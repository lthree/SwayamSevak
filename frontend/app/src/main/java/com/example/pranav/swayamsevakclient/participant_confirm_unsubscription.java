package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class participant_confirm_unsubscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_confirm_unsubscription);

    // Shows individual event details, DB query
    // TODO
    // Takes user input through button, on confirm unsubscription
    Button confirm_unsubscription_button = findViewById(R.id.participant_abandon_confirm_button);
    confirm_unsubscription_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Notifies status of participant in DB
            // TODO
            Intent open_successful_unsubsription_message_intent = new Intent(getApplicationContext(), participant_unsubscribe_status.class);
            startActivity(open_successful_unsubsription_message_intent);
        }
    });

    Button revert_unsubscription_button = findViewById(R.id.participant_abandon_revert_button);
    revert_unsubscription_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent revert_unsubscription_intent = new Intent(getApplicationContext(), participant_unsubscribe.class);
            startActivity(revert_unsubscription_intent);
        }
    });


    }
}
