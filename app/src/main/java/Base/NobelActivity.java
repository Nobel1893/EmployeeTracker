package Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Networking.Connector;
import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Admin on 9/11/2017.
 */

public class NobelActivity extends AppCompatActivity {
    public NobelActivity activity;
    public Connector connector;
    private SweetAlertDialog pDialog;


    public NobelActivity() {
        activity = this;

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




    public void ShowHomeasUpEnabled() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        // toolbar.setNavigationIcon(R.drawable.ic_action_back);
        String title = getIntent().getStringExtra("title");
        if (title != null)
            ((TextView) findViewById(R.id.title)).setText(title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();  // to go back  finish() will do your work.
                //mActionBar.setDisplayHomeAsUpEnabled(true);
                //mActionBar.setDisplayShowHomeEnabled(true);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connector = new Connector(MyApplication.requestQueue, MyApplication.APIURL);
    }


    private void PrepareLoadingDialogue() {


        if (pDialog==null)
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        //  pDialog.setCustomImage(R.mipmap.ic_launcher);
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();


    }


    public SweetAlertDialog GetLoadingDialogue() {

        if (pDialog == null || !pDialog.isShowing())
            PrepareLoadingDialogue();


        return pDialog;

    }

    public void HideLoadingDialogue() {

        if (pDialog != null )
            pDialog.dismiss();


    }


    public void ShowMessage(String Message) {
//        Log.e("message to Show",Message);
        SweetAlertDialog Dialog = new SweetAlertDialog(this)
                .setTitleText(Message);

      /*  Dialog.get
        Button btn = (Button) Dialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));*/
        Dialog.show();

    }


    public void ShowDialogeConfirmation(String Title, String message, String confirmText, SweetAlertDialog.OnSweetClickListener confirmAction) {
        SweetAlertDialog Dialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(Title)
                .setContentText(message)
                .setConfirmText(confirmText)
                .setConfirmClickListener(confirmAction)
                .showCancelButton(true);


      /*  Button btn = (Button) Dialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));*/
        Dialog.show();
    }

    public void ShowDialogeٍSuccessful(String Title, String message, String confirmText, SweetAlertDialog.OnSweetClickListener confirmAction) {
        SweetAlertDialog Dialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(Title)
                .setContentText(message)
                .setConfirmText(confirmText)
                .setConfirmClickListener(confirmAction)
                .showCancelButton(true);


      /*  Button btn = (Button) Dialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        btn.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));*/
        Dialog.show();
    }

}
