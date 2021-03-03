package com.project.jsproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ActionEntity {

    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String time;
    private String plan;
    private String name;
    private String title;
    private String contuinetime;
    
    @Generated(hash = 1703650111)
    public ActionEntity(Long id, String time, String plan, String name,
            String title, String contuinetime) {
        this.id = id;
        this.time = time;
        this.plan = plan;
        this.name = name;
        this.title = title;
        this.contuinetime = contuinetime;
    }

    @Generated(hash = 2064731073)
    public ActionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContuinetime() {
        return this.contuinetime;
    }

    public void setContuinetime(String contuinetime) {
        this.contuinetime = contuinetime;
    }
}
