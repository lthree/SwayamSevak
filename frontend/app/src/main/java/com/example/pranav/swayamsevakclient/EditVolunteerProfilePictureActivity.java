package com.example.pranav.swayamsevakclient;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.security.PublicKey;

/**
 * Created by Safia on 5/6/18.
 */

public class EditVolunteerProfilePictureActivity extends AppCompatActivity {

    Context mContext;
    static final int REQUEST_CAMERA=1;
    static final int SELECT_FILE=2;
    public EditVolunteerProfilePictureActivity(Context context){mContext = context;}
    String[] dialogArray = mContext.getResources().getStringArray(R.array.update_photo);
    final CharSequence[] items = {"Take photo", "Choose from Gallery", "Cancel"};
    String userChoosenTask;
    ImageView profileImage = (ImageView) findViewById(R.id.imageView1);

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile_picture_edit);

        // extract profile image from intent
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("Profile picture");

        // convert bytearray to bitmap and set into Imageview
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditVolunteerProfilePictureActivity.this);
                builder.setIcon(R.drawable.ic_launcher_foreground)
                        .setTitle("Profile picture")
                        .setItems(dialogArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                boolean result = checkPermission(EditVolunteerProfilePictureActivity.this);
                                if(dialogArray[item].equals("Camera")) {
                                    userChoosenTask = "Take photo";
                                    if(result) {cameraIntent();}}
                                else if(dialogArray[item].equals("Gallery")) { userChoosenTask = "Choose from Gallery"; if(result) {galleryIntent();}}
                                else if(dialogArray[item].equals("Cancel")) { dialog.dismiss();}


                            }
                        });

                // create alert dialog object
                AlertDialog dialog = builder.create();


                // show alertDialog
                dialog.show();

            }
        });

    }

    // checks the permission for storage group at runtime
    public static boolean checkPermission(final Context context) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] Permissions, int[] grantResults) {
        switch (requestCode){
            case EditVolunteerProfilePictureActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Gallery"))
                        galleryIntent();
                    else {}
                    break;
                }
        }

    }

    // method for starting camera Intent
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }
    // method for starting gallery intent
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null){
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            }
            catch (IOException e ){
                e.printStackTrace();
            }
        }
        profileImage.setImageBitmap(bm);

    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch(IOException e){  e.printStackTrace();   }

        profileImage.setImageBitmap(thumbnail);

    }

}
