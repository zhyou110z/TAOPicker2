package com.rt.taopicker.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Glide图片显示
 * <p>
 * Created by chensheng on 2017/9/27.
 */

public class GlideUtil {

    public static void show(Context context, String url, ImageView imageView) {
        show(context, url, imageView, 0);
    }

    public static void show(Context context, String url, ImageView imageView, int placeholder) {
        if (context != null && url != null && imageView != null) {
            //4.2以下无法显示.png.webp
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                url = url.replaceAll(".png.webp", ".png");
            }

            if (placeholder != 0) {
                RequestOptions options = new RequestOptions().placeholder(placeholder);
                Glide.with(context).load(url).apply(options).into(imageView);
            } else {
                Glide.with(context).load(url).into(imageView);
            }
        } else if (imageView != null && placeholder != 0) {
            imageView.setImageResource(placeholder);
        }
    }

    public static void show(Context context, String url, final ImageView imageView, final int errorPlaceholder, ImageView.ScaleType scaleType) {
        if (context != null && url != null && imageView != null) {
            imageView.setScaleType(scaleType);

            //4.2以下无法显示.png.webp
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                url = url.replaceAll(".png.webp", ".png");
            }

            if (errorPlaceholder != 0) {
                Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        imageView.setImageResource(errorPlaceholder);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
            } else {
                Glide.with(context).load(url).into(imageView);
            }
        } else if (imageView != null && errorPlaceholder != 0) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(errorPlaceholder);
        }
    }

    public static void show(Context context, Object obj, ImageView imageView) {
        show(context, obj, imageView, 0);
    }

    public static void show(Context context, Object obj, ImageView imageView, int placeholder) {
        if (context != null && obj != null && imageView != null) {
            if (placeholder != 0) {
                RequestOptions options = new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(placeholder);
                Glide.with(context).load(obj).apply(options).into(imageView);
            } else {
                RequestOptions options = new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
                Glide.with(context).load(obj).into(imageView);
            }
        }
    }

}
