package com.dev.mooohamed.moviesmvp.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;


public class Prefs {

    static final String USER = "user";
    SharedPreferences sharedPreferences;
    Gson gson;

    public Prefs(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public void setUser(String user){
        sharedPreferences.edit().putString(USER,user).apply();
    }

    public String getUser(){
        return sharedPreferences.getString(USER,"");
    }

    public void clearUser(){

        sharedPreferences.edit().clear().apply();
    }

}
