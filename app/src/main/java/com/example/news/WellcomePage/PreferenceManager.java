package com.example.news.WellcomePage;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    public PreferenceManager(Context context){
        this.context=context;
        pref=context.getSharedPreferences("first",0);
        editor=pref.edit();
    }
    public void setFirstLunch(boolean isFirst){
        editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean isFirstTime(){
        return pref.getBoolean("check",true);
    }
}
