package Abstract;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 10/8/2017.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */

public class Response {

    String status;
    String msg;
    JSONObject data;
    JSONArray Alldata;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONArray getAlldata() {
        return Alldata;
    }

    public void setAlldata(JSONArray alldata) {
        Alldata = alldata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
