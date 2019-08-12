package com.core.utility.utils.cropimage;

class Log {

    private static final String TAG = "android-crop";

    public static void e(String msg) {
        android.util.Log.d(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        android.util.Log.d(TAG, msg, e);
    }

    public static void d() {

    }
}
