package com.core.utility.utils.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Le Duc Chung on 2018-07-02.
 * on project 'Buum'
 */
@SuppressWarnings("unused")
public class SharedPreferenceHelper {
    @SuppressLint("StaticFieldLeak")
    private static SharedPreferencesUtils mSharedPreferencesUtils;

    public static void initialization(Context context, String appName) {
        mSharedPreferencesUtils = SharedPreferencesUtils.newInstance(context, appName);
    }

    public static void saveString(String key, String value) {
        mSharedPreferencesUtils.saveDataString(key, value);
    }

    public static void saveBoolean(String key, boolean value) {
        mSharedPreferencesUtils.saveBoolean(key, value);
    }

    public static void saveInteger(String key, int value) {
        mSharedPreferencesUtils.saveInteger(key, value);
    }

    public static void saveLong(String key, long value) {
        mSharedPreferencesUtils.saveLong(key, value);
    }

    public static void saveJson(String key, JSONObject value) {
        mSharedPreferencesUtils.saveDataJSON(key, value);
    }

    public static void saveJson(String key, JSONArray value) {
        mSharedPreferencesUtils.saveDataJSONArray(key, value);
    }

    //==============================================================================================

    public static String getString(String key) {
        return mSharedPreferencesUtils.getStringFromSharedPreferences(key);
    }

    public static int getInteger(String key) {
        return mSharedPreferencesUtils.getIntegerFromSharedPreferences(key);
    }

    public static long getLong(String key) {
        return mSharedPreferencesUtils.getLongFromSharedPreferences(key);
    }

    public static boolean getBoolean(String key) {
        return mSharedPreferencesUtils.getBooleanFromSharedPreferences(key);
    }

    public static JSONObject getJSONObject(String key) {
        return mSharedPreferencesUtils.getJSONFromSharedPreferences(key);
    }

    public static JSONArray getJSONArray(String key) {
        return mSharedPreferencesUtils.getJSONArrayFromSharedPreferences(key);
    }

    //==============================================================================================

    public static String getString(String key, String def) {
        return mSharedPreferencesUtils.getStringFromSharedPreferences(key, def);
    }

    public static int getInteger(String key, int def) {
        return mSharedPreferencesUtils.getIntegerFromSharedPreferences(key, def);
    }

    public static long getLong(String key, long def) {
        return mSharedPreferencesUtils.getLongFromSharedPreferences(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return mSharedPreferencesUtils.getBooleanFromSharedPreferences(key, def);
    }

    public static JSONObject getJSONObject(String key, JSONObject def) {
        return mSharedPreferencesUtils.getJSONFromSharedPreferences(key, def);
    }

    public static JSONArray getJSONArray(String key, JSONArray def) {
        return mSharedPreferencesUtils.getJSONArrayFromSharedPreferences(key, def);
    }

    public static void cleanData() {
        mSharedPreferencesUtils.clean();
    }
}
