package Networking;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by it on 1/11/2017.
 */
public class JsonParser {

    private static JsonParser Parser;
    private static Gson gson;

    public static JsonParser getInstance() {
        if (Parser == null) {
            Parser = new JsonParser();
            gson = new Gson();
            return Parser;
        }
        return Parser;

    }


    public static <T> T fromJsonString(String Json, Type TypeOfObject) {
        T ob = null;
        if (Json == null || Json.equals("")) return ob;

        return gson.fromJson(Json, TypeOfObject);

    }

    public static <T> ArrayList<T> fromJsonArray(String json, Class<T[]> clazz) {

        if (json == null || json.equals("")) return new ArrayList<T>();
        if (json.charAt(0) != '[') return new ArrayList<>();
        Log.e("json", json);
        T[] jsonToObject = gson.fromJson(json, clazz);
        return new ArrayList<T>(Arrays.asList(jsonToObject));

    }



}
