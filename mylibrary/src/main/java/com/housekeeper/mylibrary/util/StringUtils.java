package com.housekeeper.mylibrary.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.matches;

/**
 * Description: 字符串工具类
 * Creator: Chenqiang
 * Date: 2017/3/3
 */

public class StringUtils {

    /**
     * 正则表达式:验证手机号
     *
     * @param mobiles 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        if (mobiles.length() != 11) {
            return false;
        }
        String telRegex = "[1][3456789]\\d{9}";
        Pattern p = Pattern.compile(telRegex);
        Matcher m = p.matcher(mobiles);
        return m.find();
    }

    /**
     * 验证电话号码
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isTelephone(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
        return matches(regex, str);
    }

    /**
     * 校验邮箱
     *
     * @param email email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        String REGEX_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        return matches(REGEX_EMAIL, email);
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return 校验通过返回true，否则返回false
     * 密码8位以上，并仅允许字母及数字组合 最大16位的字符串
     */
    public static boolean isPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher m = p.matcher(password);
        return m.find();
    }


    /**
     * 判断两个字符串是否相等
     *
     * @param str1 str1
     * @param str2 str1
     * @return true:相等
     */
    public static boolean isEquals(String str1, String str2) {
        return !(StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)) && str1.equals(str2);
    }

    /**
     * 文字底部有横线
     *
     * @param str str
     * @return 底部带横线的文字
     */
    public static SpannableStringBuilder getBottomLineStr(String str) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        CharacterStyle span = new UnderlineSpan();
        spannable.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 手机号中间四位显示
     *
     * @return 隐藏的手机号
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isEmpty(mobile) || mobile.length() != 11) {
            return null;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    /**
     * 身份证验证
     *
     * @param number 身份证号
     * @return true:是身份证号
     */
    public static boolean isIDCardNO(String number) {
        String isIDCard = "[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9a-zA-Z])";
        return !StringUtils.isEmpty(number) && number.matches(isIDCard);
    }

    /**
     * 模糊证件号码
     *
     * @param number 证件号
     * @return 模糊证件号码
     */
    public static String maskIDNumber(String number) {
        if (StringUtils.isEmpty(number)) {
            return null;
        }
        int length = number.length();
        if (length < 4) {
            return number;
        }
        int maskLength = length - 4;
        String maskStr = "";
        for (int i = 0; i < maskLength; i++) {
            maskStr = maskStr + "*";
        }
        return number.substring(0, 2) + maskStr + number.substring(length - 2, length);
    }

    /**
     * 更换一段文字中某个字段的颜色
     *
     * @param str    原文本
     * @param regExp 正则表达式
     * @author Rao^Yang 2017年9月20日
     * @describe 设置富文本，改变textView部分文字颜色
     */
    public static SpannableStringBuilder richText(String str, String regExp, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        Pattern p = Pattern.compile(regExp, Pattern.LITERAL);
        Matcher m = p.matcher(str);
        while (m.find()) {
            int start = m.start(0);
            int end = m.end(0);
            //指定位置文本的字体颜色
            style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return style;
    }

    /**
     * 字符串判空
     *
     * @param text text
     * @return true:空
     */
    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text) || "null".equals(text);
    }

    /**
     * List<String> 转换成 String
     * 逗号拼接
     *
     * @param list List<String>
     * @return str
     */
    public static String listToString(List<String> list, String symbol) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(symbol);
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 姓名mask
     *
     * @return 隐藏的姓
     */
    public static String maskName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return "*" + name.substring(1);
    }
}
