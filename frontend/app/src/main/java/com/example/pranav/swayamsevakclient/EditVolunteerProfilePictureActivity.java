package com.example.pranav.swayamsevakclient;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Safia on 5/6/18.
 */

public class EditVolunteerProfilePictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile_picture_edit);

        // extract profile image from intent
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("Profile picture");

        // convert bytearray to bitmap and set into Imageview
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView profileImage = (ImageView) findViewById(R.id.imageView1);

        profileImage.setImageBitmap(bmp);

        // Fit image to screen with floating action button
        profileImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        profileImage.setScaleType(ImageView.ScaleType.FIT_XY);

        // Add floating action button to edit image
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add an alert dialog to select image from gallery or from camera
                AlertDialog alertDialog = new AlertDialog.Builder(EditVolunteerProfilePictureActivity.this).create();
                alertDialog.setIcon(R.drawable.ic_launcher_foreground);
                alertDialog.setTitle("Profile picture");

                // Add clickable list of items on alert dialog
                //alertDialog.setView(R.layout.activity_picture_edit_options);

                // show alertDialog
                alertDialog.show();

            }
        });

    }
}
