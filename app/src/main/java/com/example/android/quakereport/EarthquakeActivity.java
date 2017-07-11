/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private EarthquakeAdapter mAdapter;

    private TextView emptyView;

    private ListView earthquakeListView;

    private ProgressBar progressBar;

    private static final String USGS_REQUEST_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&limit=2";

    //private ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
         //new ArrayList<Earthquake>();
        /*earthquakes.add(new Earthquake("7.1", "San Francisco", "Feb 2, 2016"));
        earthquakes.add(new Earthquake("6.1","London","July 20,2015"));
        earthquakes.add(new Earthquake("3.9","Tokyo", "Nov 10, 2014"));
        earthquakes.add(new Earthquake("5.4","Mexico City","May 3, 2014"));
        earthquakes.add(new Earthquake("2.8","Moscow","Jan 31,2013"));
        earthquakes.add(new Earthquake("4.9","Rio de Janeiro","Aug 19, 2012"));
        earthquakes.add(new Earthquake("1.6","Paris","Oct 30,2011"));*/

        // Cari referensi pada {@link ListView} pada layout
        earthquakeListView = (ListView) findViewById(R.id.list);

        // Cari referensi pada layout
        emptyView = (TextView) findViewById(R.id.empty_list);

        // show empty jika tidak ada data yang ditampilkan
        earthquakeListView.setEmptyView(emptyView);

        // Buat adapter baru yang mengambil daftar kosong gempa sebagai inputnyaåå
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Atur adapter di {@link ListView}
        // agar daftar dapat diisi di antarmuka pengguna
        earthquakeListView.setAdapter(mAdapter);

        // Atur item click listener di ListView, yang mengirimkan intent ke perambah web
        // untuk membuka situs web dengan lebih banyak informasi mengenai gempa tertentu.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Temukan gempa terkini yang diklik di
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Ubah String URL menjadi objek URI (untuk memasuki konstruktor Intent)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Buat intent baru untuk melihat URI gempa
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Kirimkan intent agar membuka aktivitas baru
                startActivity(websiteIntent);
            }
        });

        //AsyncTask
        //EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        //task.execute(USGS_REQUEST_URL);

        //get service phone to check internet conection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //jika tidak ada koneksi maka tampil tidak ada koneksi
        if(!isConnected){

            emptyView.setText(R.string.tidakAdaKoneksi);

            // Cari Referensi untuk progressbar
            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            //progressbar hilang
            progressBar.setVisibility(View.GONE);

        }else{
            //AsyncTask With LoaderManager
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);
        Log.v("URL :"," " + uriBuilder.toString());
        return new EarthquakeLoader(this, uriBuilder.toString());
        /* Old One
        Log.v("Loader OnCreated : ","Run");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
        */
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.v("Loader OnFinish : ","Run");
        // Bersihkan adapter dari data gempa sebelumnya
        mAdapter.clear();

        // Jika ada daftar {@link Earthquake} yang valid, tambahkan ke data set adapter.
        // Ini akan memicu pembaruan ListView..
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

        // Cari Referensi untuk progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //progressbar hilang
        progressBar.setVisibility(View.GONE);

        //empty text
        emptyView.setText(R.string.kosong);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.v("Loader OnReset : ","Run");
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Old AsyncTask
    /*class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> data = QueryUtils.fetchEarthquakeData(urls[0]);
            return data;
        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Bersihkan adapter dari data gempa sebelumnya
            mAdapter.clear();

            // Jika ada daftar {@link Earthquake} yang valid, tambahkan ke data set adapter.
            // Ini akan memicu pembaruan ListView..
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }*/
}
