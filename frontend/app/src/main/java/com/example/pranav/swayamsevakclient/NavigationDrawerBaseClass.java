package com.example.pranav.swayamsevakclient;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.pranav.swayamsevakclient.AppConfig.URL_VOLUNTEER_IMAGE;

public class NavigationDrawerBaseClass extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private Bitmap bitmap;
    private String json_query_result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_navigation_drawer_base_class);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // Houses all main content + navigation drawer content

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener()

                {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()

                {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.view_contr:
                                Intent intent1 = new Intent(NavigationDrawerBaseClass.this, ViewContributionsActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.update:
                                Intent intent2 = new Intent(NavigationDrawerBaseClass.this, UpdateParticipantDetailsActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.withdraw:
                                Intent intent3 = new Intent(NavigationDrawerBaseClass.this, ParticipantUnsubscribeActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.passive:
                                Intent intent4 = new Intent(NavigationDrawerBaseClass.this, GoPassiveActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.logout:
                                Intent intent5 = new Intent(NavigationDrawerBaseClass.this, VolunteerLogoutActivity.class);
                                startActivity(intent5);
                                break;
                            default:
                                break;
                        }


                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
                        // close drawer when item is tapped
                        //mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        TextView name = null;
        TextView email = null;
        final ImageView profileImage;
        String personName = "Yoshua Bengio";
        String personEmail = "yosh.beng@gmail.com";
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nameTxt);
        email = (TextView) header.findViewById(R.id.emailTxt);
        profileImage = (ImageView) header.findViewById(R.id.profileImageView);

        // get Volunteer profile picture, name and email id from DB
        get_volunteer_details(profileImage, name, email);
        //name.setText(personName);
        //email.setText(personEmail);
        profileImage.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                // Update volunteer profile picture activity
                Intent intent6 = new Intent(NavigationDrawerBaseClass.this, EditVolunteerProfilePictureActivity.class);

                // convert bitmap image into array
                Bitmap bmp = bitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // send byte array of image to intent
                intent6.putExtra("Profile picture", byteArray);
                startActivity(intent6);

            }
        });


    }


    public void get_volunteer_details(final ImageView profileImage, final TextView name, final TextView email) {


        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest imagerequest = new ImageRequest(URL_VOLUNTEER_IMAGE, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap1) {
                bitmap = bitmap1;
                profileImage.setImageBitmap(bitmap1);
            }
        }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NavigationDrawerBaseClass.this, "Sorry, could not access your profile picture!!", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getParams() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginToken", sessionManager.getLoginToken());
                params.put("participantID", sessionManager.getParticipantID());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        // Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(imagerequest);

        // DB query to fetch specific event details
        // Creates JSON object Post request
        StringRequest participantDetailsRequest = new StringRequest(Request.Method.POST, AppConfig.URL_VOLUNTEER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_query_result = response;
                try {
                    JSONObject json_response = new JSONObject(json_query_result);
                    String Vol_name = json_response.getString("name");
                    String Vol_email = json_response.getString("email");
                    name.setText(Vol_name);
                    email.setText(Vol_email);

                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getParams() {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginToken", sessionManager.getLoginToken());
                params.put("participantID", sessionManager.getParticipantID());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(participantDetailsRequest);

    }


}