package com.housekeeper.mylibrary.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Description: PopupWindow辅助类
 * Creator: Chenqiang
 * Date: 2017/2/20
 */
public class PopupWindowHelper {

    public static PopupWindow getPopupWindow(Context context, @LayoutRes int viewId, int width, int height) {
        View view = View.inflate(context, viewId, null);
        PopupWindow popupWindow = new PopupWindow(view, width, height);
        popupWindow.
                setBackgroundDrawable(new ColorDrawable(
                        ContextCompat.getColor(context, android.R.color.transparent)));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        return popupWindow;
    }

    public static PopupWindow getMatchWrapPopupWindow(Context context, @LayoutRes int viewId) {
        return getPopupWindow(context, viewId, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static PopupWindow getMatchPopupWindow(Context context, @LayoutRes int viewId) {
        return getPopupWindow(context, viewId, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
