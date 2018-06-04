package com.example.pranav.swayamsevakclient;

/**
 * Created by lenovo1 on 07-03-2018.
 */

public class AppConfig {
    // Server user login url
    //public static String URL_LOGIN = "http://10.0.6.157/android_login_api/login.php";
    public static String URL_LOGIN = "http://www.thesoulitude.in/MumbaiHackathon/volunteer/login.php";

    // Server user register url
    //public static String URL_REGISTER = "http://10.0.6.157/android_login_api/register.php";
    public static String URL_REGISTER ="http://www.thesoulitude.in/MumbaiHackathon/volunteer/register.php";

    // Server event list url
    public static String URL_LIST_EVENTS= "http://thesoulitude.in/MumbaiHackathon/event/readAllEvents.php";


    // Server participant-specific event list url
    public static String URL_LIST_PARTICIPANT_EVENTS= "http://thesoulitude.in/MumbaiHackathon/event/readAllEvents.php";

    // Server event details url
    public static String URL_EVENT_DETAILS = "http://thesoulitude.in/MumbaiHackathon/event/readEventById.php";

    // Server donor details add url
    public static String URL_ADD_DONOR_DETAILS = "http://thesoulitude.in/MumbaiHackathon/event/readEventById.php";

    // Server participant details update url
    public static String URL_UPDATE_PARTICIPANT_DETAILS = "http://thesoulitude.in/MumbaiHackathon/event/readEventById.php";

    // Server participant unsubscribe url
    public static String URL_UNSUBSCRIBE_PARTICIPANT_FROM_EVENT = "http://thesoulitude.in/MumbaiHackathon/event/readEventById.php";

    // Server participant view contribution url
    public static String URL_VIEW_PARTCIPANT_EVENT_AND_CONTRIBUTIONS = "http://thesoulitude.in/MumbaiHackathon/event/readEvent_ContrById.php";

    // Check participant into Go Passive mode
    public static String URL_CHECK_PARTCIPANT_INTO_PASSIVE_MODE = "http://thesoulitude.in/MumbaiHackathon/event/participant_passive.php";
}
