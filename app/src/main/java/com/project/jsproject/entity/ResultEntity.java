package com.project.jsproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ResultEntity {

    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String time;
    private String name;
    private int contuinetime;
    @Generated(hash = 1693086378)
    public ResultEntity(Long id, String time, String name, int contuinetime) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.contuinetime = contuinetime;
    }
    @Generated(hash = 369396957)
    public ResultEntity() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getContuinetime() {
        return this.contuinetime;
    }
    public void setContuinetime(int contuinetime) {
        this.contuinetime = contuinetime;
    }

}
