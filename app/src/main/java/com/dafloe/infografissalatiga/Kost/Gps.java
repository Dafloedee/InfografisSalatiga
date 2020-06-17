package com.dafloe.infografissalatiga.Kost;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.core.content.ContextCompat;

import static androidx.constraintlayout.widget.Constraints.TAG;

//https://www.youtube.com/watch?v=Xb0DTCR1H0s&t=3s
public class Gps implements LocationListener {
    Context context;
    public Gps(Context c){
        context = c;
    }


    public Location getLocation(){

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"GPS tidak diijinkan",Toast.LENGTH_LONG).show();
            return null;
        }
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(gpsEnabled){
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,6000,0,this);
            Location l = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER );
            return l;
        } else {
            Toast.makeText(context,"GPS tidak aktif",Toast.LENGTH_LONG).show();
        }

        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
