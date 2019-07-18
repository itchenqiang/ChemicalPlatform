package com.housekeeper.mylibrary.util;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Description: glide辅助类
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class ImageLoader {

    /**
     * 加载图片
     *
     * @param context   加载图片的activity或fragment
     * @param url       图片地址
     * @param imageView imageView
     * @param type      默认图片类型
     */
    public static void displayImage(Context context, String url, ImageView imageView, @DrawableRes int type) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(type)
                .error(type);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context   加载图片的activity或fragment
     * @param file      图片文件
     * @param imageView imageView
     */
    public static void displayImage(Context context, File file, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context)
                .load(file.getAbsoluteFile())
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载本地图片圆角
     *
     * @param context        加载图片的activity或fragment
     * @param file           图片文件
     * @param imageView      imageView
     * @param roundedCorners 单位dp
     */
    public static void displayImage(Context context, File file, ImageView imageView, int roundedCorners) {
        int roundedCornersPx = DensityUtil.dpToPx(context, roundedCorners);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new RoundedCorners(roundedCornersPx))
                .skipMemoryCache(true);
        Glide.with(context)
                .load(file.getAbsoluteFile())
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆图
     *
     * @param context   加载图片的activity或fragment
     * @param imageView imageView
     * @param imageUrl  图片地址
     * @param type      默认图片类型
     */
    public static void displayCircle(Context context, ImageView imageView, String imageUrl,
                                     @DrawableRes int type) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(type)
                .error(type)
                .centerCrop()
                .circleCrop();

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆角图
     *
     * @param context        加载图片的activity或fragment
     * @param imageView      imageView
     * @param roundedCorners 圆角px
     * @param imageUrl       图片地址
     * @param type           默认图片类型
     */
    public static void displayRoundedCorner(Context context, ImageView imageView, int roundedCorners, String imageUrl,
                                            @DrawableRes int type) {

        int roundedCornersPx = DensityUtil.dpToPx(context, roundedCorners);
        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(roundedCornersPx))
                .placeholder(type)
                .error(type);

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆角图
     *
     * @param context        加载图片的activity或fragment
     * @param imageView      imageView
     * @param roundedCorners 圆角px
     * @param file           图片地址
     */
    public static void displayRoundedCorner(Context context, ImageView imageView, int roundedCorners, File file) {

        int roundedCornersPx = DensityUtil.dpToPx(context, roundedCorners);
        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(roundedCornersPx));

        Glide.with(context)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void displayGifLoadingImage(Context context, ImageView imageView, @RawRes int resourceId) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asGif()
                .apply(requestOptions)
                .load(resourceId)
                .into(imageView);
    }

    public static void displayGifLoadingImage(Context context, ImageView imageView, File gifFile) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .asGif()
                .apply(requestOptions)
                .load(gifFile)
                .into(imageView);
    }

}
