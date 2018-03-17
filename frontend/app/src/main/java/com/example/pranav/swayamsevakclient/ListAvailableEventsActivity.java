package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListAvailableEventsActivity extends AppCompatActivity {

    private String event_details_url="http://thesoulitude.in/MumbaiHackathon/event/read.php";
    private String json_query_result;
    private EventAdapter mEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_events);
        access_event_data_and_show();
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "loginToken=2018-03-12%2018%3A23%3A1178");
            Request request = new Request.Builder()
                    .url("http://thesoulitude.in/MumbaiHackathon/event/read.php")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "81e8ce5b-2670-839e-c8df-6a455d30781b")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                json_query_result = response.body().string();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result){
            display_event_list();
        }
    }


    // generates DB request to access event data, once received it renders the content.
    public void access_event_data_and_show(){
        JsonReadTask task = new JsonReadTask();
        task.execute(new String[] {event_details_url});

    }

    public void display_event_list(){
        // extract information from json result
        final ArrayList<Event> event_data_list = new ArrayList<Event>();
        try {
            JSONObject json_response = new JSONObject(json_query_result);
            JSONArray json_main_node = json_response.optJSONArray("eventlist");

            // extract event and initialize Event array list

            for (int j = 0; j < json_main_node.length(); j++) {
                Event event = new Event();
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                event.setEventTitle(json_child_node.optString("title"));
                event.setEventId(json_child_node.optInt("id"));
                event_data_list.add(event);
            }
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        // bind it to the adapter
        mEventAdapter = new EventAdapter(this, event_data_list);
        ListView event_list_view = findViewById(R.id.list_events_list_view);
        event_list_view.setAdapter(mEventAdapter);
        event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = event_data_list.get(i);
                Intent open_event_details_intent = new Intent(getApplicationContext(), ShowEventDetailsActivity.class);
                open_event_details_intent.putExtra("event_id", event.getEventId());
                startActivity(open_event_details_intent);
            }
        });
    }

}
