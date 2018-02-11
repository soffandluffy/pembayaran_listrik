package com.soffandluffy.pembayaranlistrik;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SoffanDLuffy on 2/11/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
