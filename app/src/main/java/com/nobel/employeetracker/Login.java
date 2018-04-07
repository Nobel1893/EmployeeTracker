package com.nobel.employeetracker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.nobel.employeetracker.EMP.LoginEmployee;
import com.nobel.employeetracker.HR.LoginHR;

import Base.NobelActivity;

public class Login extends NobelActivity implements View.OnClickListener {

    protected Button loginHr;
    protected Button loginEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();

        checkLocationPermission();
          //  StartLocationService();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_hr) {
            startActivity(new Intent(this,LoginHR.class));

        } else if (view.getId() == R.id.login_emp) {
            startActivity(new Intent(this,LoginEmployee.class));

        }
    }

    public void StartLocationService(){
        startService(new Intent(getBaseContext(), TrackGPS.class));
    }
    private void initView() {
        loginHr = (Button) findViewById(R.id.login_hr);
        loginHr.setOnClickListener(Login.this);
        loginEmp = (Button) findViewById(R.id.login_emp);
        loginEmp.setOnClickListener(Login.this);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location persmession")
                        .setMessage("please enable this app to use Location !!")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Login.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        StartLocationService();
                        ;

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    MyApplication.trackGPS.stopUsingGPS();

                }
                return;
            }

        }
    }
}
