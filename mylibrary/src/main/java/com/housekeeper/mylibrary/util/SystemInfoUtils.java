package com.housekeeper.mylibrary.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Description: 获取手机信息
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class SystemInfoUtils {

    public static String getVersionName(Context context) {
        String versionName = "";
        // ---get the package info---
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            versionName = pi.versionName;
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = -1;
        // ---get the package info---
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            versionCode = pi.versionCode;
        }
        return versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TelephonyManager getDeviceInfo(Context context) {
        return (TelephonyManager) context.getSystemService(Application.TELEPHONY_SERVICE);
    }

    /**
     * 获取设备id
     *
     * @return 设备id
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId(Context context) {
        if (PermissionUtils.isHasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return getDeviceInfo(context).getDeviceId();
        }
        return "-1";
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取屏幕宽
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高
     *
     * @return 屏幕高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
//
//    /**
//     * 获取StatusBar高
//     *
//     * @return StatusBar高
//     */
//    public static int getStatusBarHeight() {
//        DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
//        return (int) Math.ceil(25 * dm.density);
//    }

    /**
     * 判断是否有网络
     *
     * @return true：有网络
     */
    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();

    }

    public static boolean isSDMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取公共目录
     *
     * @param type   列如：Environment.DIRECTORY_PICTURES
     * @param folder 文件夹名称
     * @return 文件绝对路径
     */
    public static String getExternalPublicPath(String type, String folder) {

        if (isSDMounted()) {
            File file;
            file = Environment.getExternalStoragePublicDirectory(type);
            if (file.exists()) {
                file = new File(file.getAbsolutePath(), folder);
                if (file.exists()) {
                    return file.getAbsolutePath();
                }
            }
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                return file.getAbsolutePath();
            }
        }
        return "";
    }

    /**
     * 判断是否有导航栏
     *
     * @param context context
     * @return true:有
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }


    /**
     * 检查系统键盘是否显示
     *
     * @param context context
     * @return true:显示
     */
    public static boolean isSysKeyboardVisiable(Activity context) {
        final View v = context.getWindow().peekDecorView();
        return v != null && v.getWindowToken() != null;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void getHideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(context.INPUT_METHOD_SERVICE);
        // 如果软键盘已经显示，则隐藏，反之则显示
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
