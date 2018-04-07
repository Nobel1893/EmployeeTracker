package com.nobel.employeetracker.HR;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import java.util.ArrayList;

import Abstract.Employee;
import Abstract.Response;
import Adapters.EmployeesAdapter;
import Base.NobelFragment;
import Networking.JsonParser;
import Networking.ServiceCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class Employees extends NobelFragment {


    protected RecyclerView recyclerview;

    public Employees() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_employees, null, false);

        initView(rootView);
        return rootView;
    }

    ArrayList<Employee> employees=new ArrayList<>();
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){
        activity.GetLoadingDialogue();
        connector.getAllEmployees(new ServiceCallback() {
            @Override
            public void onSuccess(Response Response) {

                activity.HideLoadingDialogue();
                employees= JsonParser.fromJsonArray(Response.getAlldata().toString(),Employee[].class);
                employees.remove(0);
                MyApplication.employees=employees;
                Setdata();
            }

            @Override
            public String onFail(Response response) {
                activity.HideLoadingDialogue();
                activity.ShowMessage(response.getMessage());
                return super.onFail(response);
            }
        });

    }
    EmployeesAdapter adapter;
    public void Setdata(){
        adapter=new EmployeesAdapter(employees);
       adapter.setDeleteLisitner(new EmployeesAdapter.Listener() {
           @Override
           public void onClick(int position) {
               ShowLoadingDialogue();
               connector.DeleteEmployee(employees.get(position).getID(), new ServiceCallback() {
                   @Override
                   public void onSuccess(Response Response) {
                       activity.HideLoadingDialogue();
                       ShowMessage("successfuly deleted");
                       getData();

                   }

                   @Override
                   public String onFail(Response response) {
                       activity.HideLoadingDialogue();
                       ShowMessage(response.getMessage());
                       return super.onFail(response);
                   }
               });
           }
       });

       adapter.setEditLisitner(new EmployeesAdapter.Listener() {
           @Override
           public void onClick(int position) {
               AddEmployee.employee=employees.get(position);
               AddEmployee.isedit=true;
               startActivity(new Intent(activity,AddEmployee.class).putExtra("title","Edit Employee"));
           }
       });
        recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutManager);



    }
    private void initView(View rootView) {
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
    }
}
