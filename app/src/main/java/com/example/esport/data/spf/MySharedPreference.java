package com.example.esport.data.spf;

import android.content.SharedPreferences;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Author: created by MarkYoung on 4/01/2019 15:37
 */
@Singleton
public class MySharedPreference {

    private SharedPreferences sharedPreferences;

    @Inject
    public MySharedPreference(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void putString(String key, String value){
        sharedPreferences.edit().putString(key,value).apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }

    public Map<String,?> getAll(){
        return sharedPreferences.getAll();
    }
}
