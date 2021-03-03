package com.project.jsproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllPlanEntity {

    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String time; //20200101
    private String plan; //11
    private String frame;   //图
    private boolean checked=false;

    @Generated(hash = 507317985)
    public AllPlanEntity() {
    }
    @Generated(hash = 1932810106)
    public AllPlanEntity(Long id, String time, String plan, String frame,
            boolean checked) {
        this.id = id;
        this.time = time;
        this.plan = plan;
        this.frame = frame;
        this.checked = checked;
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

    public boolean getChecked() {
        return this.checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getFrame() {
        return this.frame;
    }
    public void setFrame(String frame) {
        this.frame = frame;
    }

}
