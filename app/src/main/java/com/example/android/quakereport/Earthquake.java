package com.example.android.quakereport;

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
    Private URL discription
     */
    private String mUrl;

    /*
    This is Construktor
     */
    public Earthquake(double mag, String city, long date,String url){
        mMag = mag;
        mCityName = city;
        mTimeInMillisecond = date;
        mUrl = url;
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

    /*
    get url data
     */
    public String getUrl(){
        return mUrl;
    }

}
