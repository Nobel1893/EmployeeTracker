package com.nobel.employeetracker.EMP;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nobel.employeetracker.R;

import Abstract.Response;
import Abstract.Task;
import Base.NobelActivity;
import Networking.ServiceCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateTask extends NobelActivity implements View.OnClickListener {

    protected Spinner empName;
    protected EditText taskName;
    protected EditText taskDetails;
    protected EditText time;
    protected EditText startDate;
    protected EditText endDate;
    protected Button add;
    protected Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ShowHomeasUpEnabled();
        initView();

        if (task!=null){
            taskName.setText(task.getTitle());
            taskDetails.setText(task.getTask());
            time.setText(task.getPeriod());
            startDate.setText(task.getS_date());
            endDate.setText(task.getE_date());
            add.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

        }


    }
    public String Validate(EditText e){
        if (e.getText().toString().equals(""))
        {
            e.setError("required");
            return null;
        }
        return e.getText().toString();
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

    public static Task task;
    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.edit) {
                GetLoadingDialogue();
                connector.FinishTask(task.getID(),callback);
            }
    }

    private void initView() {
        empName = (Spinner) findViewById(R.id.emp_name);
        taskName = (EditText) findViewById(R.id.task_name);
        taskDetails = (EditText) findViewById(R.id.task_details);
        time = (EditText) findViewById(R.id.time);
        startDate = (EditText) findViewById(R.id.start_date);
        endDate = (EditText) findViewById(R.id.end_date);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(UpdateTask.this);
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(UpdateTask.this);
        findViewById(R.id.emp_layout).setVisibility(View.GONE);


    }
}
