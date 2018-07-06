
package com.example.pranav.swayamsevakclient;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.StateSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.pranav.swayamsevakclient.AppConfig.URL_EVENT_IMAGE;
import static com.example.pranav.swayamsevakclient.AppConfig.URL_VOLUNTEER_IMAGE;

public class VolunteerHomeActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private int PROXIMITY_RADIUS = 5000;
    private String json_query_result;
    private Bitmap bitmap;
    private String Volunteer_name;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    DrawerLayout mDrawerLayout;
    private static String eventID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        // extract Event_id and participant_id
        Bundle extras = getIntent().getExtras();
        eventID = (String) extras.get("event_id");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        final TextView targetTextView = findViewById(R.id.targetID);
        final TextView currentTextView = findViewById(R.id.currentID);
        Resources res = getResources();
        String[] event_static_amount = get_target_and_current_amount_for_an_event();
        String tgtamnt = event_static_amount[0];
        String crtamnt = event_static_amount[1];
        String targettext = String.format(res.getString(R.string.targetex), tgtamnt);
        String currenttext = String.format(res.getString(R.string.currentex), crtamnt);
        targetTextView.setText(targettext);

        currentTextView.setText(currenttext);

        get_nearby_buildings();
        //get_selected_building_location();

        Button btnCheckin = findViewById(R.id.btn_checkin);
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                //mMap.clear();
                // Send selected building location (lat lang) and send it to server for setting status of that building
                get_selected_building_location();
                // send lat lang to the server along with its details
                send_location_to_change_status();
            }
        });

        Button btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                //mMap.clear();
                // send selected building location (lat lang) and send it to server for changing the status of the building
                send_location_to_change_status();
                get_nearby_buildings();
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
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
                                Intent intent5 = new Intent(VolunteerHomeActivity.this, LoginActivity.class);
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
        name = (TextView)header.findViewById(R.id.nameTxt);
        email = (TextView)header.findViewById(R.id.emailTxt);
        profileImage = (ImageView)header.findViewById(R.id.profileImageView);

        // get Volunteer profile picture, name and email id from DB
        get_volunteer_details(profileImage, name, email);
        //name.setText(personName);
        //email.setText(personEmail);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.setBuildingsEnabled(true);
                mMap.setIndoorEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.setIndoorEnabled(true);
        }

        //LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //criteria.setAltitudeRequired(false);
        //criteria.setBearingRequired(false);
        //String bestProvider = locationManager.getBestProvider(criteria, true);
        //Location location = locationManager.getLastKnownLocation(bestProvider);
        //if (location != null) {
        //   onLocationChanged(location);
        //}
        //locationManager.requestLocationUpdates(bestProvider,5,0, (android.location.LocationListener) this);
        //locationManager.requestLocationUpdates(bestProvider, 5, 0,);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        /**LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         Criteria criteria = new Criteria();
         criteria.setAccuracy(Criteria.ACCURACY_FINE);
         criteria.setAltitudeRequired(false);
         criteria.setBearingRequired(false);
         String bestProvider = locationManager.getBestProvider(criteria, true);
         if (ContextCompat.checkSelfPermission(this,
         Manifest.permission.ACCESS_FINE_LOCATION)
         == PackageManager.PERMISSION_GRANTED) {
         Location location = locationManager.getLastKnownLocation(bestProvider);
         if (location != null) {
         onLocationChanged(location);
         }
         locationManager.requestLocationUpdates(bestProvider,5,0, (com.google.android.gms.location.LocationListener) this);
         }*/

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are in this Building");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        Toast.makeText(VolunteerHomeActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f",latitude,longitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   public void get_volunteer_details( final ImageView profileImage, final TextView name, final TextView email) {


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
                       Toast.makeText(VolunteerHomeActivity.this, "Sorry, could not access your profile picture!!", Toast.LENGTH_SHORT).show();
                   }
               }) {

           @Override
           public Map<String, String> getParams() {
               SessionManager sessionManager = new SessionManager(getApplicationContext());
               Map<String, String> params = new HashMap<String, String>();
               params.put("loginToken", sessionManager.getLoginToken());
               params.put("participantID", sessionManager.getParticipantID() );
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
               try{
                   JSONObject json_response = new JSONObject(json_query_result);
                   String Vol_name = json_response.getString("name");
                   String Vol_email = json_response.getString("email");
                   name.setText(Vol_name);
                   email.setText(Vol_email);

               }


               catch(JSONException error){ error.printStackTrace();}
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


   // fetch an event target and current amount from server for user home page
   public String[] get_target_and_current_amount_for_an_event()
   {
        final String[] event_target_current_amnt = new String[2];

       StringRequest participantDetailsRequest = new StringRequest(Request.Method.POST, AppConfig.URL_EVENT_DETAILS, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               json_query_result = response;
               try{

                   JSONObject json_response = new JSONObject(json_query_result);
                   event_target_current_amnt[0] = json_response.getString("target_amount");
                   event_target_current_amnt[1] = json_response.getString("current_amount");

               }


               catch(JSONException error){ error.printStackTrace();}
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
               params.put("eventID", eventID);
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

       return event_target_current_amnt;

   }

   public void get_nearby_buildings() {
       String url = getUrl(latitude, longitude,"regions|restaurant|neighborhood|locality|sublocality");
       Object[] DataTransfer = new Object[2];
       DataTransfer[0] = mMap;
       DataTransfer[1] = url;
       Log.d("onClick", url);
       GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Volunteer_name);
       getNearbyPlacesData.execute(DataTransfer);
       Toast.makeText(VolunteerHomeActivity.this, "Nearby Restaurants & locality", Toast.LENGTH_LONG).show();
   }
   public void get_selected_building_location() {

   }
   public void send_location_to_change_status() {

   }
}

