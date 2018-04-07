package com.nobel.employeetracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by FCI on 4/17/2017.
 */

public class TrackGPS extends Service implements LocationListener {

    private Context mContext;
   boolean checkGPS = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;
    Location loc;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5;


    private static final String TAG = "MyLocationService";

    protected LocationManager locationManager;

    public TrackGPS() {
        mContext = getBaseContext();
    }

    public TrackGPS(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e("onStartCommand", "onStartCommand");
        getLocation();
        return START_STICKY;
    }

    @Override
    public void onCreate() {


    }

    private Location getLocation() {
        Log.e("Networkasdasd", "Netasdasdwork");

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);
            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            checkNetwork = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!checkGPS && !checkNetwork) {
            } else {
                this.canGetLocation = true;
                if (checkNetwork) {

                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.e("Network", "Network");
                        if (locationManager != null) {
                            loc = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        }

                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    } catch (SecurityException e) {
                        Log.e("SecurityException1", e.toString());
                    }
                }
            }
            if (checkGPS) {
                if (loc == null) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            loc = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc != null) {
                                latitude = loc.getLatitude();
                                longitude = loc.getLongitude();
                            }
                        }
                    } catch (SecurityException e) {
                        Log.e("SecurityException2", e.toString());

                    }
                }
            }

        } catch (Exception e) {
            Log.e("Exception3", e.toString());

            e.printStackTrace();
        }
        return loc;
    }

    public double getLongitude() {
        if (loc != null) {
            longitude = loc.getLongitude();
        }
        return longitude;
    }

    public double getLatitude() {
        if (loc != null) {
            latitude = loc.getLatitude();
        }
        return latitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(TrackGPS.this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {
        mContext = getBaseContext();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        MyApplication.currentLocation=location;
        Log.e("locationchanged","called");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.e("OnProviderEnabled", s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.e("OnProviderDisabled", s);
    }



    public void StopLocation() {
        locationManager.removeUpdates(this);
        locationManager = null;
    }

}