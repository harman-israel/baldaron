package com.example.harman.baldaron;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class ShowParcelActivity extends AppCompatActivity {

    static String TAG = "BALDARON_PARCEL";
    static String base_url = "https://baldaron-4adb5.firebaseio.com/";
    private String m_mule_id;
    private JSONObject m_data;
    private String m_type;
    private String m_leech_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parcel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String data = getIntent().getExtras().getString("parcel");
        try {
            m_data = new JSONObject(data);
            m_mule_id = m_data.getString("mule");
            m_leech_id = m_data.getString("leecher");
            m_type = m_data.getString("type");
            new QueryTask().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class QueryTask extends AsyncTask<Void, Integer, String> {
        protected String doInBackground(Void... urls) {
            try {
                StringBuffer result = new StringBuffer();
                if (m_type.equals("mule"))
                    return readFromUrl("users/" + m_leech_id + ".json");
                else if (m_type.equals("leech"))
                    return readFromUrl("mules/" + m_mule_id + ".json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject reader = new JSONObject(result);
                Log.d(TAG, reader.toString());
                Log.d(TAG, m_data.toString());

                TextView product_name = (TextView) findViewById(R.id.parcel_name);
                String strProduct = m_data.getString("description");
                product_name.setText(strProduct);

                if (m_type.equals("leech")) {

                    TextView fullname = (TextView) findViewById(R.id.parcel_mule_fullname);
                    String strFullname = reader.getString("username");
                    fullname.setText("Mule Name: " + strFullname);

                    TextView phone = (TextView) findViewById(R.id.parcel_mule_phone);
                    String strPhone = reader.getString("mulephone");
                    phone.setText("Mule Phone: " + strPhone);


                } else {
                    TextView fullname = (TextView) findViewById(R.id.parcel_mule_fullname);
                    String strFullname = reader.getString("firstname");
                    strFullname += " " + reader.getString("lastname");
                    fullname.setText("Leecher: " + strFullname);

                    TextView phone = (TextView) findViewById(R.id.parcel_mule_phone);
                    String strPhone = reader.getString("phone");
                    phone.setText("Leecher Phone: " + strPhone);
                }

                TextView date = (TextView) findViewById(R.id.purchase_date);
                String strDate = m_data.getString("purchase_date");
                date.setText("Purchase date: " + strDate);

                TextView status = (TextView) findViewById(R.id.parcel_status);
                String strStatus = m_data.getString("status");
                status.setText("Status: " + strStatus);


                ImageView iv = (ImageView)findViewById(R.id.parcel_image);
                URL url = new URL(m_data.getString("url"));
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                iv.setImageBitmap(bmp);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String readFromUrl(String query) throws MalformedURLException {
            String outBuf = "";
            URL yahoo = null;
            try {
                yahoo = new URL(base_url + query);
                BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    //Log.d(TAG, inputLine);
                    outBuf+=inputLine;
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outBuf;
        }
    }

}
