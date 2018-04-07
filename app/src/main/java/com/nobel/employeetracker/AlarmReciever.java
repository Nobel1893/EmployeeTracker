package com.nobel.employeetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Abstract.Response;
import Networking.Connector;
import Networking.ServiceCallback;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 4/1/2018.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {

            Log.e("alaram recieved","aaa");
            if (MyApplication.EMPID!=null&&MyApplication.trackGPS!=null
                    &&MyApplication.trackGPS.canGetLocation() ){
                if (MyApplication.trackGPS.getLatitude()==0f&&MyApplication.trackGPS.getLongitude()==0f){
                    Toast.makeText(context, "please enable gps", Toast.LENGTH_SHORT).show();
                    return;
                }

                Connector connector=new Connector(MyApplication.requestQueue,MyApplication.APIURL);
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                Log.e("location","Current date => " + date);

                connector.SendLocationUpdates(MyApplication.EMPID,date, MyApplication.trackGPS.getLatitude(), MyApplication.trackGPS.getLongitude()
                        , new ServiceCallback() {
                            @Override
                            public void onSuccess(Response Response) {
                                Log.e("location reciever",MyApplication.trackGPS.getLatitude()+"  "+ MyApplication.trackGPS.getLongitude());
                                Log.e("location reciever","sent success");
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
