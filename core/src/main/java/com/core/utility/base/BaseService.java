package com.core.utility.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.core.utility.utils.AppLog;
import com.core.utility.utils.sharedpreferences.SharedPreferencesUtils;

/**
 * Created by Le Duc Chung on 2017-10-23.
 * on device 'shoot'
 */

@SuppressLint("Registered")
@SuppressWarnings("unused")
public class BaseService extends Service implements BaseMethodOriginal {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final String MESSAGE = "message";
    private static final int SMS_LENGTH = 160;
    public String SENT = "SMS_SENT";
    public String DELIVERED = "SMS_DELIVERED";
    protected AppLog log = AppLog.newInstance(this);
    protected SharedPreferencesUtils mSharedPreferencesUtils;

    private SmsManager sms;


    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void ToastLocal(@StringRes int content, int length) {
        if (getApplicationContext() == null) return;
        Toast.makeText(getApplicationContext(), content, length).show();
    }

    public void ToastLocal(String content, int length) {
        if (getApplicationContext() == null) return;
        Toast.makeText(getApplicationContext(), content, length).show();
    }

    public void ToastLocal(String content) {
        if (getApplicationContext() == null) return;
        ToastLocal(content, LENGTH_SHORT);
    }

    public void ToastLocal(@StringRes int content) {
        if (getApplicationContext() == null) return;
        ToastLocal(content, LENGTH_SHORT);
    }

    public void ToastSystem(@StringRes int content, int length) {
        Toast.makeText(getApplicationContext(), content, length).show();
    }

    protected Intent createIntentData(String action) {
        return new Intent().setAction(action);
    }

    @Override
    public void saveStringSharedPreference(String key, String value) {
        mSharedPreferencesUtils.saveDataString(key, value);
    }

    @Override
    public String getStringSharedPreference(String key) {
        return mSharedPreferencesUtils.getStringFromSharedPreferences(key);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
