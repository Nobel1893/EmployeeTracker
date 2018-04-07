package Base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import Networking.Connector;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 9/11/2017.
 */

public class NobelFragment extends Fragment {

    public NobelActivity activity;
    public Context context;
    protected Connector connector;
    SweetAlertDialog dialog;
    boolean isvisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (NobelActivity) getActivity();
        this.connector = activity.connector;
//        PerfomLoginifNotLogin();

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isvisible = isVisibleToUser;
    }


    public SweetAlertDialog ShowLoadingDialogue() {
        Log.e("fragment", "showLoading");

        if (activity == null) return null;

        else {
            if (isvisible) {
                dialog = activity.GetLoadingDialogue();

                return dialog;
            }
            return null;
        }

    }

    public void HideLoadingDialoge() {

        if (activity == null) return;

        else {
            Log.e("fragment", "HideLoading");
            activity.HideLoadingDialogue();
        }
    }



    public void ShowMessage(String Message) {
        if (activity != null)
            activity.ShowMessage(Message);
    }


    public void ShowDialogeConfirmation(String Title, String message, String confirmText, SweetAlertDialog.OnSweetClickListener confirmAction) {

        activity.ShowDialogeConfirmation(Title, message, confirmText, confirmAction);
    }


}
