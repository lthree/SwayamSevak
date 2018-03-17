package com.example.pranav.swayamsevakclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pranav on 12/3/18.
 */

public class EventAdapter extends ArrayAdapter<Event>{

    private Context mContext;
    private ArrayList<Event> meventList;

    public EventAdapter(Context context, ArrayList<Event> event_list){
        super(context, 0, event_list);
        mContext = context;
        meventList = event_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.event_item, parent, false);
        }

        Event currentEvent = meventList.get(position);
        TextView event_title_textview = listItem.findViewById(R.id.event_title_item);
        event_title_textview.setText(currentEvent.getEventTitle());

        return listItem;
    }
}
