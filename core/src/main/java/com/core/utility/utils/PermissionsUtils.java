package com.core.utility.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by Le Duc Chung on 12/10/2016.
 * on project 'AutoSMS'
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PermissionsUtils {
    public static final int CAMERA_REQUEST_CODE = 10;
    public static final int PHONE_REQUEST_CODE = 15;
    public static final int REQUEST_PERMISSION_SETTING = 11;
    public static final int PERMISSION_REQUEST_CODE = 12;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public static void init(Context context) {
        PermissionsUtils.context = context;
    }

    public static boolean isPermissionGranted(String permissions) {
        return ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean isPermissionGranted(String... permissions) {
        for (String value : permissions) {
            if (isPermissionGranted(value)) continue;
            return false;
        }
        return true;
    }

    public static <T extends Fragment> void requestPermission(T fragment, String[] permissionList, int codeRequest) {
        fragment.requestPermissions(permissionList, codeRequest);
    }

    public static <T extends AppCompatActivity> void requestPermission(T activity, String[] permissionList, int codeRequest) {
        ActivityCompat.requestPermissions(activity, permissionList, codeRequest);
    }

    public static <T extends Fragment> void startSettingPermissions(T fragment) {
        fragment.startActivityForResult(createIntentSetting(), REQUEST_PERMISSION_SETTING);
    }

    public static <T extends AppCompatActivity> void startSettingPermissions(T activity) {
        activity.startActivityForResult(createIntentSetting(), REQUEST_PERMISSION_SETTING);
    }

    private static Intent createIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        return intent.setData(uri);
    }
}
