package Networking;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import java.util.HashMap;
import java.util.Map;

import Abstract.Employee;
import Abstract.Task;


/**
 * Created by MOhamed Nabil
 * Email m.nabil.fci2015@gmail.com
 * Mobile num 01117576576 / 01010753727 on 1/31/2016.
 */

public class Connector {
    private static String GET = "GET";
    private static String POST = "POST";
    String Lang;
    private RequestQueue queue;
    private String APIURL;

    public Connector(RequestQueue queue,String ApiUrl) {
        this.queue = queue;
        this.APIURL = ApiUrl;
    }




    private void SendRequest(String api, String Method, Map<String, String> fparams, Map<String, String> headers,
                             ServiceCallback callback) {


        AuthenticatedApi.DefaultResponeHandler handler;

        int finalMethod = Request.Method.GET;
        if (Method.equals(GET)) {
            finalMethod = Request.Method.GET;

        } else if (Method.equals(POST)) {
            finalMethod = Request.Method.POST;
        }
        Log.e("test", api);


        handler = new AuthenticatedApi.DefaultResponeHandler(callback, this);
        AuthenticatedApi Request = new AuthenticatedApi(finalMethod, api, handler, handler, headers, fparams);


        queue.add(Request);

    }

    private void MultiPartRequest(String api, String Method,
                                  final Map<String, String> fparams,
                                  final Map<String, String> headers,
                                  final Map<String, DataPart> dataParams,
                                  ServiceCallback callback) {
        AuthenticatedApi.DefaultResponeHandler handler;

        int finalMethod = Request.Method.GET;
        if (Method.equals(GET)) {
            finalMethod = Request.Method.GET;

        } else if (Method.equals(POST)) {
            finalMethod = Request.Method.POST;
        }
        Log.e("test", api);


        handler = new AuthenticatedApi.DefaultResponeHandler(callback, this);
//        AuthenticatedApi Request = new AuthenticatedApi(finalMethod, api, handler, handler, headers, fparams);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(finalMethod, api, handler, handler) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                return fparams;
            }

            @Override
            protected Map<String, DataPart> getByteData() {

                return dataParams;
            }
        };

        queue.add(multipartRequest);

    }

    public void LoginHR(String email, String password, ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        SendRequest(APIURL + "logadmin", Connector.POST, params, headers, callback);
    }

    public void LoginEMP(String email, String password,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        SendRequest(APIURL + "login", Connector.POST, params, headers, callback);
    }

    public void Logout(String email, String password, HashMap<String,DataPart>imgs,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        MultiPartRequest(APIURL + "login", Connector.POST, params, headers,imgs, callback);
    }
    public void SendLoginUpdates(String id,String date,double lat,double lng, HashMap<String,DataPart>imgs,ServiceCallback callback){
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id",id);
        params.put("lat",""+lat);
        params.put("lng",""+lng);
        params.put("type","login");
        params.put("day_date",date);

        MultiPartRequest(APIURL + "add_google", Connector.POST, params, headers,imgs, callback);
    }public void SendLogoutUpdates(String id,String date,double lat,double lng, HashMap<String,DataPart>imgs,ServiceCallback callback){
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id",id);
        params.put("lat",""+lat);
        params.put("lng",""+lng);
        params.put("type","logout");
        params.put("day_date",date);

        MultiPartRequest(APIURL + "add_google", Connector.POST, params, headers,imgs, callback);
    }
    //////////////////////////////////////////////////////////////////////////////////
    public void AddDepartment (String name, ServiceCallback callback) {
        //,,,,,,,,,,,
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("name",name);
        SendRequest(APIURL + "add_section", Connector.POST, params, headers, callback);
    }
    public void GetDepartments ( ServiceCallback callback) {
        //,,,,,,,,,,,
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "get_allsection", Connector.GET, params, headers, callback);
    }
    public void DelDepartment ( String id,ServiceCallback callback) {
        //,,,,,,,,,,,
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "del_section?id="+id, Connector.GET, params, headers, callback);
    }
    /////////////////////////////////////////////////////////////////////////
    public void UpdateEmployee(String id, Employee e,HashMap<String,DataPart>imgs, ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id",id);
        params.put("id_employee",e.getId_employee());
        params.put("section",e.getSection());
        params.put("name",e.getName());
        params.put("email",e.getEmail());
        params.put("password",e.getPassword());
        params.put("mobile",e.getMobile());
        params.put("address",e.getAddress());
        params.put("salary",e.getSalary());
        params.put("rank",e.getRank());
        params.put("w_date",e.getW_date());
        params.put("b_dat",e.getB_date());
        params.put("pic",e.getName());
        // params.put("pic",password);
        MultiPartRequest(APIURL + "update_emp", Connector.POST, params, headers,imgs, callback);
    }
    public void AddEmployee(Employee e,HashMap<String,DataPart> files, ServiceCallback callback) {
        //,,,,,,,,,,,
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id_employee",e.getId_employee());
        params.put("section",e.getSection());
        params.put("name",e.getName());
        params.put("email",e.getEmail());
        params.put("password",e.getPassword());
        params.put("mobile",e.getMobile());
        params.put("address",e.getAddress());
        params.put("salary",e.getSalary());
        params.put("rank",e.getRank());
        params.put("w_date",e.getW_date());
        params.put("b_dat",e.getB_date());
        params.put("pic",e.getName());
        // params.put("pic",password);
        MultiPartRequest(APIURL + "add_emp", Connector.POST, params, headers, files,callback);
    }
    public void getAllEmployees(ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "getallemp", Connector.GET, params, headers, callback);
    }
    public void DeleteEmployee(String id,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id",id);
        SendRequest(APIURL + "del_emp?id="+id, Connector.GET, params, headers, callback);
    }

    /////////////////////////////////////////////////////////////////////////
    public void getUnFinishedTasksForAdmin(ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "showadmintasks?status=NO", Connector.GET, params, headers, callback);
    }
    public void getFinishedTasksForAdmin(ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "showadmintasks?status=YES", Connector.GET, params, headers, callback);
    }
    public void AddTask(String id,Task task, ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        //   id,,,,,
        params.put("id",id);
        params.put("title",task.getTitle());
        params.put("task",task.getTask());
        params.put("s_date",task.getS_date());
        params.put("e_date",task.getE_date());
        params.put("period",task.getPeriod());
        SendRequest(APIURL + "add_task", Connector.POST, params, headers, callback);


    }
    public void UpdateTask(String id,Task task, ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("ID",id);
        params.put("title",task.getTitle());
        params.put("task",task.getTask());
        params.put("s_date",task.getS_date());
        params.put("e_date",task.getE_date());
        params.put("period",task.getPeriod());
        SendRequest(APIURL + "add_task", Connector.POST, params, headers, callback);


    }
    public void DelTask ( String id,ServiceCallback callback) {
        //,,,,,,,,,,,
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "del_task?id="+id, Connector.GET, params, headers, callback);
    }


    ////////////////////////////////////////////////

    public void getFinishedTasksForEmployee(String id,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "showalltask?status=YES&id_employee="+id, Connector.GET, params, headers, callback);
    }
    public void getUnFinishedTasksForEmployee(String id,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "showalltask?status=NO&id_employee="+id, Connector.GET, params, headers, callback);
    }
    public void getEmployeeData(String id,ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "showEmpData?id_employee="+id, Connector.GET, params, headers, callback);
    }

    /////////////////////////////////////////////////////
    public void FinishTask(String id, ServiceCallback callback) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        SendRequest(APIURL + "update_task?status=YES&id="+id, Connector.GET, params, headers, callback);

    }

    public void SendLocationUpdates(String id,String date,double lat,double lng,ServiceCallback callback){
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        params.put("id",id);
        params.put("lat",""+lat);
        params.put("lng",""+lng);
        params.put("pic","");
        params.put("type","in_work");
        params.put("day_date",date);

        SendRequest(APIURL + "add_google", Connector.POST, params, headers, callback);

    }
}