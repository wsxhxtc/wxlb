package com.project.jsproject.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.project.jsproject.Commons;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.entity.PlanTask;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * 获取md5
   */
  public static String getMD5(String content) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(content.getBytes());
      return getHashString(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String getHashString(MessageDigest digest) {
    StringBuilder builder = new StringBuilder();
    for (byte b : digest.digest()) {
      builder.append(Integer.toHexString((b >> 4) & 0xf));
      builder.append(Integer.toHexString(b & 0xf));
    }
    return builder.toString();
  }

  public static String getFormatDate(long timestamp) {
    return DATE_FORMAT.format(new Date(timestamp));
  }

  public static Date getDate(int year, int month, int date) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DATE, date);
    return calendar.getTime();
  }

  public static Date getDateStart(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  public static Date getDateEnd(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    return calendar.getTime();
  }

  public static int getDaysByYearMonth(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DATE, 1);
    calendar.roll(Calendar.DATE, -1);
    return calendar.get(Calendar.DATE);
  }

  public static int[] getYearMonthDateArray(long timestamp) {
    int[] result = new int[3];
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timestamp);
    result[0] = calendar.get(Calendar.YEAR);
    result[1] = calendar.get(Calendar.MONTH) + 1;
    result[2] = calendar.get(Calendar.DATE);
    return result;
  }

  /**
   * 获取某月的第一天
   */
  public static Date getFirstDateOfMonth(int year, int month) {
    return getDate(year, month, 1);
  }

  /**
   * 获取某月的最后一天
   */
  public static Date getLastDateOfMonth(int year, int month) {
    return getDate(year, month, getDaysByYearMonth(year, month));
  }

  public static boolean isContainCategory(int categories, int categoryMask) {
    return (categories & categoryMask) == categoryMask;
  }

  public static String getCategoryText(int category) {
    if (category == Commons.CATEGORY_STJ) {
      return "三头肌";
    }
    if (category == Commons.CATEGORY_XJ) {
      return "胸肌";
    }
    if (category == Commons.CATEGORY_DT) {
      return "大腿";
    }
    return "其他";
  }

  public static List<PlanTask> getPlanAllTasks(@NonNull ActionPlan plan) {
    List<PlanTask> list = new ArrayList<>();
    if (isContainCategory(plan.categories, Commons.CATEGORY_STJ)) {
      list.addAll(getCategoryTasks(plan.id, Commons.CATEGORY_STJ, Commons.STJ_TASKS));
    }
    if (isContainCategory(plan.categories, Commons.CATEGORY_XJ)) {
      list.addAll(getCategoryTasks(plan.id, Commons.CATEGORY_XJ, Commons.XJ_TASKS));
    }
    if (isContainCategory(plan.categories, Commons.CATEGORY_DT)) {
      list.addAll(getCategoryTasks(plan.id, Commons.CATEGORY_DT, Commons.DT_TASKS));
    }
    return list;
  }

  public static String getTaskTitle(@NonNull PlanTask task) {
    return Commons.allTasksMap.get(task.category)[task.subCategory];
  }

  private static List<PlanTask> getCategoryTasks(long planId, int category, String[] tasks) {
    List<PlanTask> list = new ArrayList<>();
    for (int i = 0; i < tasks.length; i++) {
      PlanTask task = new PlanTask();
      task.id = (category << 16) | i;
      task.planId = planId;
      task.category = category;
      task.subCategory = i;
      list.add(task);
    }
    return list;
  }

  public static <T> List<T> filter(List<T> originList, @NonNull Predicate<T> filter) {
    List<T> result = new ArrayList<>();
    try {
      for (T item : originList) {
        if (filter.test(item)) {
          result.add(item);
        }
      }
    } catch (Exception e) {
      Log.e(e.getMessage());
    }
    return result;
  }

  public static <T> List<List<T>> groupBy(List<T> originList,
      @NonNull Function<T, String> classifier) {
    List<List<T>> result = new ArrayList<>();
    Map<String, List<T>> map = new HashMap<>();
    try {
      for (T item : originList) {
        String key = classifier.apply(item);
        List<T> group = map.get(key);
        if (group == null) {
          group = new ArrayList<>(1);
          result.add(group);
        }
        group.add(item);
      }
    } catch (Exception e) {
      Log.e(e.getMessage());
    }
    return result;
  }

  public static String getPlanTaskFrame(PlanTask task) {
    if (task.category == Commons.CATEGORY_STJ) {
      return "android.resource://com.project.jsproject/" + Commons.TASK_FRAMES[0][task.subCategory];
    } else if (task.category == Commons.CATEGORY_XJ) {
      return "android.resource://com.project.jsproject/" + Commons.TASK_FRAMES[1][task.subCategory];
    }
    return "android.resource://com.project.jsproject/" + Commons.TASK_FRAMES[2][task.subCategory];
  }

  public static int dp(Context context, int dpValue) {
    float density = context.getResources().getDisplayMetrics().density;
    return (int) (density * dpValue + 0.5);
  }

  public static Drawable drawable(Context context, @DrawableRes int id) {
    return ContextCompat.getDrawable(context, id);
  }
}
