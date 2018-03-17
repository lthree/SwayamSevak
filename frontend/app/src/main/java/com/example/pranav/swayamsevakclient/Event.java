package com.example.pranav.swayamsevakclient;

/**
 * Created by pranav on 10/3/18.
 * Models the concept of events available for volunteering.
 */
public class Event {
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private int mobileNumber;
    private String venue;
    private String eventDetails;
    private byte[] photo;
    private int adminId;
    private float locationLat;
    private float locationLongitude;
    private int eventType;


    /** Set method for event title
     */
    public void setEventTitle(String input_event_tile) {
        title = input_event_tile;
    }

    /** Set method for event details
     */
    public void setEventDetails(String input_event_details){
        eventDetails = input_event_details;
    }
    /** Set method for event image
     */
    public void setEventImage(byte[] input_event_image) {
        photo = input_event_image;
    }

    /** Set method for event id
     */
    public void setEventId(int input_event_id) {
        id = input_event_id;
    }

    /** Get method for event title
     */
    public String getEventTitle() {
        return title;
    }

    /** Get method for event details
     */
    public String getEventDetails(){
        return eventDetails;
    }

    /** Get method for event image
     */
    public byte[] getEventImage() {
        return photo;
    }

    /** Get method for event id
     */
    public int getEventId() {
        return id;
    }

}
