package com.project.jsproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PlanEntity {

    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String actionid;
    private String time; //20200101
    private String plan; //11
    private String frame;   //图
    private String username ;//用户

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }


    @Generated(hash = 8992154)
    public PlanEntity() {
    }

    @Generated(hash = 172024180)
    public PlanEntity(Long id, String actionid, String time, String plan,
            String frame, String username) {
        this.id = id;
        this.actionid = actionid;
        this.time = time;
        this.plan = plan;
        this.frame = frame;
        this.username = username;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPlan() {
        return this.plan;
    }
    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getActionid() {
        return this.actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }



}
