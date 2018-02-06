package com.example.mqtest.scanqrcode.Dao;

import android.util.Log;

import com.example.mqtest.scanqrcode.Data.DaoSession;
import com.example.mqtest.scanqrcode.Data.DroneMsgBean;
import com.example.mqtest.scanqrcode.Data.DroneMsgBeanDao;
import com.example.mqtest.scanqrcode.Data.ToastShow;
import com.example.mqtest.scanqrcode.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class UserGreenDao {
    DaoSession daoSession;
    DroneMsgBeanDao msgBeanDao;

    public UserGreenDao() {
        daoSession = MyApplication.getDaoSession();
        msgBeanDao = daoSession.getDroneMsgBeanDao();
    }

    //添加数据至数据库中
    public boolean addDateToSQL(String drone_id) {

        DroneMsgBean droneMsgBean = new DroneMsgBean();
        droneMsgBean.setDrone_id(drone_id);
        droneMsgBean.setDrone_activation("否");
        try {
            msgBeanDao.insert(droneMsgBean);
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("插入失败");
            return false;
        }
        ToastShow.show("插入成功");
        return true;
    }

    //修改版本号信息
    public void updateSQL(String drone_id, String drone_color, String drone_weight) {

        List<DroneMsgBean> list = msgBeanDao.queryBuilder()
                .where(DroneMsgBeanDao.Properties.Drone_id.eq(drone_id))
                .build()
                .list();
        DroneMsgBean droneMsgBean = list.get(0);
        droneMsgBean.setDrone_id(drone_id);
        droneMsgBean.setDrone_activation("是");
        droneMsgBean.setDrone_color(drone_color);
        droneMsgBean.setDrone_weight(drone_weight);
        try {
            msgBeanDao.update(droneMsgBean);
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("修改失败");

        }

    }

    //查询数据库
    public DroneMsgBean querySQL(String drone_id) {
        List<DroneMsgBean> list = null;
        try {
            list = msgBeanDao.queryBuilder()
                    .where(DroneMsgBeanDao.Properties.Drone_id.eq(drone_id))
                    .build()
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    //删除数据
    public boolean DeleteSQLByID(String drone_id) {
        List<DroneMsgBean> list = msgBeanDao.queryBuilder()
                .where(DroneMsgBeanDao.Properties.Drone_id.eq(drone_id))
                .build()
                .list();
        if (list.size() == 0) {
            ToastShow.show("没有此飞控信息");
            return false;
        }
        try {
            DroneMsgBean droneMsgBean = list.get(0);
            msgBeanDao.delete(droneMsgBean);
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("删除失败");
            return false;
        }
        ToastShow.show("删除成功");
        return true;
    }

    public void DeleteSQLAll() {
        try {
            msgBeanDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
            ToastShow.show("删除失败");
        }
        ToastShow.show("删除成功");
    }

}
