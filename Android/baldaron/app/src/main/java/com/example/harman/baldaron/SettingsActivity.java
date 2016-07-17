package com.example.harman.baldaron;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class SettingsActivity extends AppCompatActivity {

    static String TAG = "BALDARON";
    static String base_url = "https://baldaron-4adb5.firebaseio.com/";
    String m_user= "sagiben";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new QueryTask().execute();
    }



    private class QueryTask extends AsyncTask<Void, Integer, String> {
        protected String doInBackground(Void... urls) {
            try {
                StringBuffer result = new StringBuffer();
                return readFromUrl("users/" + m_user + ".json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            PopulateText(result);
        }

        private void PopulateText(String result)
        {
            try {
                JSONObject reader = new JSONObject(result);

                ImageView myImage = (ImageView)findViewById(R.id.mypic);
                URL url = new URL(reader.getString("pic_url"));
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                myImage.setImageBitmap(bmp);

                TextView myName = (TextView)findViewById(R.id.myName);
                myName.setText(reader.getString("lastname") + " " + reader.getString("firstname"));

                TextView myEmail = (TextView)findViewById(R.id.myEmail);
                myEmail.setText("Email: " + reader.getString("email"));

                TextView myPhone = (TextView)findViewById(R.id.myPhone);
                myPhone.setText("Phone: " + reader.getString("phone"));

                TextView myLeechScore = (TextView)findViewById(R.id.myLeechScore);
                myLeechScore.setText("Purchase Credit :" + reader.getString("purchase_credit"));
                TextView myMuleScore = (TextView)findViewById(R.id.myMuleScore);
                myMuleScore.setText("Mule Credit: " + reader.getString("mule_credit"));

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
                    Log.d(TAG, inputLine);
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
