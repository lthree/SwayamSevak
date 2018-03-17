package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewContributionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contributions);
        // fetch list of contributions from DB
        // TODO
        Button back_from_contributions_button = findViewById(R.id.back_from_contribution_list_button);
        back_from_contributions_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_back_to_contributor_home_intent = new Intent(getApplicationContext(), ParticipantHomePageActivity.class);
                startActivity(go_back_to_contributor_home_intent);
            }
        });
    }
}
