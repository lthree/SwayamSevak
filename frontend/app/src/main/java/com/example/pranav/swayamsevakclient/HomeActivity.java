package com.example1.loginregapp;

/**
 * Created by lenovo1 on 08-03-2018.
 */

//Fetch User detail from php server

//import com.example1.loginregapp.SQLiteHandler;
import com.example1.loginregapp.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    //private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn() & session.getLoginToken()!=null ) {
            logoutUser();
        }

        Bundle extras = getIntent().getExtras();
        String name="",email="";
        if (extras!=null) {
            name = extras.getString("name");
            email = extras.getString("email");
        }
        // Displaying the user details on the screen

        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);
        session.setLogintoken(null);

        //db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
