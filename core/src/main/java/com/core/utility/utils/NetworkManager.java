package com.core.utility.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


/**
 * Created by ChungLD on 16/09/2016.
 */
public class NetworkManager {
    public static final int VIETTEL_TELECOM = 4;
    public static final int VINAPHONE = 2;
    public static final int MOBIFONE = 1;

    public static int sNetworkStatus;
    public static final int MOBILE = 1;
    public static final int WIFI = 2;
    public static final int DIS = 0;

    private int mcc, mnc;
    public static final int VIETNAM = 452;
    private AppLog log;

    private Context context;

    public interface NetworkCallBackListener {
        void onMobileConnect();

        void onWifiConnect();

        void onDisconnect();
    }

    public NetworkManager(Context context) {
        this.context = context;
        log = AppLog.newInstance(NetworkManager.class);
    }

    public int checkNetworkCode() {
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();

        if (TextUtils.isEmpty(networkOperator) == false) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
        }

        if (mcc == VIETNAM) {
            return mnc;
        }
        return 0;
    }

    public boolean isNetworkOnline(NetworkCallBackListener callBack) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
                log.i("MOBILE");
                sNetworkStatus = MOBILE;
                callBack.onMobileConnect();
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                    sNetworkStatus = WIFI;
                    log.i("WIFI");
                    callBack.onWifiConnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sNetworkStatus = DIS;
            callBack.onDisconnect();
            return false;
        }
        return status;

    }
}
