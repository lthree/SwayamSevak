package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Safia on 28/5/18.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    String googlePlacesData;
    GoogleMap mMap;
    String url;
    final String Vol_name;

    public GetNearbyPlacesData(String volunteer_name)
    {
        Vol_name = volunteer_name;
    }
    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        List<HashMap<LatLng, String>> markerStatus = new ArrayList<>();

        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            //Log.d("onPostExecute", "Entered into showing locations");
            //MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            //String placeName = googlePlace.get("place_name");
            //String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            HashMap<LatLng, String> data = new HashMap<LatLng, String>();
            data.put(latLng,"");
            markerStatus.add(data);
        }

        List<HashMap<LatLng, String>> markerStatusUpdated = getMarkerStatus(markerStatus);

        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            HashMap<LatLng, String> getStatus = markerStatusUpdated.get(i);
            String status = null;
            if (getStatus.containsKey(latLng)) {
                status = getStatus.get(latLng);
            }
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.snippet(status);
            //mMap.addMarker(markerOptions);
            if (status.equals(new String("Not-visited"))) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } else if (status.equals(new String("Visited"))) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            } else if (status.equals(new String("In-process"))) {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }

            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String building_name = marker.getTitle();
                    String vol_name = Vol_name;
                    String toast_msg = "Volunteer " + vol_name + "has just Checked in to " +building_name+" building";
                    Toast.makeText(GetNearbyPlacesData.this, toast_msg, Toast.LENGTH_SHORT).show();
                    Intent Donate_intent = new Intent(GetNearbyPlacesData.this, DonateActivity.this);
                    startActivity(Donate_intent);
                    return false;
                }
            });
        }
    }

    private List<HashMap<LatLng, String>> getMarkerStatus(List<HashMap<LatLng, String>> markerStatus){
        // connect with server to get corresponding status of the given Latlng

        for (int i=0; i<markerStatus.size();i++) {
            HashMap<LatLng, String> locstatus = markerStatus.get(i);
            Set<LatLng> keys = locstatus.keySet();
            for (LatLng eachkey : keys) {
                locstatus.put(eachkey, "Visited" );
            }
        }

        return markerStatus;
    }
}
