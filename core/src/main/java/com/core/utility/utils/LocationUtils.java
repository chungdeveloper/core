package com.core.utility.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;

import com.core.dc.utility.R;

/**
 * Created by Le Duc Chung on 2017-11-03.
 * on project 'TrackingProject'
 */

public class LocationUtils {

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    public static <T extends AppCompatActivity> void buildAlertMessageNoGps(T activity, GPSMessageListener gpsMessageListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.gps_setting))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.yes), (dialog, id) -> activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Constants.SETTINGS.LOCATION_SETTING))
                .setNegativeButton(activity.getString(R.string.no), (dialog, id) -> {
                    dialog.cancel();
                    if (gpsMessageListener == null) return;
                    gpsMessageListener.onEventCancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public interface GPSMessageListener {
        void onEventCancel();
    }
}
