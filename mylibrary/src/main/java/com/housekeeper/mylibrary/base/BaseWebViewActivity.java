package com.housekeeper.mylibrary.base;

import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.housekeeper.mylibrary.util.LogUtil;
import com.housekeeper.mylibrary.util.StringUtils;
import com.housekeeper.mylibrary.util.ToastUtil;
import com.housekeeper.mylibrary.webview.BaseJsHandler;


/**
 * Description:
 *
 * @author created by Chenqiang
 * Date: 2019-5-21
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    protected String mUrl;
    protected WebView mWebView;

    @Override
    public void initView() {
        mWebView = getWebView();
        initSetting();
        loadWebView();
    }

    /**
     * 获取webView,不能为null
     *
     * @return WebView
     */
    protected abstract WebView getWebView();

    protected void initSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccess(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDisplayZoomControls(false);//隐藏webView缩放按钮

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(initWebViewClient());
        mWebView.setWebChromeClient(initWebChromeClient());
        BaseJsHandler baseJsHandler = initJsHandler();
        mWebView.addJavascriptInterface(baseJsHandler, baseJsHandler.getName());
    }

    /**
     * 加载网页(需要调用loadWebViewByUrl 或者loadWebViewByHtml)
     */
    protected abstract void loadWebView();


    protected void loadWebViewByUrl(String url) throws Exception {
        if (null == mWebView) {
            throw new Exception("mWebView is not null");
        }
        if (StringUtils.isEmpty(url)) {
            throw new Exception("url is not null");
        }
        LogUtil.e(mUrl);
        mWebView.loadUrl(url);
    }

    protected void loadWebViewByHtml(String html) throws Exception {
        if (null == mWebView) {
            throw new Exception("mWebView is not null");
        }
        if (StringUtils.isEmpty(html)) {
            throw new Exception("html is not null");
        }
        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null); // 加载定义的代码，并设定编码格式和字
    }

    protected WebChromeClient initWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        };
    }

    protected WebViewClient initWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                    super.onReceivedSslError(view, handler, error);
                LogUtil.e("WebViewActivity: onReceivedSslError=" + error.toString());
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtil.e("WebViewActivity: " + description + "======" + failingUrl);
                ToastUtil.showToast(mContext, description);
            }
        };
    }


    protected BaseJsHandler initJsHandler() {
        return new BaseJsHandler(this);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 调用js函数拼参
     *
     * @param function 函数名称
     * @param params   参数
     * @return str
     */
    public static String buildJsFunctionString(String function, Object... params) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(function)) {
            sb.append(function).append("(");
            if (params != null && params.length > 0) {
                for (Object param : params) {
                    if (param instanceof String) {
                        sb.append("'").append(param).append("'");
                    } else {
                        sb.append(param);
                    }
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
            }
            sb.append(")");
        }
        LogUtil.e("回调网页的信息= " + sb.toString().trim());
        return sb.toString().trim();
    }

    public void loadJsFunction(String function, Object... params) {
        final String jsFunctionString = buildJsFunctionString(function, params);
        if (null != mWebView && !StringUtils.isEmpty(jsFunctionString)) {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mWebView.evaluateJavascript(jsFunctionString, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String arg0) {
                                LogUtil.e("onReceiveValue= " + arg0);
                            }
                        });
                    } else {
                        mWebView.loadUrl("javascript:" + jsFunctionString);
                    }
                }
            });

        }
    }
}
