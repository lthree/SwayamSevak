package com.example.pranav.swayamsevakclient;

/**
 * Created by pranav on 10/3/18.
 */

public class Event {
    private int id;
    private String title;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private int mobile_number;
    private String venue;
    private String event_details;
    private byte[] photo;
    private int admin_id;
    private float location_lat;
    private float loction_longitude;
    private int event_type;

    /*public Event(String event_title){
        title = event_title;
    }*/

    public void set_event_title(String input_event_tile) {
        title = input_event_tile;
    }

    public void set_event_details(String input_event_details){
        event_details = input_event_details;
    }

    public void set_event_image(byte[] input_event_image) {
        photo = input_event_image;
    }

    public String get_event_title() {
        return title;
    }

    public String get_event_details(){
        return  event_details;
    }

    public byte[] set_event_image() {
        return photo;
    }

    public int get_event_id() {
        return id;
    }

    public void set_event_id(int input_event_id) {
        id = input_event_id;
    }

}
