package pl.design.mrn.matned.dogmanagementapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GsonFormatter {

    public static ArrayList getList(String gsonStr){
        Gson gson = new Gson();
        return gson.fromJson(gsonStr, new TypeToken<ArrayList>() {
        }.getType());
    }

    public static String make(List list){
        return new Gson().toJson(list);
    }

}
