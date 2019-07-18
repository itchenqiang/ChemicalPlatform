package com.housekeeper.mylibrary.okhttp.log;

import android.text.TextUtils;

import com.housekeeper.mylibrary.util.LogUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Description:
 * Creator: Chenqiang
 * Date: 2017/3/17
 */

public class LogInterceptor implements Interceptor {
    private static final String TAG = "OkHttp";
    private String mTag;

    public LogInterceptor(String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.mTag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            LogUtil.e(mTag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtil.e(mTag, "url : " + clone.request().url());
            LogUtil.e(mTag, "code : " + clone.code());
            LogUtil.e(mTag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                LogUtil.e(mTag, "message : " + clone.message());

            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    LogUtil.e(mTag, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        LogUtil.e(mTag, "responseBody's content : " + resp);
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        LogUtil.e(mTag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.e(mTag, "========response'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(mTag, "Exception: " + e.getMessage());
        }
        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            LogUtil.e(mTag, "========request'log=======");
            LogUtil.e(mTag, "method : " + request.method());
            LogUtil.e(mTag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtil.e(mTag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtil.e(mTag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogUtil.e(mTag, "requestBody's content : " + bodyToString(request));
                    } else {
                        LogUtil.e(mTag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.e(mTag, "========request'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(mTag, "Exception: " + e.getMessage());
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
