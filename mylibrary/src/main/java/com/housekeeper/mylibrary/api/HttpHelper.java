package com.housekeeper.mylibrary.api;

import androidx.annotation.StringDef;

import com.housekeeper.mylibrary.okhttp.OkHttpUtils;
import com.housekeeper.mylibrary.okhttp.callback.Callback;
import com.housekeeper.mylibrary.okhttp.log.LogInterceptor;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Description: OKHttp辅助类
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public class HttpHelper {

    private static final HttpHelper sHttpHelper = new HttpHelper();
    private final OkHttpUtils mOkHttpUtils;

    public static HttpHelper getInstance() {
        return sHttpHelper;
    }

    private HttpHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(25000L, TimeUnit.MILLISECONDS)
                .readTimeout(25000L, TimeUnit.MILLISECONDS)
                .writeTimeout(25000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LogInterceptor("okHttp"))
                .build();
        mOkHttpUtils = OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * get请求
     *
     * @param obj      请求类
     * @param url  请求地址
     * @param id       请求网络的标识
     * @param paramMap 请求参数，没有传null
     * @param callback 回调
     */
    public static void get(Object obj, String url, int id, Map<String, String> paramMap, Callback<String> callback) {
        OkHttpUtils
                .get()
                .url(url)
                .tag(obj)
                .id(id)
                .params(paramMap)
                .build()
                .execute(callback);
    }

    /**
     * json方式post请求
     *
     * @param obj        请求类
     * @param url    请求地址partUrl
     * @param id         请求网络的标识
     * @param JsonString 请求参数
     * @param callback   回调
     */
    public static void postJson(Object obj, String url, int id, String JsonString, Callback<String> callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .tag(obj)
                .id(id)
                .content(JsonString)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }

    /**
     * 普通POST
     *
     * @param obj      请求类
     * @param url  请求地址partUrl
     * @param id       请求网络的标识
     * @param paramMap 请求参数，没有传null
     * @param callback 回调
     */
    public static void postString(Object obj, String url, int id, Map<String, String> paramMap, Callback<String> callback) {
        OkHttpUtils
                .post()
                .url(url)
                .tag(obj)
                .id(id)
                .params(paramMap)
                .build()
                .execute(callback);
    }

    /**
     * 上传文件
     *
     * @param url  请求地址partUrl
     * @param file     文件（图片。。。）
     * @param id       请求网络的标识
     * @param callback 回调
     */
    public static void postFile(String url, int id, File file, Callback<String> callback) {
        OkHttpUtils
                .postFile()
                .url(url)
                .id(id)
                .file(file)
                .build()
                .execute(callback);
    }

    /**
     * 上传key
     */
    public static final String FILE = "file";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FILE})
    @interface FormKey {

    }

    /**
     * 上传表单
     *
     * @param url  请求地址partUrl
     * @param id       请求网络的标识
     * @param key      请求文件key upload:上传头像的key
     * @param file     文件（图片。。。）
     * @param callback 回调
     */
    public static void postForm(String url, int id, @FormKey String key, File file,
                                Map<String, String> params, Callback<String> callback) {
        OkHttpUtils.post()
                .addFile(key, file.getName(), file)
                .url(url)
                .params(params)
                .id(id)
                .build()
                .execute(callback);
    }

    /**
     * 上传表单(包含多张图片)
     *
     * @param url  请求地址partUrl
     * @param id       请求网络的标识
     * @param key      请求文件key upload:上传头像的key
     * @param fileMap  图片
     * @param callback 回调
     */
    public static void postForms(String url, int id, @FormKey String key, Map<String, File> fileMap,
                                 Map<String, String> params, Callback<String> callback) {
        OkHttpUtils.post()
                .files(key, fileMap)
                .url(url)
                .params(params)
                .id(id)
                .build()
                .execute(callback);
    }

    /**
     * 上传表单
     *
     * @param url      请求地址partUrl
     * @param id       请求网络的标识
     * @param callback 回调
     */
    public static void postForm(Object obj, String url, int id, Map<String, String> params, Callback<String> callback) {
        OkHttpUtils.post()
                .url(url)
                .tag(obj)
                .params(params)
                .id(id)
                .build()
                .execute(callback);
    }

    /**
     * 下载
     *
     * @param url      请求地址
     * @param id       请求网络的标识
     * @param callback 回调
     */
    public static void downloadFile(String url, int id, Callback<File> callback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(id)
                .build()
                .execute(callback);
    }

    /**
     * 下载pdf
     *
     * @param url      请求地址
     * @param id       请求网络的标识
     * @param callback 回调
     */
    public static void downloadFilePost(String url, int id, String JsonString, Callback<File> callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .id(id)
                .content(JsonString)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }

    /**
     * 下载图像验证码
     *
     * @param url      请求地址
     * @param id       请求网络的标识
     * @param callback 回调
     */
    public static void downloadGraphValidateFilePost(String url, int id, String JsonString,
                                                     Callback<Object> callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .id(id)
                .content(JsonString)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }

    public void cancelRequest(Object obj) {
        if (mOkHttpUtils != null) {
            mOkHttpUtils.cancelTag(obj);
        }
    }

}
