package com.core.utility.utils;


/**
 * Created by Le Duc Chung on 5/29/2017.
 * on project 'TrackingProject'
 */

class Constants {
    public static final String EMPTY_STRING = "";
    public static final int ZERO_VALUE = 0;
    public static final String ON_NULL_DATA = "On Null Data";
    public static final String SPACE_CHARACTER = " ";
    public static final String DEVICE_ID = "device";
    public static final String UTF_8 = "UTF-8";
    public static final String TYPE = "type";

    public interface SETTINGS {
        String LANGUAGE = "chungld.language";
        int LOCATION_SETTING = 757;
    }

    public interface USER {
        String TOKEN = "token";
        String USER_NAME = "username";
        String PASSWORD = "password";
        String UNIT = "unit";
        String TYPE_LOGIN = "type_login";
        String PHONE_SIGN = "phone_sign";
        String GOOGLE_SIGN = "google_sign";
        String FACEBOOK_SIGN = "facebook_sign";
        String GGS_USER = "ggs_user_";
        String QUICK_USER = "quick_user";
        String QUICK_PASSWORD = "quick_password";
        String USER_ID = "user_id";
        String COUPON = "coupon";
    }

    public interface MESSAGE {
        String PHOTO = "photo";
        String TYPE = "type";
        String TYPE_MESSAGE = "1";
        String TYPE_CALL = "2";
        String TYPE_START = "0";
    }

    public interface NOTIFICATION {
        String TYPE_CALL = "1";
        String TYPE_ONLINE = "2";
        String TYPE_NOTIFICATION = "3";
        String TYPE_OPEN_APP = "4";
        String HAVE_NOTIFICATION = "have_notification";
    }

    public interface BROADCAST_RECEIVER_ACTION {
        String OFFLINE = "chungld.quickblock.offline";
    }
}
