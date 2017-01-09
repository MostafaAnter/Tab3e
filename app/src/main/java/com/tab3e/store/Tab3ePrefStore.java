package com.tab3e.store;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mostafa_anter on 9/26/16.
 */

public class Tab3ePrefStore {
    private static final String PREFKEY = "Tab3ePreferencesStore";
    private SharedPreferences tab3ePreferences;

    public Tab3ePrefStore(Context context){
        tab3ePreferences = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public void clearPreference(){
        SharedPreferences.Editor editor = tab3ePreferences.edit();
        editor.clear().apply();
    }

    public void addPreference(String key, String value){
        SharedPreferences.Editor editor = tab3ePreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void addPreference(String key, int value){
        SharedPreferences.Editor editor = tab3ePreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void removePreference(String key){
        SharedPreferences.Editor editor = tab3ePreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getPreferenceValue(String key){
        return tab3ePreferences.getString(key, "");
    }

    public int getIntPreferenceValue(String key){
        return tab3ePreferences.getInt(key, 0);
    }
}
