package com.nobel.employeetracker.EMP;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nobel.employeetracker.AlarmReciever;
import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;
import com.nobel.employeetracker.TrackGPS;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Abstract.Employee;
import Abstract.Response;
import Base.NobelActivity;
import Networking.DataPart;
import Networking.FileType;
import Networking.JsonParser;
import Networking.ServiceCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginEmployee extends NobelActivity implements View.OnClickListener {
    protected EditText email;
    protected EditText password;
    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();




    }



    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;

public void StartTrackingService()
{


    // OnCreate()
    alarmIntent = new Intent ( getApplicationContext(), AlarmReciever.class );
    pendingIntent = PendingIntent.getBroadcast( this.getApplicationContext(), 234324243, alarmIntent, 0 );
    alarmManager = ( AlarmManager ) getSystemService( ALARM_SERVICE );
    alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, ( MyApplication.interval * 1000 ),( MyApplication.interval * 1000 ), pendingIntent );
//    startService(new Intent(getApplicationContext(), TrackGPS.class));

}



    String eemail;
    String ppassword;

HashMap<String, DataPart>imgs;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
              eemail=email.getText().toString();
             ppassword=password.getText().toString();
            if (eemail.equals("")){
                email.setError("required");
                return;
            }
            if (ppassword.equals("")){
                password.setError("required");
            }else {
                Login();

            }
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
                            SendLoginUpdates();
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



    Bitmap btimage;
    public void Login(){


        activity.GetLoadingDialogue();

        connector.LoginEMP(eemail, ppassword ,new ServiceCallback() {
            @Override
            public void onSuccess(Response Response) {
                MyApplication.EMPID=Response.getData().optString("ID");
                MyApplication.EMPEmail=Response.getData().optString("email");

                connector.getEmployeeData(MyApplication.EMPID, new ServiceCallback() {
                    @Override
                    public void onSuccess(Response Response) {
                        activity.HideLoadingDialogue();
                        MyApplication.LoggedInEmployee= JsonParser.fromJsonString(Response.getData().toString(), Employee.class);
                        MyApplication.trackGPS=new TrackGPS(activity);
                        StartTrackingService();
                        activity.ShowDialogeConfirmation("HR", "please take a photo to login", "ok",
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        StartPickerAction(1,1);
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                });
                    }

                });





            }

            @Override
            public String onFail(Response response) {
                ShowMessage(response.getMessage());
                HideLoadingDialogue();
                return super.onFail(response);
            }
        });
    }

    public void SendLoginUpdates(){


        imgs = new HashMap<>();
        if (idimage!=null) {
            imgs.put("pic", new DataPart("IDImg." + idimage.getExtention(), idimage.getContent(), idimage.getMimiType()));
        }

        if (MyApplication.EMPID!=null&&MyApplication.trackGPS!=null){
            if (MyApplication.trackGPS.getLatitude()==0f&&MyApplication.trackGPS.getLongitude()==0f){
                Toast.makeText(activity, "please enable gps", Toast.LENGTH_SHORT).show();
            }

            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            Log.e("location","Current date => " + date);

            GetLoadingDialogue();
            connector.SendLoginUpdates(MyApplication.EMPID,date, MyApplication.trackGPS.getLatitude(),
                    MyApplication.trackGPS.getLongitude(),
                    imgs ,   new ServiceCallback() {
                        @Override
                        public void onSuccess(Response Response) {
                            HideLoadingDialogue();
                            Log.e("location login",MyApplication.trackGPS.getLatitude()+"  "+ MyApplication.trackGPS.getLongitude());
                            Log.e("location login","sent success");
                            startActivity(new Intent(activity,EMPHome.class));
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
    private void initView() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(LoginEmployee.this);
    }

}
