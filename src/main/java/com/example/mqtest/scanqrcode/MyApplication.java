package com.example.mqtest.scanqrcode;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.mqtest.scanqrcode.Dao.UserGreenDao;
import com.example.mqtest.scanqrcode.Data.DaoMaster;
import com.example.mqtest.scanqrcode.Data.DaoSession;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static Context mContext;
    public RealmConfiguration configuration;
    public static DaoSession daoSession;
    @Override

    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        configuration = new RealmConfiguration.Builder()
                .name("myRealm.realm")
//                .deleteRealmIfMigrationNeeded()
              //  .assetFile("aydrone")
                .build();
        Realm.setDefaultConfiguration(configuration);
        mContext = getBaseContext();
        Log.d(TAG, "onCreate: " + getApplicationContext() + "  " + getBaseContext());

        //初始化GreenDao
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "aydroneinfo.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        //userGreenDao = new UserGreenDao(daoSession);
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
