package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

        /*
        get item position
         */
        Earthquake earthquake = getItem(position);

        /*
        Magnetudo TextView and SetText
         */
        TextView magnetido = (TextView) listItemView.findViewById(R.id.mag);

        magnetido.setText(earthquake.getMag());

        /*
        get location name
         */
        String locationName = earthquake.getCityName();

        /*
        Split If Contain " of " else add " near the "
         */
        if(locationName.contains("of")){

            String[] parts = locationName.split("(?<=of)");

            String xLocation = parts[0];
            String yPositon = parts[1];

            TextView location = (TextView) listItemView.findViewById(R.id.location);

            location.setText(yPositon);

            TextView cityName = (TextView) listItemView.findViewById(R.id.position);

            cityName.setText(xLocation);

        }else {

            TextView location = (TextView) listItemView.findViewById(R.id.location);

            location.setText(locationName);

            TextView cityName = (TextView) listItemView.findViewById(R.id.position);

            cityName.setText("Near the");
        }

        /*
        get Date Time in millisecond and declarate as dateObject
         */
        Date dateObject = new Date(earthquake.getTimeMillisecond());

        /*
        Date View
         */
        TextView date = (TextView) listItemView.findViewById(R.id.date);

        String formattedDate = formatDate(dateObject);

        date.setText(formattedDate);

        /*
        Time View
         */
        TextView time = (TextView) listItemView.findViewById(R.id.time);

        String formattedTime = formatTime(dateObject);

        time.setText(formattedTime);

        return listItemView;
    }

    /**
     * Kembalikan string tanggal terformat ("Mar 3, 1984") dari objek Date.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Kembalikan string tanggal terformat ( "4:30 PM") dari objek Date.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
