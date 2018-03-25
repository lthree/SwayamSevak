package com.example.pranav.swayamsevakclient;

/**
 * Created by lenovo1 on 07-03-2018.
 */
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController extends Application {

        public static final String TAG = AppController.class.getSimpleName();

        private RequestQueue mRequestQueue;

        private static AppController mInstance;

        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;
        }

        public static synchronized AppController getInstance() {
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req, String tag) {
            req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().add(req);
        }

        public <T> void addToRequestQueue(Request<T> req) {
            req.setTag(TAG);
            getRequestQueue().add(req);
        }

        public void cancelPendingRequests(Object tag) {
            if (mRequestQueue != null) {
                mRequestQueue.cancelAll(tag);
            }
        }

    }

