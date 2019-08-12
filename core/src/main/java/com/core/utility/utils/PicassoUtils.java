package com.core.utility.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.core.dc.utility.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by Le Duc Chung on 18/10/2016.
 * on project 'TutorVNandroid'
 */
@SuppressWarnings("ALL")
public class PicassoUtils {
    private static final String EMPTY_CHARACTER = "";
    private static final String TAG = "PicassoUtils";

    public static void with(Context context, String url, int placeHold, int error, ImageView imageView) {
        if (context == null) return;
        if (url == null) {
            imageView.setImageDrawable(context.getResources().getDrawable(error));
            return;
        }
        Log.d(TAG, "with: " + url);
        if (url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(error).into(imageView);
        } else {
            Picasso.get().load(url).placeholder(placeHold).error(error).into(imageView);
        }
    }

    public static void with(Context context, String url, int placeHold, int error, ImageView imageView, Callback callback) {
        if (context != null) {
            if (url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(error).into(imageView);
            } else {
                Picasso.get().load(url).placeholder(placeHold).error(error).into(imageView, callback);
            }
        }
    }

    public static void with(Context context, String url, int placeHold, int error, int w, int h, ImageView imageView, Callback callback) {
        if (context != null) {
            if (url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(error).into(imageView);
            } else {
                Picasso.get().load(url).placeholder(placeHold).error(error).resize(w, h).into(imageView, callback);
            }
        }
    }

    public static void with(Context context, String url, int placeHold, int error, int w, int h, ImageView imageView) {
        if (context != null) {
            if (url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(error).into(imageView);
            } else {
                Picasso.get().load(url).placeholder(placeHold).error(error).resize(w, h).into(imageView);
            }
        }
    }

    public static void with(Context context, String url, int error, ImageView imageView) {
        if (context != null && url != null) {
            if (url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(error).into(imageView);
            } else {
                Picasso.get().load(url).placeholder(error).error(error).into(imageView);
            }
        } else {
            imageView.setImageResource(error);
        }
    }

    public static void with(Context context, String url, ImageView imageView) {
        if (context != null && url != null) {
            if (!url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(url).into(imageView);
            }
        }
    }

    public static void with(Context context, String url, int error, Target imageView) {
        if (context != null) {
            if (!url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(url).placeholder(R.drawable.no_thumb).error(error).into(imageView);
            }
        }

    }

    public static boolean clearImageDiskCache(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), "picasso-cache");
        if (cache.exists() && cache.isDirectory()) {
            return deleteDir(cache);
        }
        return false;
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    public static void with(String url, int placeHold, int error, ImageView imageView) {
        if (url == null) {
            imageView.setImageResource(error);
            return;
        }
        Log.d(TAG, "with: " + url);
        if (url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(error).into(imageView);
        } else {
            Picasso.get().load(url).placeholder(placeHold).error(error).into(imageView);
        }
    }

    public static void with(String url, int placeHold, int error, ImageView imageView, Callback callback) {

        if (url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(error).into(imageView);
        } else {
            Picasso.get().load(url).placeholder(placeHold).error(error).into(imageView, callback);
        }
    }

    public static void with(String url, int placeHold, int error, int w, int h, ImageView imageView, Callback callback) {

        if (url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(error).into(imageView);
        } else {
            Picasso.get().load(url).placeholder(placeHold).error(error).resize(w, h).into(imageView, callback);
        }
    }

    public static void with(String url, int placeHold, int error, int w, int h, ImageView imageView) {

        if (url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(error).into(imageView);
        } else {
            Picasso.get().load(url).placeholder(placeHold).error(error).resize(w, h).into(imageView);
        }
    }

    public static void with(String url, int error, ImageView imageView) {
        if (url != null) {
            if (url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(error).into(imageView);
            } else {
                Picasso.get().load(url).placeholder(error).error(error).into(imageView);
            }
        } else {
            imageView.setImageResource(error);
        }
    }

    public static void with(String url, ImageView imageView) {
        if (url != null) {
            if (!url.equals(EMPTY_CHARACTER)) {
                Picasso.get().load(url).into(imageView);
            }
        }
    }

    public static void with(String url, int error, Target imageView) {

        if (!url.equals(EMPTY_CHARACTER)) {
            Picasso.get().load(url).placeholder(R.drawable.no_thumb).error(error).into(imageView);
        }
    }

    public static <T extends Target> void cancel(T target) {
        Picasso.get().cancelRequest(target);
    }
}
