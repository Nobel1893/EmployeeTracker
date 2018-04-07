package com.nobel.employeetracker.HR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Abstract.Response;
import Base.NobelActivity;
import Networking.ServiceCallback;

public class LoginHR extends NobelActivity implements View.OnClickListener {

    protected EditText email;
    protected EditText password;
    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login_hr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            String eemail=email.getText().toString();
            String ppassword=password.getText().toString();
            if (eemail.equals("")){
                email.setError("required");
                return;
            }
            if (ppassword.equals("")){
                password.setError("required");
            }
            else {
                connector.LoginHR(eemail, ppassword, new ServiceCallback() {
                    @Override
                    public void onSuccess(Response Response) {
                        HideLoadingDialogue();
                        MyApplication.HRID=Response.getData().optString("ID");
                        MyApplication.HRemail=Response.getData().optString("email");
                        Log.e("HRID",MyApplication.HRID);
                        Log.e("HRID",MyApplication.HRemail);
                        startActivity(new Intent(activity,HRHome.class));

                    }

                    @Override
                    public String onFail(Response response) {
                        ShowMessage(response.getMessage());
                        HideLoadingDialogue();
                        return super.onFail(response);
                    }
                });
            }
        }
    }

    private void initView() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(LoginHR.this);
    }
}
