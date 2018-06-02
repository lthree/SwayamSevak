package com.example.pranav.swayamsevakclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Safia on 2/6/18.
 */

public class ViewContrAdapter extends ArrayAdapter<String>
{
    private Activity Context;
    private String[] maintitle;
    private Integer[] subtitle;


    public ViewContrAdapter(Activity context, String[] maintitle, Integer[] subtitle) {
        super(context,R.layout.activity_view_contr_row, maintitle);
        this.Context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
    }
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = Context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_view_contr_row, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    }


}
