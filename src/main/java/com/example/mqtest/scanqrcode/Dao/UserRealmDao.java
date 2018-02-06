package com.example.mqtest.scanqrcode.Dao;

import com.example.mqtest.scanqrcode.Data.DroneDate;
import com.example.mqtest.scanqrcode.Data.ToastShow;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2018/2/5.
 */

public class UserRealmDao {
    Realm realm;

    public UserRealmDao(Realm realm) {
        this.realm = realm;
    }

    //Realm中增加数据
    public void addDate(String adronid) {

        final DroneDate droneDate = new DroneDate();
        droneDate.setDrone_id(adronid);
        droneDate.setDrone_activation("否");

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(droneDate);
                    ToastShow.show("飞控编号添加成功");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("飞控编号已存在");
        }
        //realm.beginTransaction();
        //   final DroneDate droneDate = realm.createObject(DroneDate.class, adronid);
//        droneDate.setDrone_id("S1000004");
        //droneDate.setDrone_activation("否");
        //droneDate.setDrone_color("");
        //droneDate.setDrone_weight("");
        //realm.commitTransaction();
        /*RealmAsyncTask task = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final DroneDate droneDate = realm.createObject(DroneDate.class);
                droneDate.setDrone_id("S1000004");
                droneDate.setDrone_activation("否");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
*/
    }

    //查询Realm中数据
    public DroneDate queryDraoneById(String mdroneid) {
        DroneDate droneDate = null;
        try {
            droneDate = realm.where(DroneDate.class).equalTo("drone_id", mdroneid).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("查询失败");
        }

        return droneDate;

    }

    //Realm修改数据
    public void updateDroneDate(String mdroneID,String mdroneColor, String mdroneWeight) {
        DroneDate droneDate = realm.where(DroneDate.class).equalTo("drone_id", mdroneID).findFirst();
        realm.beginTransaction();
        droneDate.setDrone_activation("是");
        droneDate.setDrone_color(mdroneColor);
        droneDate.setDrone_weight(mdroneWeight);
        realm.commitTransaction();

    }

    //删除Realm的数据
    public void deleteAllDate() {
        final RealmResults<DroneDate> droneDates = realm.where(DroneDate.class).findAll();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    droneDates.deleteAllFromRealm();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("删除失败");
        }
        ToastShow.show("删除成功");

    }

}
