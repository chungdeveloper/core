package com.core.utility.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Le Duc Chung on 2018-10-03.
 * on project 'musictv-android'
 */
public class GlideUtils {

    private static final String TAG = "glide";
    private static RequestOptions options = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);

    public static void load(Context context, String url, @DrawableRes int place, @DrawableRes int error, ImageView target) {
        if (target == null) return;
        if (context == null || url == null || url.equals("")) {
            target.setImageResource(error);
            return;
        }

        Log.d(TAG, "with: " + url);

        options = options.placeholder(place).error(error);
        Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(target);
    }

    public static void load(Context context, String url, @DrawableRes int holder, ImageView target) {
        if (target == null) return;
        if (context == null || url == null || url.equals("")) {
            target.setImageResource(holder);
            return;
        }
        options = options.placeholder(holder).error(holder);
        Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(target);
    }

    public static void load(Context context, String url, ImageView target) {
        if (target == null || context == null || url == null || url.equals("")) return;
        Glide.with(context).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(target);
    }
}
