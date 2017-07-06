package com.example.android.quakereport;

/**
 * Created by aji on 06/07/17.
 */

public class Earthquake {
    /*
    Private Data of Magnetido
     */
    private String mMag;

    /*
    Private Data of City Name
     */
    private String mCityName;

    /*
    Private Data of Date
     */
    private String mDate;


    /*
    This is Construktor
     */
    public Earthquake(String mag, String city, String date){
        mMag = mag;
        mCityName = city;
        mDate = date;
    }

    /*
    get method for getmag
     */
    public String getMag(){
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

}
