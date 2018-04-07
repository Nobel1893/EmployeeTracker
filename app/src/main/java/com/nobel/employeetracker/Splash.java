package com.nobel.employeetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import Abstract.Department;
import Abstract.Employee;
import Abstract.Response;
import Base.NobelActivity;
import Networking.JsonParser;
import Networking.ServiceCallback;

public class Splash extends NobelActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity,Login.class));
                finish();
            }
        },2000);

        connector.getAllEmployees(new ServiceCallback() {
            @Override
            public void onSuccess(Response Response) {
                MyApplication.employees= JsonParser.fromJsonArray(Response.getAlldata().toString(), Employee[].class);
                MyApplication.employees.remove(0);
            }
        });
        connector.GetDepartments(new ServiceCallback() {
            @Override
            public void onSuccess(Response Response) {
                MyApplication.departments= JsonParser.fromJsonArray(Response.getAlldata().toString(), Department[].class);
                MyApplication.departments.remove(0);

            }
        });

    }
}
