package com.example.android.quakereport;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

    @TargetApi(Build.VERSION_CODES.N)
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
        Decimal number formatting
         */
        DecimalFormat formatter = new DecimalFormat("0.0");
        String vMagnetudo = formatter.format(earthquake.getMag());

        /*
        Magnetudo TextView and SetText
         */
        TextView magnetido = (TextView) listItemView.findViewById(R.id.mag);

        magnetido.setText(vMagnetudo);

        // Atur warna latar belakang di lingkaran magnitudo.
        // Ambil latar belakang dari TextView, yang berupa GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnetido.getBackground();

        // Ambil warna latar belakang sesuai magnitudo saat itu
        int magnitudeColor = getMagnitudeColor(earthquake.getMag());

        // Atur warna lingkaran magnitudo
        magnitudeCircle.setColor(magnitudeColor);

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

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
