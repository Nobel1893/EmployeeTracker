package com.nobel.employeetracker.HR;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Abstract.Response;
import Abstract.Task;
import Base.NobelActivity;
import Networking.ServiceCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddTask extends NobelActivity implements View.OnClickListener {

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

        if (isedit&&task!=null){
            taskName.setText(task.getTitle());
            taskDetails.setText(task.getTask());
            time.setText(task.getPeriod());
            startDate.setText(task.getS_date());
            endDate.setText(task.getE_date());
            add.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            if (MyApplication.employees!=null)
            for(int i=0;i<MyApplication.employees.size();i++){
                if (MyApplication.employees.get(i).getID().equals(task.getId_employee())){
                    empName.setSelection(i);
                    break;

                }

            }

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


   String sempName;
   String staskName;
   String staskDetails;
   String stime;
   String sstartDate;
   String sendDate;

    public boolean Validate(){

        sempName=MyApplication.employees.get(empName.getSelectedItemPosition()).getID();
        //empName.getSelectedItem().toString();
        staskName=Validate(taskName);
        staskDetails=Validate(taskDetails);
        stime=Validate(time);
        sstartDate=Validate(startDate);
        sendDate=Validate(endDate);
        if (sempName==null||staskName==null||staskDetails==null||stime==null||sstartDate==null||sendDate==null)
            return false;

        return true;

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
    public static boolean isedit=false;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            if (Validate()){
                 task=new Task();
                 task.setE_date(sendDate);
                 task.setPeriod(stime);
                 task.setTask(staskDetails);
                 task.setS_date(sstartDate);
                 task.setTitle(staskName);

                GetLoadingDialogue();
                connector.AddTask(sempName,task,callback);
            }

        } else if (view.getId() == R.id.edit) {
            if (Validate()){
                Task t=new Task();
                t.setE_date(sendDate);
                t.setPeriod(stime);
                t.setTask(staskDetails);
                t.setS_date(sstartDate);
                t.setTitle(staskName);
                GetLoadingDialogue();
                connector.UpdateTask(task.getID(),t,callback);
            }
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
        add.setOnClickListener(AddTask.this);
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(AddTask.this);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        MyApplication.getEmployeesNames()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        empName.setAdapter(spinnerArrayAdapter1);

    }
}
