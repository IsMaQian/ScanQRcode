package com.example.mqtest.scanqrcode.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/2/5.
 */
@Entity
public class DroneMsgBean {

    @Id(autoincrement = true)
    private Long id;
    @Unique
    @Property(nameInDb = "DRONE_ID")
    private String drone_id;
    @Property(nameInDb = "ACTIVATION")
    private String drone_activation;
    @Property(nameInDb = "DRONE_COLOR")
    private String drone_color;
    @Property(nameInDb = "DRONE_WEIGHT")
    private String drone_weight;

    @Generated(hash = 489924644)
    public DroneMsgBean(Long id, String drone_id, String drone_activation, String drone_color,
            String drone_weight) {
        this.id = id;
        this.drone_id = drone_id;
        this.drone_activation = drone_activation;
        this.drone_color = drone_color;
        this.drone_weight = drone_weight;
    }

    @Generated(hash = 312059673)
    public DroneMsgBean() {
    }

    public String getDrone_id() {
        return this.drone_id;
    }

    public void setDrone_id(String drone_id) {
        this.drone_id = drone_id;
    }

    public String getDrone_activation() {
        return this.drone_activation;
    }

    public void setDrone_activation(String drone_activation) {
        this.drone_activation = drone_activation;
    }

    public String getDrone_color() {
        return this.drone_color;
    }

    public void setDrone_color(String drone_color) {
        this.drone_color = drone_color;
    }

    public String getDrone_weight() {
        return this.drone_weight;
    }

    public void setDrone_weight(String drone_weight) {
        this.drone_weight = drone_weight;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    
}
