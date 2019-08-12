package com.core.utility.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Le Duc Chung on 2017-11-06.
 * on project 'TrackingProject'
 */

public class SecretUtils {

    public static String encodeStringToBase64(String value) throws UnsupportedEncodingException {
        return Base64.encodeToString(value.getBytes(Constants.UTF_8), Base64.DEFAULT);
    }


    public static String decodeBase64ToString(String base64) throws UnsupportedEncodingException {
        return new String(Base64.decode(base64, Base64.DEFAULT), Constants.UTF_8);
    }

    public static String decryptData(String value, String secretKey) throws UnsupportedEncodingException {
        String result = Constants.EMPTY_STRING;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            long add = i % secretKey.length();
            character = (char) (((int) character) - add);
            result += character;
        }

        return decodeBase64ToString(result);
    }

    public static String encryptData(String value, String secretKey) throws UnsupportedEncodingException {
        value = encodeStringToBase64(value);
        String result = Constants.EMPTY_STRING;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            long add = i % secretKey.length();
            character = (char) (((int) character) + add);
            result += character;
        }

        return result;
    }
}
