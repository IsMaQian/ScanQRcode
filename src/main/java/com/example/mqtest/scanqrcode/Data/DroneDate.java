package com.example.mqtest.scanqrcode.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2018/2/3.
 */

public class DroneDate extends RealmObject {
    @PrimaryKey
    private String drone_id;
    private String drone_activation;
    private String drone_color;
    private String drone_weight;

    public String getDrone_id() {
        return drone_id;
    }

    public void setDrone_id(String drone_id) {
        this.drone_id = drone_id;
    }

    public String getDrone_activation() {
        return drone_activation;
    }

    public void setDrone_activation(String drone_activation) {
        this.drone_activation = drone_activation;
    }

    public String getDrone_color() {
        return drone_color;
    }

    public void setDrone_color(String drone_color) {
        this.drone_color = drone_color;
    }

    public String getDrone_weight() {
        return drone_weight;
    }

    public void setDrone_weight(String drone_weight) {
        this.drone_weight = drone_weight;
    }
}
