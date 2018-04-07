package Networking;

import Abstract.Response;

/**
 * Created by Admin on 8/17/2017.
 */

public abstract class ServiceCallback {

    public abstract void onSuccess(Response Response);

    public String onFail(Response response) {

        if (response != null)
            return response.getMessage();
        else return "error";
    }
}
