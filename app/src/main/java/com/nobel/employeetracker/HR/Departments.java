package com.nobel.employeetracker.HR;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import java.util.ArrayList;

import Abstract.Department;
import Abstract.Response;
import Adapters.DepartmentsAdapter;
import Base.NobelFragment;
import Networking.JsonParser;
import Networking.ServiceCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class Departments extends NobelFragment implements View.OnClickListener {


    protected EditText name;
    protected Button add;
    protected RecyclerView recyclerview;

    public Departments() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_departments, null, false);

        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            String nname=name.getText().toString();
            if (nname.equals(""))
                name.setError("required");
            else {
                activity.GetLoadingDialogue();
                connector.AddDepartment(nname, new ServiceCallback() {
                    @Override
                    public void onSuccess(Response Response) {
                        activity.HideLoadingDialogue();
                        activity.ShowMessage("successfuly Added");
                        getData();

                    }

                    @Override
                    public String onFail(Response response) {
                        activity.HideLoadingDialogue();
                        Log.e("error",response.getMessage());
                        activity.ShowMessage(response.getMessage());
                        return super.onFail(response);
                    }
                });
            }
        }
    }

    DepartmentsAdapter adapter;
    ArrayList<Department>departments;

    public void Setdata(){
        adapter=new DepartmentsAdapter(departments);
        adapter.setDeleteLisitner(new DepartmentsAdapter.Listener() {
            @Override
            public void onClick(int position) {
                ShowLoadingDialogue();
                connector.DelDepartment(departments.get(position).getID(), new ServiceCallback() {
                    @Override
                    public void onSuccess(Response Response) {
                        activity.HideLoadingDialogue();
                        ShowMessage("successfuly deleted");
                        getData();

                    }

                    @Override
                    public String onFail(Response response) {
                        activity.HideLoadingDialogue();
                        Log.e("error",response.getMessage());
                        ShowMessage(response.getMessage());
                        return super.onFail(response);
                    }
                });
            }
        });

        adapter.setEditLisitner(new DepartmentsAdapter.Listener() {
            @Override
            public void onClick(int position) {
                ShowUpadateDialoge(departments.get(position).getID());
            }
        });

        recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutManager);
    }


    public void ShowUpadateDialoge(final String id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("edit");
        alertDialog.setMessage("Enter Department Name");

        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
//        alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("edit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String nname = input.getText().toString();
                        if (nname.equals("")){
                            input.setError("required");
                        }
                        else {
                            updateDepartment(id,nname);
                           dialog.dismiss();
                        }

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void updateDepartment(String id,String name){
        activity.GetLoadingDialogue();

        connector.updateDepartment(id,name,new ServiceCallback(){
            @Override
            public void onSuccess(Response Response) {
                activity.HideLoadingDialogue();
                getData();
            }

            @Override
            public String onFail(Response response) {
                activity.HideLoadingDialogue();
                activity.ShowMessage(response.getMessage());
                return super.onFail(response);
            }
        });

    }



    public void getData(){
        Log.e("data","data");


        activity.GetLoadingDialogue();
        connector.GetDepartments(new ServiceCallback() {
            @Override
            public void onSuccess(Response Response) {

                activity.HideLoadingDialogue();
                departments= JsonParser.fromJsonArray(Response.getAlldata().toString(), Department[].class);
                departments.remove(0);
                MyApplication.departments=departments;
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



    private void initView(View rootView) {
        name = (EditText) rootView.findViewById(R.id.name);
        add = (Button) rootView.findViewById(R.id.add);
        add.setOnClickListener(Departments.this);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
    }
}
