package com.tab3e.parser;

import com.tab3e.model.SpinnerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa_anter on 1/9/17.
 */

public class JsonParser {
    public static List<SpinnerModel> parseSpinnerFeed(String feed){
        try {
            JSONArray jsonArray = new JSONArray(feed);
            List<SpinnerModel> modelList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ID = jsonObject.optString("ID");
                String name = jsonObject.optString("name");
                modelList.add(new SpinnerModel(name, ID));
            }
            return modelList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
