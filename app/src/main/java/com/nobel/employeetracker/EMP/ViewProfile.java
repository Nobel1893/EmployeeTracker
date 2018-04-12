package com.nobel.employeetracker.EMP;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import Abstract.Employee;
import Base.NobelActivity;

public class ViewProfile extends NobelActivity {

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
        if (employee!=null) {
            fNum.setText(employee.getId_employee());
            empName.setText(employee.getName());
            birth.setText(employee.getB_date());
            hire.setText(employee.getW_date());
            rank.setText(employee.getRank());
            salary.setText(employee.getSalary());
            email.setText(employee.getEmail());
            address.setText(employee.getAddress());
            mobile.setText(employee.getMobile());
            add.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            addPhoto.setVisibility(View.GONE);
            if(employee.getPic()!=null){
              ImageLoader.getInstance().displayImage(MyApplication.Images+employee.getPic(),image);
            }
        }
    }
    public static Employee employee;


    private void initView() {
        addPhoto = (Button) findViewById(R.id.add_photo);
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
        edit = (Button) findViewById(R.id.edit);
        image =  findViewById(R.id.image);
        findViewById(R.id.pass_layout).setVisibility(View.GONE);
        findViewById(R.id.dep_layout).setVisibility(View.GONE);

    }
}
