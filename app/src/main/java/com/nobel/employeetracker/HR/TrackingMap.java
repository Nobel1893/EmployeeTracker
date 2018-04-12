package com.nobel.employeetracker.HR;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Abstract.TrackObject;

public class TrackingMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static ArrayList<TrackObject> data;
    private ImageView login,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ((TextView)findViewById(R.id.title)).setText(getIntent().getStringExtra("title"));

        login=findViewById(R.id.login);
        logout=findViewById(R.id.logout);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if(data!=null && data.size()>0){

            for(int i=0;i<data.size();i++){
                // Add a marker in Sydney and move the camera
                Log.e("data",i+"");

                try {
                    double lat = Double.valueOf(data.get(i).getLat());
                    double lng = Double.valueOf(data.get(i).getLng());
                    String pic=data.get(i).getPic();

                    if (data.get(i).getType().equals("login")&&pic!=null&&!pic.equals(""))
                        ImageLoader.getInstance().displayImage(MyApplication.Images+pic,login);

                    if (data.get(i).getType().equals("logout")&&pic!=null&&!pic.equals(""))
                        ImageLoader.getInstance().displayImage(MyApplication.Images+pic,logout);


                    Log.e("data",data.get(i).getLat()+" "+data.get(i).getLat()+"  "+data.get(i).getType());

                    LatLng sydney = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(data.get(i).getType()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,12f));

                }catch (NumberFormatException e){
                    continue;
                }
            }
        }

    }
}
