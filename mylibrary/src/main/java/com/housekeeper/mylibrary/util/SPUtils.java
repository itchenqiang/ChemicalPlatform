package com.housekeeper.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.StringDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: SharedPreference辅助类
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class SPUtils {

    public static final String IS_LOGIN = "isLogin";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({IS_LOGIN})
    public @interface SharedPreferencesKey {

    }

    /**
     * Sp文件名称
     */
    private static final String CONFIG = "config";

    /**
     * 获取SharedPreferences实例对象
     *
     * @param fileName sp文字
     */
    private synchronized static SharedPreferences getSharedPreference(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 保存一个String类型的值！
     */
    public synchronized static void putString(Context context, @SharedPreferencesKey String key, String value) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.putString(key, value).commit();
    }

    /**
     * 保存一个String类型的值！
     */
    public synchronized static void putString(Context context, String fileName, @SharedPreferencesKey String key,
                                              String value) {
        SharedPreferences sp = getSharedPreference(context, fileName);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value).commit();
    }

    /**
     * 获取String的value
     */
    public synchronized static String getString(Context context, @SharedPreferencesKey String key, String defValue) {
        return getSharedPreference(context, CONFIG).getString(key, defValue);
    }

    /**
     * 获取String的value
     */
    public synchronized static String getString(Context context, String fileName,
                                                @SharedPreferencesKey String key, String defValue) {
        return getSharedPreference(context, fileName).getString(key, defValue);
    }

    /**
     * 保存一个Boolean类型的值！
     */
    public synchronized static void putBoolean(Context context, @SharedPreferencesKey String key, Boolean value) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.putBoolean(key, value).commit();
    }

    /**
     * 永久保存一个Boolean类型的值！
     */
    public synchronized static void putBoolean(Context context, String fileName, @SharedPreferencesKey String key, Boolean value) {
        SharedPreferences.Editor edit = getSharedPreference(context, fileName).edit();
        edit.putBoolean(key, value).commit();
    }

    /**
     * 获取boolean的value
     */
    public synchronized static boolean getBoolean(Context context, @SharedPreferencesKey String key, Boolean defValue) {
        return getSharedPreference(context, CONFIG).getBoolean(key, defValue);
    }

    /**
     * 永久获取boolean的value
     */
    public synchronized static boolean getBoolean(Context context, String fileName, @SharedPreferencesKey String key, Boolean defValue) {
        return getSharedPreference(context, fileName).getBoolean(key, defValue);
    }

    /**
     * 保存一个int类型的值！
     */
    public synchronized static void putInt(Context context, @SharedPreferencesKey String key, int value) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.putInt(key, value).commit();
    }

    /**
     * 获取int的value
     */
    public synchronized static int getInt(Context context, @SharedPreferencesKey String key, int defValue) {
        return getSharedPreference(context, CONFIG).getInt(key, defValue);
    }

    /**
     * 保存一个float类型的值！
     */
    public synchronized static void putFloat(Context context, String fileName, @SharedPreferencesKey String key, float value) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.putFloat(key, value).commit();
    }

    /**
     * 获取float的value
     */
    public synchronized static float getFloat(Context context, @SharedPreferencesKey String key, Float defValue) {
        return getSharedPreference(context, CONFIG).getFloat(key, defValue);
    }

    /**
     * 保存一个long类型的值！
     */
    public synchronized static void putLong(Context context, @SharedPreferencesKey String key, long value) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.putLong(key, value).commit();
    }

    /**
     * 获取long的value
     */
    public synchronized static long getLong(Context context, @SharedPreferencesKey String key, long defValue) {
        return getSharedPreference(context, CONFIG).getLong(key, defValue);
    }

    /**
     * 清空对应key数据
     */
    public synchronized static void remove(Context context, @SharedPreferencesKey String key) {
        SharedPreferences.Editor edit = getSharedPreference(context, CONFIG).edit();
        edit.remove(key).commit();
    }

    /**
     * 清空数据
     */
    public synchronized static void clear(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = CONFIG;
        }
        SharedPreferences.Editor edit = getSharedPreference(context, fileName).edit();
        edit.clear().commit();
    }

}
