package com.nobel.employeetracker.HR;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Base.NobelFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelloAdmin extends NobelFragment {


    protected TextView name;
    public HelloAdmin() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hello_admin, null, false);


        initView(rootView);
        name.setText(MyApplication.HRemail);
        return rootView;
    }


    private void initView(View rootView) {
        name =  rootView.findViewById(R.id.name);
    }
}
