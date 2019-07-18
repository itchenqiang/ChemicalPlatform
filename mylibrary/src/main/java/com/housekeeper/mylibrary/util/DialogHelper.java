package com.housekeeper.mylibrary.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;


/**
 * Description: 通用的对话框
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class DialogHelper {
    private static AlertDialog.Builder getDialog(Context context) {
        return new AlertDialog.Builder(context);
    }

    /**
     * 获取一个验证对话框
     *
     * @param context          context
     * @param message          内容
     * @param positiveListener 确定监听
     * @param negativeListener 取消监听
     * @return Dialog
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context, String message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getDialog(context)
                .setMessage(message)
                .setPositiveButton("确定", positiveListener)
                .setNegativeButton("取消", negativeListener);
    }

    /**
     * 获取一个验证对话框
     *
     * @param context          context
     * @param message          内容
     * @param btnText          按钮文字
     * @param positiveListener 确定监听
     * @return Dialog
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context, String message, String btnText,
            DialogInterface.OnClickListener positiveListener) {
        return getDialog(context)
                .setMessage(message)
                .setPositiveButton(btnText, positiveListener);
    }

    /**
     * 获取一个验证对话框
     *
     * @param context          context
     * @param message          内容
     * @param positiveListener 确定监听
     * @return Dialog
     */
    public static AlertDialog.Builder getConfirmDialog(Context context, String message,
                                                       DialogInterface.OnClickListener positiveListener) {
        Resources resources = context.getResources();
        return getDialog(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", positiveListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(
            Context context,
            String title,
            String[] arrays,
            int selectIndex,
            DialogInterface.OnClickListener onClickListener,
            DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", onPositiveClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(
            Context context,
            String title,
            String[] arrays,
            int selectIndex,
            DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    /**
     * 获取一个输入对话框(自定义确定，取消按钮)
     *
     * @param context          context
     * @param title            title
     * @param editText         editText
     * @param positiveText     positiveText
     * @param negativeText     negativeText
     * @param cancelable       cancelable
     * @param positiveListener positiveListener
     * @param negativeListener negativeListener
     * @return AlertDialog
     */
    public static AlertDialog.Builder getInputDialog(
            Context context,
            String title,
            EditText editText,
            String positiveText,
            String negativeText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getDialog(context)
                .setCancelable(cancelable)
                .setTitle(title)
                .setView(editText)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener);
    }

    /**
     * 获取一个输入对话框(默认确定取消)
     *
     * @param context          context
     * @param title            title
     * @param editText         editText
     * @param cancelable       cancelable
     * @param positiveListener positiveListener
     * @param negativeListener negativeListener
     * @return AlertDialog
     */
    public static AlertDialog.Builder getInputDialog(
            Context context, String title, EditText editText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getInputDialog(
                context, title, editText, "确定", "取消", cancelable
                , positiveListener, negativeListener);
    }

    public static AlertDialog.Builder getSelectDialog(
            Context context, String title, String[] items,
            DialogInterface.OnClickListener itemListener) {
        return getDialog(context)
                .setTitle(title)
                .setItems(items, itemListener);
    }

}
