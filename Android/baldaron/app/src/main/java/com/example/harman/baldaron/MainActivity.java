package com.example.harman.baldaron;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.quickstart.database.models.User;
//import com.google.firebase.quickstart.database.models.Post;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String json;
    String m_user= "sagiben";
    private ListView mainListView ;
    List<JSONObject> parcel_list;
    static String TAG = "BALDARON";
    static String base_url = "https://baldaron-4adb5.firebaseio.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        parcel_list = new ArrayList<JSONObject>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFlight.class);
                intent.putExtra("username", m_user);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new QueryTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class QueryTask extends AsyncTask<Void, Integer, String> {
        protected String doInBackground(Void... urls) {
            try {
                StringBuffer result = new StringBuffer();
                return readFromUrl("parcels.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            json = result;
            PopulateText();
        }

        private void PopulateText()
        {
            mainListView = (ListView) findViewById( R.id.listView );

            try {
                JSONObject reader = new JSONObject(json);
                Iterator<String> iter = reader.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    JSONObject obj = reader.getJSONObject(key);
                    if (obj.getString("leecher").equals(m_user)) {
                        obj.putOpt("type", "leech");
                    }
                    else if (obj.getString("mule_username").equals(m_user)) {
                        obj.putOpt("type", "mule");
                    }
                    parcel_list.add(obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            mainListView = ( ListView ) findViewById(R.id.listView);
            mainListView.setAdapter(new ParcelListAdapter(getApplicationContext(), R.layout.listitem, parcel_list));

            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JSONObject obj = (JSONObject) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), ShowParcelActivity.class);
                    intent.putExtra("parcel", obj.toString());
                    startActivity(intent);
                }
            });
        }


        private String readFromUrl(String query) throws MalformedURLException {
            String outBuf = "";
            URL yahoo = null;
            try {
                yahoo = new URL(base_url + query); //?orderBy=\"leecher\"&equalTo=\"sagiben\"");
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
