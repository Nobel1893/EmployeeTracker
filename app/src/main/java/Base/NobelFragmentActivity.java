package Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.nobel.employeetracker.MyApplication;
import com.nobel.employeetracker.R;

import Networking.Connector;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Admin on 9/11/2017.
 */

public class NobelFragmentActivity extends FragmentActivity {
    public NobelFragmentActivity activity;
    public Connector connector;
    private SweetAlertDialog pDialog;


    public NobelFragmentActivity() {
        activity = this;

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connector = new Connector(MyApplication.requestQueue, MyApplication.APIURL);
    }


    private void PrepareLoadingDialogue() {


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

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();


    }


    public void ShowMessage(String Message) {
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
