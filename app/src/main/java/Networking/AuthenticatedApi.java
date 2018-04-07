package Networking;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 8/30/2017.
 */

public class AuthenticatedApi extends Request<NetworkResponse> {

    static AuthenticatedApi currentCall;
    Map<String, String> headers, fparams;
    private Response.Listener<NetworkResponse> mListener;
    private int mStatusCode;
    /**
     * the parse charset.
     */
    private String charset = null;

    /**
     * Creates a new request with the given method.
     *
     * @param method        the request {@link Method} to use
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public AuthenticatedApi(int method, String url, Response.Listener<NetworkResponse> listener,
                            Response.ErrorListener errorListener,
                            Map<String, String> headers,
                            Map<String, String> fparams) {
        super(method, url, errorListener);
        mListener = listener;
        this.fparams = fparams;
        this.headers = headers;
        currentCall = this;

        setCharset("UTF-8");
    }

    /**
     * Creates a new GET request.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public AuthenticatedApi(String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener,
                            Map<String, String> headers,
                            Map<String, String> fparams) {
        this(Method.GET, url, listener, errorListener, headers, fparams);
    }

    /**
     * Creates a new GET request with the given Charset.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public AuthenticatedApi(String url, String charset,
                            Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener
            , HashMap<String, String> headers,
                            HashMap<String, String> fparams) {
        this(Method.GET, url, listener, errorListener, headers, fparams);
        this.charset = charset;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {

        try {
            return Response.success(

                    response,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }


    /**
     * @return the Parse Charset Encoding
     */
    public String getCharset() {
        return charset;
    }

    /**
     * set the Parse Charset Encoding
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    protected Map<String, String> getParams() {
        return fparams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    public static class DefaultResponeHandler implements Response.Listener<NetworkResponse>, Response.ErrorListener {

        ServiceCallback callback;
        Connector connector;
        int callAttempt = 0;
        Abstract.Response responseobject;

        public DefaultResponeHandler(ServiceCallback callback, Connector connector) {
            this.connector = connector;
            this.callback = callback;
        }


        @Override
        public void onResponse(NetworkResponse responses) {
            // process your response here
//                         resp=response;

            String response = new String(responses.data);
            Abstract.Response response1;
            response1=new Abstract.Response();
            Log.e("response",response);
            try {
                JSONArray arr=new JSONArray(response);
                JSONObject object=arr.getJSONObject(0);
                String st=object.opt("status").toString();
                response1.setStatus(object.opt("status").toString());
                   Object mssg=object.opt("msg");
                response1.setMessage(mssg.toString());

                JSONObject data=null;


                if(st.equalsIgnoreCase("success")){
                    data=arr.getJSONObject(1);
                    response1.setAlldata(arr);
                }
                if(data!=null)
                    response1.setData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (response1.getStatus().equalsIgnoreCase("success")){
                callback.onSuccess(response1);
            }
            else {
                callback.onFail(response1);
            }

        }


        @Override
        public void onErrorResponse(VolleyError error) {


            NetworkResponse networkResponse = error.networkResponse;
            String errorMessage = "Unknown error";
            if (networkResponse == null) {
                Log.e("error","Service error "+error.getClass());
                if (error.getClass().equals(TimeoutError.class)) {
                    errorMessage = "Request timeout";
                } else if (error.getClass().equals(NoConnectionError.class)) {
                    errorMessage = "Failed to connect server";
                }else {
                    errorMessage=error.getMessage();
                }
                Abstract.Response responses = new Abstract.Response();
                responses.setMessage(errorMessage);
                responses.setStatus("fail");
                callback.onFail(responses);

            } else {
                Log.e("error","Service error "+networkResponse.statusCode);

                String result = new String(networkResponse.data);
                Log.e("error in service",networkResponse.statusCode+"");
                if (networkResponse.statusCode == 404) {
                    errorMessage = "Resource not found";
                } else if (networkResponse.statusCode == 401) {

                    Abstract.Response response = JsonParser.fromJsonString(result, Abstract.Response.class);
                    callback.onFail(response);
                    errorMessage = "Please login again";
                    return;
                } else if (networkResponse.statusCode == 400) {
                    Log.e("error response", result);
                    Abstract.Response response = JsonParser.fromJsonString(result, Abstract.Response.class);
                    errorMessage = "Check your inputs";
                    callback.onFail(response);
                    return;
                } else if (networkResponse.statusCode == 500) {
                    errorMessage = " Something is getting wrong";
                }

                Abstract.Response responses = new Abstract.Response();
                responses.setMessage(errorMessage);
                responses.setStatus("fail");
                callback.onFail(responses);


            }
        }

    }
}
