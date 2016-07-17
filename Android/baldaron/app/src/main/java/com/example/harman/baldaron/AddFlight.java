package com.example.harman.baldaron;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddFlight extends AppCompatActivity {
    String m_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QueryTask().execute();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView typeImage = (ImageView) findViewById(R.id.flitgh_image);
        typeImage.setImageResource(R.mipmap.ic_addflight);

        m_user = getIntent().getExtras().getString("username");
    }


    private class QueryTask extends AsyncTask<Void, Integer, String> {
        protected String doInBackground(Void... urls) {
            try {
                StringBuffer result = new StringBuffer();
                patchUrlDB("users/" + ".json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String patchUrlDB(String query) throws MalformedURLException {
            URL url;
            String response = "";
            try {
                url = new URL("https://baldaron-4adb5.firebaseio.com/mules.json");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("PATCH");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String request = "{\"999\":{\"address1\":\" 27033 Northwestern Hwy\",\"address2\":\" \",\"city\":\"Southfield\",\"country\":\"United States\",\"end_date\":\"2016-08-10\",\"handoff_date\":\"2016-08-11\",\"mulephone\":\"248-356-7400\",\"num_items\":2,\"size\":\"16x16x16\",\"start_date\":\"2016-08-01\",\"state\":\"MI\",\"username\":\"michar\",\"weight\":4,\"zip\":48033}}";
                //String request = "{\"XX\":{\"f1\":\"1234\"}}";

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(request);

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

    }

}
