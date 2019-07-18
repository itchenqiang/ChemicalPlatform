package com.housekeeper.mylibrary.util;

import android.content.Context;
import androidx.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description: Toast辅助类
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class ToastUtil {

    public static void showToast(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, @StringRes int res) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void showCenterToast(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
