package com.core.utility.base;

/**
 * Created by Le Duc Chung on 2017-10-13.
 * on project 'TrackingProject'
 */

public interface BaseMethodOriginal {
    void saveStringSharedPreference(String key, String value);

    String getStringSharedPreference(String key);

    boolean isServiceRunning(Class<?> serviceClass);
}
