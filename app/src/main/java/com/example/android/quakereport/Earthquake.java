package com.example.android.quakereport;

/**
 * Created by aji on 06/07/17.
 */

public class Earthquake {
    /*
    Private Data of Magnetido
     */
    private double mMag;

    /*
    Private Data of City Name
     */
    private String mCityName;

    /*
    Private Data of Date
     */
    private String mDate;

    /*
    private long mTimeInMillisecond
     */
    private long mTimeInMillisecond;


    /*
    This is Construktor
     */
    public Earthquake(double mag, String city, long date){
        mMag = mag;
        mCityName = city;
        mTimeInMillisecond = date;
    }

    /*
    get method for getmag
     */
    public double getMag(){
        return mMag;
    }

    /*
    get method for get cityname
    */
    public String getCityName(){
        return mCityName;
    }

    /*
    get method for get date
     */
    public String getDate(){
        return mDate;
    }

    /*
    get time in millisecond
     */
    public long getTimeMillisecond(){
        return mTimeInMillisecond;
    }

}
