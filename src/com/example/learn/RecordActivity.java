package com.example.learn;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class RecordActivity extends Activity {
	ListView lv;
	Button takephoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getData()));
        takephoto = (Button)findViewById(R.id.takePhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record, menu);
        return true;
    }
    private List<String> getData(){
    	LocationManager loctionManager; 
        String contextService=Context.LOCATION_SERVICE; 
        loctionManager=(LocationManager) getSystemService(contextService); 
        String provider=LocationManager.GPS_PROVIDER; 
        Location location = loctionManager.getLastKnownLocation(provider); 
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
        Date curDate = new Date(System.currentTimeMillis());
        String date = sDateFormat.format(curDate); 
        List<String> data = new ArrayList<String>();
        
        //data.add("GPS : " + location.getLongitude() + "," + location.getLatitude());
        takephoto.setText((int)location.getLongitude());
        data.add("time : " + date);
   

         

        return data;

    }
}
