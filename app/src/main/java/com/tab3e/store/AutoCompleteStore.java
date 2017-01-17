package com.tab3e.store;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by mostafa_anter on 1/17/17.
 */

public class AutoCompleteStore {
    private static final String PREFKEY = "AutoCompleteStore";
    private SharedPreferences favoritePrefs;

    public AutoCompleteStore(Context context) {
        favoritePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public List<String> findAll() {

        Map<String, ?> notesMap = favoritePrefs.getAll();

        SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());
        List<String> dataList = new ArrayList<>();
        for (String key : keys) {
            dataList.add(key);
        }

        return dataList;
    }

    public boolean update(String key) {

        SharedPreferences.Editor editor = favoritePrefs.edit();
        editor.putString(key, key);
        editor.apply();
        return true;
    }

    public void clearPreference(){
        SharedPreferences.Editor editor = favoritePrefs.edit();
        editor.clear().apply();
    }
}
