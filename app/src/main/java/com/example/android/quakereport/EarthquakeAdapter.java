package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by aji on 06/07/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /*
    Constructor
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> word){
        super(context, 0, word);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_items, parent, false);
        }

        Earthquake nameWord = getItem(position);

        TextView magnetido = (TextView) listItemView.findViewById(R.id.mag);

        magnetido.setText(nameWord.getMag());

        TextView location = (TextView) listItemView.findViewById(R.id.location);

        location.setText(nameWord.getCityName());

        TextView date = (TextView) listItemView.findViewById(R.id.date);

        date.setText(nameWord.getDate());

        return listItemView;
    }

}
