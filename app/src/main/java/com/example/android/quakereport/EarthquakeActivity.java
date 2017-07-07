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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private EarthquakeAdapter mAdapter;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=100";

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
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

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

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);


    }

    class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

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
    }

}
