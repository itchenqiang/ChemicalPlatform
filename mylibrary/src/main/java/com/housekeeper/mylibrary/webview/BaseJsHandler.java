package com.housekeeper.mylibrary.webview;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.housekeeper.mylibrary.base.BaseWebViewActivity;

/**
 * Description:
 * Creator: Chenqiang
 * Date: 2017/12/4
 */

public class BaseJsHandler {

    private static final String DEFAULT_NAME = "webView";
    /**
     * js注入名称
     */
    private String mName;
    protected BaseWebViewActivity mActivity;

    public BaseJsHandler(BaseWebViewActivity activity) {
        this(DEFAULT_NAME, activity);
    }

    public BaseJsHandler(String name, BaseWebViewActivity activity) {
        mName = name;
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    protected void startActivity(Intent intent) {
        if (intent != null && mActivity != null) {
            mActivity.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void finishWeb() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

}
