package com.project.jsproject;

import java.util.HashMap;
import java.util.Map;

public class Commons {

  public static final int CATEGORY_STJ = 1; // 三头肌
  public static final int CATEGORY_XJ = 1 << 1; // 胸肌
  public static final int CATEGORY_DT = 1 << 2; // 大腿

  public static final int[] CATEGORY_RES = {
      R.mipmap.muscle_cb, // 三头肌
      R.mipmap.muscle_ea, // 胸肌
      R.mipmap.muscle_ga  // 大腿
  };

  // 三头肌训练科目
  public static final String[] STJ_TASKS = {
      "坐姿哑铃屈臂伸",
      "仰卧屈臂伸",
      "俯身支撑屈臂伸"
  };

  // 胸肌训练科目
  public static final String[] XJ_TASKS = {
      "哑铃卧推",
      "下斜哑铃飞鸟",
      "哑铃仰卧屈臂伸"
  };

  // 大腿训练科目
  public static final String[] DT_TASKS = {
      "哑铃深蹲",
      "哑铃弓步蹲",
      "哑铃前弓步"
  };

  public static final int[][] TASK_FRAMES = {
      {
          R.raw.aaa1,
          R.raw.aaa2,
          R.raw.aaa3
      },
      {
          R.raw.aab1,
          R.raw.aab2,
          R.raw.aab3
      },
      {
          R.raw.had1,
          R.raw.had2,
          R.raw.had3
      }
  };

  public static final Map<Integer, String[]> allTasksMap = new HashMap<>(9);

  static {
    allTasksMap.put(CATEGORY_STJ, STJ_TASKS);
    allTasksMap.put(CATEGORY_XJ, XJ_TASKS);
    allTasksMap.put(CATEGORY_DT, DT_TASKS);
  }

}
