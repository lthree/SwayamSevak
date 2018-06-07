package com.example.pranav.swayamsevakclient;

/**
 * Created by lenovo1 on 08-03-2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class  SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String LOGIN_TOKEN = "loginToken";

    private static final String PARTICIPANT_ID = "participantID";

    private static final String EVENT_ID = "eventID";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setLogintoken(String loginToken) {

        editor.putString(LOGIN_TOKEN, loginToken);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setParticipantID(String participantID) {
        editor.putString(PARTICIPANT_ID, participantID);
        editor.commit();
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public String getLoginToken(){ return pref.getString(LOGIN_TOKEN, null);}
    public String getParticipantID() { return pref.getString(PARTICIPANT_ID, null);}

}

