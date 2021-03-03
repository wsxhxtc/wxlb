package com.project.jsproject.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "训练信息")
public class DataInfo {
    @SmartColumn(id = 1, name = "用户")
    private String name;
    @SmartColumn(id = 2, name = "引体向上(个数)")
    private String ytcount;
    @SmartColumn(id = 3, name = "屈臂伸")
    private String qbcount;
    @SmartColumn(id = 4, name = "400障碍")
    private String forthm;
    @SmartColumn(id = 5, name = "3000米跑")
    private String thirdthm;
}
