package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by aji on 10/07/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    String mUrl;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        mUrl = url;
        Log.v("Loader EarthQuake : ","Run");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.v("Loader LoadInBack: ","Run");
        if (mUrl == null) {
            return null;
        }
        List<Earthquake> data = QueryUtils.fetchEarthquakeData(mUrl);
        return data;
    }

}
