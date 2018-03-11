package com.example.pranav.swayamsevakclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class list_available_events extends AppCompatActivity {

    private String event_details_url="http://thesoulitude.in/MumbaiHackathon/event/read.php";
    private String json_query_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_events);
        access_event_data_and_show();
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){
            SharedPreferences shared_preferences = getApplicationContext().getSharedPreferences("shared_preferences", 0);
            shared_preferences.getString("session_key", null);
            // TODO append session key and user id with HTTP request
            HttpClient get_event_data_client = new DefaultHttpClient();
            HttpPost http_post_request = new HttpPost(params[0]);
            try {
                HttpResponse response = get_event_data_client.execute(http_post_request);
                json_query_result = inputStreamToString(response.getEntity().getContent()).toString();
            }
        catch (ClientProtocolException e){
                e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
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
            Event event = new Event();
            for (int j = 0; j < json_main_node.length(); j++) {
                JSONObject json_child_node = json_main_node.getJSONObject(j);
                event.set_event_title(json_child_node.optString("title"));
                event.set_event_id(json_child_node.optInt("id"));
                event_data_list.add(event);
            }
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        // bind it to the adapter
    /*    ArrayAdapter<Event> event_list_adapter = new ArrayAdapter<Event>(this, event_data_list, R.id.event_item) ;
        ListView event_list_view = findViewById(R.id.list_events_list_view);
        event_list_view.setAdapter(event_list_adapter);
        event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = event_data_list.get(i);
                Intent open_event_details_intent = new Intent(getApplicationContext(), show_event_details.class);
                open_event_details_intent.putExtra("event_id", event.get_event_id());
                startActivity(open_event_details_intent);
            }
        });
    */}

}
