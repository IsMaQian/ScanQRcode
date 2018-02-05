package com.example.mqtest.scanqrcode;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static Context mContext;
    public RealmConfiguration configuration;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        configuration = new RealmConfiguration.Builder()
                .name("myRealm.realm")
//                .deleteRealmIfMigrationNeeded()
                .assetFile("aydrone")
                .build();
        Realm.setDefaultConfiguration(configuration);
        mContext = getBaseContext();
        Log.d(TAG, "onCreate: " + getApplicationContext() + "  " + getBaseContext());
    }
}
