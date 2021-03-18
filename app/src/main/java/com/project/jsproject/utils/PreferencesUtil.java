package com.project.jsproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

  private static final String APP_PREFERENCES_NAME = "app_setting";

  public static void putString(Context context, String key, String value) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    preferences.edit().putString(key, value).apply();
  }

  public static void putInt(Context context, String key, int value) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    preferences.edit().putInt(key, value).apply();
  }

  public static void putBoolean(Context context, String key, boolean value) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    preferences.edit().putBoolean(key, value).apply();
  }

  public static String getString(Context context, String key, String defaultValue) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    return preferences.getString(key, defaultValue);
  }

  public static int getInt(Context context, String key, int defaultValue) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    return preferences.getInt(key, defaultValue);
  }

  public static boolean getBoolean(Context context, String key, boolean defaultValue) {
    SharedPreferences preferences = context
        .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    return preferences.getBoolean(key, defaultValue);
  }
}
