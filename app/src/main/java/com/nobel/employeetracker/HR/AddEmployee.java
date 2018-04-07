package com.nobel.employeetracker.HR;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Abstract.Employee;
import Abstract.Response;
import Base.NobelActivity;
import Networking.DataPart;
import Networking.FileType;
import Networking.ServiceCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddEmployee extends NobelActivity implements View.OnClickListener {

    protected Button addPhoto;
    protected EditText fNum;
    protected EditText empName;
    protected EditText birth;
    protected EditText hire;
    protected EditText rank;
    protected EditText salary;
    protected Spinner depName;
    protected EditText email;
    protected EditText address;
    protected EditText mobile;
    protected EditText password;
    protected Button add;
    protected Button edit;
    protected ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ShowHomeasUpEnabled();
        initView();
        if (isedit&&employee!=null) {
            fNum.setText(employee.getId_employee());
            empName.setText(employee.getName());
            birth.setText(employee.getB_date());
            hire.setText(employee.getW_date());
            rank.setText(employee.getRank());
            salary.setText(employee.getSalary());
            email.setText(employee.getEmail());
            address.setText(employee.getAddress());
            mobile.setText(employee.getMobile());
            password.setText(employee.getPassword());
            add.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            if (employee.getPic()!=null&&!employee.getPic().equals("")){
                ImageLoader imageLoader=ImageLoader.getInstance();
                imageLoader.displayImage(MyApplication.Images+employee.getPic(),image);
            }
        }
    }
    protected String sfNum;
    protected String sempName;
    protected String sbirth;
    protected String shire;
    protected String srank;
    protected String ssalary;
    protected String sdepName;
    protected String semail;
    protected String saddress;
    protected String smobile;
    protected String spassword;
    FileType idimage;
    public static Employee employee;
    public static boolean isedit=false;
    public String Validate(EditText e){
        if (e.getText().toString().equals(""))
        {
            e.setError("required");
            return null;
        }
        return e.getText().toString();
    }

    public boolean Validate(){

        sfNum=Validate(fNum);
        sempName=Validate(empName);
        sbirth=Validate(birth);
        shire=Validate(hire);
        srank=Validate(rank);
        ssalary=Validate(salary);
        sdepName=depName.getSelectedItem().toString();
        semail=Validate(email);
        saddress=Validate(address);
        smobile=Validate(mobile);
        spassword=Validate(password);
       if (sfNum==null||sempName==null||sbirth==null||shire==null||srank==null||ssalary==null||sdepName==null
               ||semail==null||saddress==null||smobile==null||spassword==null)
           return false;

       return true;

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
                        if (PrepareImage())
                            Log.e("image ","loaded");

                    }

                }
                break;
            }
        }

    }

    private boolean PrepareImage() {
        try {
            ContentResolver cR = activity.getContentResolver();
            //   InputStream iStream = cR.openInputStream(img);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            image.setImageURI(img);
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


    boolean Validated=false;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_photo) {
            StartPickerAction(1, 1);



        } else if (view.getId() == R.id.add) {

            if (Validate()){
                employee=new Employee();
                employee.setAddress(saddress);
                employee.setB_date(sbirth);
                employee.setEmail(semail);
                employee.setId_employee(sfNum);
                employee.setMobile(smobile);
                employee.setName(sempName);
                employee.setRank(srank);
                employee.setSalary(ssalary);
                employee.setW_date(shire);
                employee.setPassword(spassword);
                employee.setSection(sdepName);
                HashMap<String, DataPart> imgs = new HashMap<>();
                if (idimage!=null)
                 imgs.put("pic", new DataPart("IDImg." + idimage.getExtention(),idimage.getContent(), idimage.getMimiType()));

                GetLoadingDialogue();
                connector.AddEmployee(employee,imgs,callback);
            }
        }else if (view.getId() == R.id.edit) {

            if (Validate()){
                Employee e;
                e=new Employee();
                e.setAddress(saddress);
                e.setB_date(sbirth);
                e.setEmail(semail);
                e.setId_employee(sfNum);
                e.setMobile(smobile);
                e.setName(sempName);
                e.setRank(srank);
                e.setSalary(ssalary);
                e.setW_date(shire);
                e.setPassword(spassword);
                e.setSection(sdepName);
                GetLoadingDialogue();
                HashMap<String, DataPart> imgs = new HashMap<>();
                if (idimage!=null)
                    imgs.put("pic", new DataPart("IDImg." + idimage.getExtention(),idimage.getContent(), idimage.getMimiType()));
                connector.UpdateEmployee(employee.getID(),e,imgs,callback);

            }
        }
    }

    ServiceCallback callback=new ServiceCallback() {
        @Override
        public void onSuccess(Response Response) {
            HideLoadingDialogue();
            ShowDialogeٍSuccessful("", Response.getMessage(), "ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    finish();
                }
            });

        }

        @Override
        public String onFail(Response response) {
            HideLoadingDialogue();
            ShowMessage(response.getMessage());
            return super.onFail(response);
        }
    };
    private void initView() {
        image =  findViewById(R.id.image);
        addPhoto = (Button) findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(AddEmployee.this);
        fNum = (EditText) findViewById(R.id.f_num);
        empName = (EditText) findViewById(R.id.emp_name);
        birth = (EditText) findViewById(R.id.birth);
        hire = (EditText) findViewById(R.id.hire);
        rank = (EditText) findViewById(R.id.rank);
        salary = (EditText) findViewById(R.id.salary);
        depName =  findViewById(R.id.dep_name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(AddEmployee.this);
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(AddEmployee.this);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        MyApplication.getDepartmentsNames()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        depName.setAdapter(spinnerArrayAdapter);
    }
}
