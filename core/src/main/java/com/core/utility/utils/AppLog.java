package com.core.utility.utils;

import android.app.Service;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.core.dc.utility.BuildConfig;

/**
 * Created by Le Duc Chung on 09/11/2016.
 * on project 'AutoSMS'
 */
@SuppressWarnings("unused")
public class AppLog {

    private String TAG;

    public static AppLog newInstance(String TAG) {
        return new AppLog(TAG);
    }

    public static AppLog newInstance(Class TAG) {
        return new AppLog(TAG);
    }

    public static <T extends AppCompatActivity> AppLog newInstance(T TAG) {
        return new AppLog(TAG);
    }

    public static <T extends Fragment> AppLog newInstance(T TAG) {
        return new AppLog(TAG);
    }

    public static <T extends DialogFragment> AppLog newInstance(T TAG) {
        return new AppLog(TAG);
    }

    public static <T extends Service> AppLog newInstance(T TAG) {
        return new AppLog(TAG);
    }

    public AppLog(String TAG) {
        this.TAG = TAG;
    }

    public <T> AppLog(T dialogFragment) {
        this(dialogFragment.getClass().getSimpleName());
    }

    public AppLog(Class classLog) {
        this(classLog.getName());
    }

    public void d(String content) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, content);
        }
    }

    public void e(String content) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, content);
        }
    }

    public void i(String content) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, content);
        }
    }

    public void w(String content) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, content);
        }
    }

    public void w(String classLog, String content) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG + "_" + classLog, content);
        }
    }

    public void i(String classLog, String content) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG + "_" + classLog, content);
        }
    }

    public void e(String classLog, String content) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG + "_" + classLog, content);
        }
    }

    public void d(String classLog, String content) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG + "_" + classLog, content);
        }
    }
}
