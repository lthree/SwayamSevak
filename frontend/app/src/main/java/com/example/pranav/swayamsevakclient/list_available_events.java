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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class list_available_events extends AppCompatActivity {

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
  /*          SessionManager sessionManager = new SessionManager(getApplicationContext());
            String loginToken = sessionManager.getLoginToken();
            // TODO append loginToken with HTTP request
            HttpClient get_event_data_client = new DefaultHttpClient();
            HttpPost http_post_request = new HttpPost(params[0]);
            //http_post_request.setHeader("loginToken", loginToken);
    //        http_post_request.setHeader("Content-Type", "application/x-www-form-urlencoded");

            Map<String, String> stringWithStringPostBody = new HashMap<String, String>();

            JSONObject postBody = new JSONObject();
                stringWithStringPostBody.put("loginToken", loginToken);
                postBody = new JSONObject(stringWithStringPostBody);

                try {

                    http_post_request.setEntity(new StringEntity(postBody.toString(), "UTF8"));

                }
                catch (Exception e){
                    e.printStackTrace();
                }



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
    */
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

            for (int j = 0; j < json_main_node.length(); j++) {
                Event event = new Event();
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
        mEventAdapter = new EventAdapter(this, event_data_list);
        ListView event_list_view = findViewById(R.id.list_events_list_view);
        event_list_view.setAdapter(mEventAdapter);
        event_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = event_data_list.get(i);
                Intent open_event_details_intent = new Intent(getApplicationContext(), show_event_details.class);
                open_event_details_intent.putExtra("event_id", event.get_event_id());
                startActivity(open_event_details_intent);
            }
        });
    }

}
