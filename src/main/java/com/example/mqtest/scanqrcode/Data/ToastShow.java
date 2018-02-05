package com.example.mqtest.scanqrcode.Data;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.mqtest.scanqrcode.MyApplication;


/**
 * Created by Administrator on 2018/2/5.
 */

public class ToastShow {
    public static void show(Context context, String message) {
        Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }
    public static void show( String message) {
        Toast.makeText(MyApplication.mContext, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
