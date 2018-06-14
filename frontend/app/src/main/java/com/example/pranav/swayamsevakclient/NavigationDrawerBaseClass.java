package com.example.pranav.swayamsevakclient;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.ByteArrayOutputStream;

public class NavigationDrawerBaseClass extends Activity {

    DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

    mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener()

    {
        @Override
        public void onDrawerSlide (View drawerView,float slideOffset){
        // Respond when the drawer's position changes
    }

        @Override
        public void onDrawerOpened (View drawerView){
        // Respond when the drawer is opened
    }

        @Override
        public void onDrawerClosed (View drawerView){
        // Respond when the drawer is closed
    }

        @Override
        public void onDrawerStateChanged ( int newState){
        // Respond when the drawer motion state changes
    }
    }
        );
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()

    {
        @Override
        public boolean onNavigationItemSelected (MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.view_contr:
                Intent intent1 = new Intent(VolunteerHomeActivity.this, ViewContributionsActivity.class);
                startActivity(intent1);
                break;
            case R.id.update:
                Intent intent2 = new Intent(VolunteerHomeActivity.this, UpdateParticipantDetailsActivity.class);
                startActivity(intent2);
                break;
            case R.id.withdraw:
                Intent intent3 = new Intent(VolunteerHomeActivity.this, ParticipantUnsubscribeActivity.class);
                startActivity(intent3);
                break;
            case R.id.passive:
                Intent intent4 = new Intent(VolunteerHomeActivity.this, GoPassiveActivity.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                Intent intent5 = new Intent(VolunteerHomeActivity.this, VolunteerLogoutActivity.class);
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
    name=(TextView)header.findViewById(R.id.nameTxt);
    email=(TextView)header.findViewById(R.id.emailTxt);
    profileImage=(ImageView)header.findViewById(R.id.profileImageView);

    // get Volunteer profile picture, name and email id from DB
    get_volunteer_details(profileImage, name, email);
    //name.setText(personName);
    //email.setText(personEmail);
        profileImage.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

        // Update volunteer profile picture activity
        Intent intent6 = new Intent(VolunteerHomeActivity.this, EditVolunteerProfilePictureActivity.class);

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