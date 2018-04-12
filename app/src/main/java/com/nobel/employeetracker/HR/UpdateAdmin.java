package com.nobel.employeetracker.HR;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Abstract.Response;
import Base.NobelFragment;
import Networking.ServiceCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateAdmin extends NobelFragment implements View.OnClickListener {


    protected TextView name;
    protected EditText email;
    protected EditText password;
    protected Button edit;

    public UpdateAdmin() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_admin, null, false);


        initView(rootView);
        email.setText(MyApplication.HRemail);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edit) {

            final String semail=email.getText().toString();
            final String pass=password.getText().toString();
            if(semail.equals("")){
                email.setError("required");

            }else if (pass.equals("")){
                password.setError("required");
            }else {
                activity.GetLoadingDialogue();
                connector.updateAdmin(MyApplication.HRID,semail,pass,new ServiceCallback(){

                    @Override
                    public void onSuccess(Response Response) {
                        activity.HideLoadingDialogue();
                        MyApplication.HRemail=semail;
                        ShowMessage("updated succcessfuly");
                        password.setText("");
                    }

                    @Override
                    public String onFail(Response response) {
                        activity.HideLoadingDialogue();
                        ShowMessage(response.getMessage());
                        return super.onFail(response);
                    }
                });
            }
        }
    }

    private void initView(View rootView) {
        email =  rootView.findViewById(R.id.email);
        password =  rootView.findViewById(R.id.password);
        edit =  rootView.findViewById(R.id.edit);
        edit.setOnClickListener(UpdateAdmin.this);
    }
}
