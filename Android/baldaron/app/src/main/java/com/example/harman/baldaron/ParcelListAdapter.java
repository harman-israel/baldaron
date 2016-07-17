package com.example.harman.baldaron;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;

/**
 * Created by michar on 7/13/2016.
 */
public class ParcelListAdapter extends ArrayAdapter<JSONObject>{
    private int resource;
    private LayoutInflater inflater;
    private Context context;
    public ParcelListAdapter(Context ctx, int resourceId, List<JSONObject> objects) {
        super( ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context=ctx;
    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent ) {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        JSONObject parcel = getItem(position);

        try {
            TextView muleName = (TextView) convertView.findViewById(R.id.name);
            muleName.setText(parcel.getString("description"));

            TextView details = (TextView) convertView.findViewById(R.id.purchase_date);
            String txt = parcel.getString("purchase_date");
            details.setText(txt);

            //TextView mule_details = (TextView) convertView.findViewById(R.id.mule_details);
            //mule_details.setText(parcel.getString("mule"));

            TextView status = (TextView) convertView.findViewById(R.id.parcel_status);
            String strStatus = parcel.getString("status");
            status.setText(strStatus);
            if (strStatus.toLowerCase().equals("paid")) {
                status.setTextColor(Color.RED);
            }
            else if (strStatus.toLowerCase().equals("arrived")) {
                status.setTextColor(Color.rgb(0,153,0));
            }
            else if (strStatus.toLowerCase().equals("picked-up")) {
                status.setTextColor(Color.rgb(0,204,0));
            }
            else if (strStatus.toLowerCase().equals("recieved")) {
                status.setTextColor(Color.DKGRAY);
            }

            ImageView legendImage = (ImageView) convertView.findViewById(R.id.image);
            URL url = new URL(parcel.getString("url"));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            legendImage.setImageBitmap(bmp);

            ImageView typeImage = (ImageView) convertView.findViewById(R.id.type_image);
            String type = parcel.getString("type");
            if (type.equals("mule")) {
                typeImage.setImageResource(R.mipmap.ic_mule);
            }
            else if (type.equals("leech")){
                typeImage.setImageResource(R.mipmap.ic_leech);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
