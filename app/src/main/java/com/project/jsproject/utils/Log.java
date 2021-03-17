package com.project.jsproject.utils;

import com.project.jsproject.BuildConfig;

public class Log {

  private static final String TAG = Log.class.getSimpleName();
  private static boolean DEBUG = BuildConfig.DEBUG;

  public static void v(String msg) {
    if (DEBUG) {
      android.util.Log.v(TAG, msg);
    }
  }

  public static void i(String msg) {
    if (DEBUG) {
      android.util.Log.i(TAG, msg);
    }
  }

  public static void d(String msg) {
    if (DEBUG) {
      android.util.Log.d(TAG, msg);
    }
  }

  public static void e(String msg) {
    if (DEBUG) {
      android.util.Log.e(TAG, msg);
    }
  }

}
