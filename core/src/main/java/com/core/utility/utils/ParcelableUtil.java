package com.core.utility.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Le Duc Chung on 2018-02-07.
 * on project 'AutoSMS'
 */

@SuppressWarnings({"SpellCheckingInspection", "unused", "WeakerAccess"})
public class ParcelableUtil {
    public static byte[] marshall(Parcelable parceable) {
        if (parceable == null) return null;
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unmarshall(byte[] bytes) {
        if (bytes == null) return null;
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        if (bytes == null) return null;
        Parcel parcel = unmarshall(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}