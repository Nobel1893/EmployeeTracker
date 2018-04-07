package com.nobel.employeetracker.EMP;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Abstract.Response;
import Base.NobelActivity;
import Networking.Connector;
import Networking.DataPart;
import Networking.FileType;
import Networking.ServiceCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EMPHome extends NobelActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emphome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_department);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment frag = null;
         if (id == R.id.nav_profile) {
             ViewProfile.employee= MyApplication.LoggedInEmployee;

            startActivity(new Intent(this,ViewProfile.class).putExtra("title","My Profile"));
        }
        else if (id == R.id.nav_tasks) {
            frag = new Tasks();
        }
        else if (id == R.id.nav_logout) {
             activity.ShowDialogeConfirmation("HR", "please take a photo to logout", "ok",
                     new SweetAlertDialog.OnSweetClickListener() {
                         @Override
                         public void onClick(SweetAlertDialog sweetAlertDialog) {
                             StartPickerAction(1,1);
                             sweetAlertDialog.dismissWithAnimation();
                         }
                     });

         }



        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    HashMap<String, DataPart>imgs;
    public void Logout(){
        imgs = new HashMap<>();
        if (idimage!=null) {
            imgs.put("pic", new DataPart("IDImg." + idimage.getExtention(), idimage.getContent(), idimage.getMimiType()));
        }

        if (MyApplication.EMPID!=null&&MyApplication.trackGPS!=null){
            if (MyApplication.trackGPS.getLatitude()==0f&&MyApplication.trackGPS.getLongitude()==0f){
                Toast.makeText(activity, "please enable gps", Toast.LENGTH_SHORT).show();
            }

            Connector connector=new Connector(MyApplication.requestQueue,MyApplication.APIURL);
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            Log.e("location","Current date => " + date);

            connector.SendLogoutUpdates(MyApplication.EMPID,date, MyApplication.trackGPS.getLatitude(),
                    MyApplication.trackGPS.getLongitude(), imgs,   new ServiceCallback() {
                        @Override
                        public void onSuccess(Response Response) {
                            HideLoadingDialogue();
                            Log.e("location logout",MyApplication.trackGPS.getLatitude()+"  "+ MyApplication.trackGPS.getLongitude());
                            Log.e("location logout","sent success");
                            finish();
                        }

                        @Override
                        public String onFail(Response response) {
                            HideLoadingDialogue();
                            ShowMessage(response.getMessage());

                            return super.onFail(response);
                        }
                    }
            );
        }






    }


    public void StartPickerAction(int min, int max) {
        FishBun.with(this)
                .MultiPageMode()
                .setIsUseDetailView(false)
                .setMaxCount(max)
                .setMinCount(min)
                .setPickerSpanCount(6)
                .setAlbumSpanCount(2, 4)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .textOnImagesSelectionLimitReached("Limit Reached!")
                .textOnNothingSelected("Nothing Selected")
                .startAlbum();

    }
    Uri img = null;

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    // path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                    // you can get an image path(ArrayList<String>) on <0.6.2
                    ArrayList<Uri> arr = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    // you can get an image path(ArrayList<Uri>) on 0.6.2 and later
                    if (arr != null && arr.size() > 0) {
                        img = arr.get(0);
                        if (PrepareImage()) {
                            Log.e("image ", "loaded");
                        Logout();
                        }
                    }else {
                        ShowMessage("no images choosen");
                    }

                }else {
                    ShowMessage("no images choosen");
                }
                break;
            }
        }

    }
    FileType idimage;

    private boolean PrepareImage() {
        try {
            ContentResolver cR = activity.getContentResolver();
            //   InputStream iStream = cR.openInputStream(img);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            byte[] inputData = getBytes(img);
            idimage = new FileType();
            idimage.setFileUri(img);
            idimage.setMimiType(cR.getType(img));
            idimage.setExtention(mime.getExtensionFromMimeType(cR.getType(img)));
            idimage.setContent(inputData);
            return true;
        } catch (IOException e) {
            Log.e("io e", "cannot handle image");
            return false;
        }
    }


    public byte[] getBytes( Uri imageUri) throws IOException {
        Bitmap bmp =  MediaStore.Images.Media.getBitmap(activity.getContentResolver(),imageUri);;
        while (bmp.getWidth()>3200||bmp.getHeight()>3200) {
            bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 2, bmp.getHeight() / 2, false);
            Log.e("compressing",bmp.getWidth()+"");
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100, stream);

        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }



    TextView title;

    private void updateToolbarText(CharSequence text) {
        title.setText(text);
    }

}
