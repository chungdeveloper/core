package com.core.utility.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.core.utility.utils.AppLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ChungLD on 15/09/2016.
 */
@SuppressWarnings("ALL")
public class SharedPreferencesUtils {
    public static final String SHARED_PREFERENCES_FILE = "_shared_preferences";
    private static final String EMPTY_STRING = "";
    private String mName = EMPTY_STRING;
    private Context mContext;
    private static SharedPreferencesUtils sharedPreferencesUtils;
    private AppLog log;

    public static SharedPreferencesUtils getInstance() {
        return sharedPreferencesUtils;
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils(context, "app");
        }
        return sharedPreferencesUtils;
    }

    public static SharedPreferencesUtils newInstance(Context context, String name) {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils(context, name);
        }
        return sharedPreferencesUtils;
    }

    public SharedPreferencesUtils(Context context, String name) {
        mContext = context;
        mName = name;
        log = AppLog.newInstance(SharedPreferencesUtils.class);
    }

    public SharedPreferences handleSharedPreferences() {
        return mContext.getSharedPreferences(mName + SHARED_PREFERENCES_FILE, Context.MODE_MULTI_PROCESS);
    }

    public SharedPreferences handleSharedPreferences(Context context) {
        return context.getSharedPreferences(mName + SHARED_PREFERENCES_FILE, Context.MODE_MULTI_PROCESS);
    }

    public void saveDataString(String key, String values) {
        SharedPreferences.Editor editor = handleSharedPreferences().edit();
        editor.putString(key, values);
        editor.commit();
    }

    public void saveDataString(Context context, String key, String values) {
        SharedPreferences.Editor editor = handleSharedPreferences(context).edit();
        editor.putString(key, values);
        editor.commit();
    }

    public void saveBoolean(String key, Boolean values) {
        SharedPreferences.Editor editor = handleSharedPreferences().edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    public int getIntegerFromSharedPreferences(String key) {
        return handleSharedPreferences().getInt(key, 0);
    }

    public long getLongFromSharedPreferences(String key) {
        return handleSharedPreferences().getLong(key, 0);
    }

    public int getIntegerFromSharedPreferences(String key, int def) {
        return handleSharedPreferences().getInt(key, def);
    }

    public long getLongFromSharedPreferences(String key, long def) {
        return handleSharedPreferences().getLong(key, def);
    }

    public void saveInteger(String key, int values) {
        SharedPreferences.Editor editor = handleSharedPreferences().edit();
        editor.putInt(key, values);
        editor.commit();
    }

    public void saveLong(String key, long values) {
        SharedPreferences.Editor editor = handleSharedPreferences().edit();
        editor.putLong(key, values);
        editor.commit();
    }

    public Boolean getBooleanFromSharedPreferences(String key) {
        return getBooleanFromSharedPreferences(key, false);
    }

    public Boolean getBooleanFromSharedPreferences(String key, boolean defaultValue) {
        return handleSharedPreferences().getBoolean(key, defaultValue);
    }

    public void saveDataJSON(String key, JSONObject object) {
        saveDataString(key, object.toString());
    }

    public void saveDataJSONArray(String key, JSONArray object) {
        saveDataString(key, object.toString());
    }

    public void saveDataJSONArray(Context context, String key, JSONArray object) {
        saveDataString(context, key, object.toString());
    }

    public String getStringFromSharedPreferences(String key) {
        return handleSharedPreferences().getString(key, "");
    }

    public String getStringFromSharedPreferences(String key, String defaultValue) {
        return handleSharedPreferences().getString(key, defaultValue);
    }

    public String getStringFromSharedPreferences(Context context, String key) {
        return handleSharedPreferences(context).getString(key, "");
    }

    public JSONObject getJSONFromSharedPreferences(String key) {
        try {
            String jsonStr = getStringFromSharedPreferences(key);
            if (jsonStr != null && !jsonStr.equals("")) {
                return new JSONObject(jsonStr);
            }
        } catch (JSONException e) {
            log.d("Get JSON From SharedPreferences Fail: " + e.toString());
        }
        return new JSONObject();
    }

    public JSONObject getJSONFromSharedPreferences(String key, JSONObject def) {
        try {
            String jsonStr = getStringFromSharedPreferences(key);
            if (jsonStr != null && !jsonStr.equals("")) {
                return new JSONObject(jsonStr);
            }
        } catch (JSONException e) {
            log.d("Get JSON From SharedPreferences Fail: " + e.toString());
        }
        return def;
    }

    public JSONArray getJSONArrayFromSharedPreferences(String key) {
        try {
            String jsonStr = getStringFromSharedPreferences(key);
            if (jsonStr != null && !jsonStr.equals("")) {
                return new JSONArray(jsonStr);
            }
        } catch (JSONException e) {
            log.d("Get JSON From SharedPreferences Fail: " + e.toString());
        }
        return new JSONArray();
    }

    public JSONArray getJSONArrayFromSharedPreferences(String key, JSONArray def) {
        try {
            String jsonStr = getStringFromSharedPreferences(key);
            if (jsonStr != null && !jsonStr.equals("")) {
                return new JSONArray(jsonStr);
            }
        } catch (JSONException e) {
            log.d("Get JSON From SharedPreferences Fail: " + e.toString());
        }
        return def;
    }

    public JSONArray getJSONArrayFromSharedPreferences(Context context, String key) {
        try {
            String jsonStr = getStringFromSharedPreferences(context, key);
            if (jsonStr != null && !jsonStr.equals("")) {
                return new JSONArray(jsonStr);
            }
        } catch (JSONException e) {
            log.d("Get JSON From SharedPreferences Fail: " + e.toString());
        }
        return new JSONArray();
    }

    public void clean() {
        SharedPreferences.Editor editor = handleSharedPreferences().edit();
        editor.clear().apply();
    }
}
