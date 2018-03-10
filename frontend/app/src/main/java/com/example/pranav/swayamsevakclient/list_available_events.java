package com.example.pranav.swayamsevakclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class list_available_events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_events);
        // fetch list of events from DB
        // set on click listener for each item to fetch individual event information

    }
}
