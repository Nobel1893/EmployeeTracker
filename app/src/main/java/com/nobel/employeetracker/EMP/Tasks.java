package com.nobel.employeetracker.EMP;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import java.util.ArrayList;

import Abstract.Response;
import Abstract.Task;
import Adapters.EmployeeTasksAdapter;
import Base.NobelFragment;
import Networking.JsonParser;
import Networking.ServiceCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tasks extends NobelFragment implements View.OnClickListener {


    protected Button finished;
    protected Button unfinished;
    protected RecyclerView recyclerview;

    public Tasks() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tasks, null, false);


        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnFinishedTasks();

    }

    ServiceCallback callback=new ServiceCallback() {
        @Override
        public void onSuccess(Response Response) {
            activity.HideLoadingDialogue();

            tasks= JsonParser.fromJsonArray(Response.getAlldata().toString(),Task[].class);
            tasks.remove(0);
            Setdata();
        }

        @Override
        public String onFail(Response response) {
            activity.HideLoadingDialogue();
            ShowMessage(response.getMessage());
            return super.onFail(response);
        }
    };


    EmployeeTasksAdapter adapter;

    boolean isfinished=true;
    public void Setdata(){
        adapter=new EmployeeTasksAdapter(tasks);


        adapter.setEditLisitner(new EmployeeTasksAdapter.Listener() {
            @Override
            public void onClick(int position) {

                UpdateTask.task=tasks.get(position);
                startActivity(new Intent(activity,UpdateTask.class).putExtra("title","Edit Task"));
            }
        });

        recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(layoutManager);
    }



    ArrayList<Task>tasks;


    public void getFinishedTasks(){
        finished.setBackgroundColor(getResources().getColor(R.color.green));
        unfinished.setBackgroundColor(getResources().getColor(R.color.grey));

        activity.GetLoadingDialogue();
        connector.getFinishedTasksForEmployee(MyApplication.EMPID,callback);

    }

    public void getUnFinishedTasks(){
        unfinished.setBackgroundColor(getResources().getColor(R.color.green));
        finished.setBackgroundColor(getResources().getColor(R.color.grey));

        activity.GetLoadingDialogue();
        connector.getUnFinishedTasksForEmployee(MyApplication.EMPID,callback);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.finished) {
            getFinishedTasks();
            isfinished=true;
        } else if (view.getId() == R.id.unfinished) {
            getUnFinishedTasks();
            isfinished=false;
        }
    }

    private void initView(View rootView) {
        finished = (Button) rootView.findViewById(R.id.finished);
        finished.setOnClickListener(Tasks.this);
        unfinished = (Button) rootView.findViewById(R.id.unfinished);
        unfinished.setOnClickListener(Tasks.this);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
    }
}
