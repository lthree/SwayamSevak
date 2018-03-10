package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent open_contributor_home_page_intent = new Intent(this, contributor_home_page.class);
        startActivity(open_contributor_home_page_intent);
    }
}
