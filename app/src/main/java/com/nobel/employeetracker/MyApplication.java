package com.nobel.employeetracker;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Calendar;

import Abstract.Department;
import Abstract.Employee;
import Networking.JsonParser;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 11/28/2017.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */

public class MyApplication extends Application {

    public static String APIURL = "http://alamirkamalfarag.com/rest/";
    public static String Images = "http://alamirkamalfarag.com/uploads/";
    public static RequestQueue requestQueue;
    public static ArrayList<Employee> employees;
    public static Employee LoggedInEmployee;
    public static ArrayList<Department>departments;
    public static String password;
    public static String username;
    public static String HRemail;
    public static String HRID;
    public static String EMPID;
    public static String EMPEmail;
    public static Location currentLocation;
    public static TrackGPS trackGPS;
    public static int interval =1*60;


    public static ArrayList<String>getDepartmentsNames(){
        if (departments!=null){
            ArrayList<String>names=new ArrayList<>();
            for (Department d:departments)
                names.add(d.getName());
            return names;
        }
        return new ArrayList<>();
    }

    public static String getEmployeeName(String ID){
        if (employees==null)return "";
        for (Employee e:employees)
            if (ID.equals(e.getID()))
                return e.getName();
        return "";
    }
    public static ArrayList<String>getEmployeesNames(){
        if (departments!=null){
            ArrayList<String>names=new ArrayList<>();
            for (Employee E:employees)
                names.add(E.getName());
            return names;
        }
        return new ArrayList<>();
    }



    @Override
    public void onCreate() {
        super.onCreate();

        JsonParser.getInstance();
        InitializeImageLoader();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        startService(new Intent(getApplicationContext(),TrackGPS.class));

        Intent myIntent = new Intent(getApplicationContext(), TrackGPS.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),  0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60); // first time
        long frequency= 60 * 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);

        // trackGPS=new TrackGPS(getApplicationContext());



        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DroidKufi-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        //requestQueue= Volley.newRequestQueue(getApplicationContext());
    }





    public void InitializeImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);


    }




}

