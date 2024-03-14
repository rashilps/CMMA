package com.example.cmma;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfigAdmin     {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfigAdmin(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.login_preference_admin),Context.MODE_PRIVATE);

    }

    public void writeLoginStatusAdmin(boolean status){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference_admin),status);
        editor.commit();
    }

    public boolean readLoginStatusAdmin(){

        boolean status=false;

        status=sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference_admin),false);
        return status;

    }
}
