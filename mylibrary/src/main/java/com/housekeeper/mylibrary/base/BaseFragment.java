package com.housekeeper.mylibrary.base;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.housekeeper.mylibrary.R;
import com.housekeeper.mylibrary.api.HttpHelper;
import com.housekeeper.mylibrary.interf.BaseFragmentInterface;
import com.housekeeper.mylibrary.okhttp.callback.StringCallback;
import com.housekeeper.mylibrary.util.LogUtil;
import com.housekeeper.mylibrary.util.StringUtils;
import com.housekeeper.mylibrary.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;

/**
 * Description: fragment基类
 * Creator: Chenqiang
 * Date: 2017/2/13
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {

    private Dialog mLoadingDialog;
    protected Context mContext;
    /**
     * 网络请求回调
     */
    protected StringCallback mStringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            hideLoading();
            if (e instanceof ConnectException) {
                ToastUtil.showToast(mContext, R.string.net_error);
                onFailure(e, id);
                return;
            }
            if (e instanceof SocketTimeoutException) {
                ToastUtil.showToast(mContext, R.string.net_time_out);
                onFailure(e, id);
                return;
            }
            ToastUtil.showToast(mContext, R.string.net_connect_error);
            onFailure(e, id);
        }

        @Override
        public void onResponse(String response, int id) {
            hideLoading(id);
            onSuccess(response, id);
        }
    };


    /**
     * 服务端返回错误原因
     *
     * @param code  错误码
     * @param error 错误提示语
     * @param id    请求id
     */
    protected void onFailure(String code, String error, int id) {
        if (!StringUtils.isEmpty(error)) {
            ToastUtil.showToast(mContext, error);
        }
    }

    /**
     * 子类可以根据自己的处理方式处理
     *
     * @param e  Exception
     * @param id 请求网络标识
     */
    protected void onFailure(Exception e, int id) {
        LogUtil.d(null, e.toString());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {//非默认值
            Resources resources = getResources();
            if (resources.getConfiguration().fontScale != 1) {
                Configuration configuration = new Configuration();
                configuration.setToDefaults();
                resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            }
        } else {
            super.onConfigurationChanged(newConfig);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initTitleBar(view);
        initView(view);
        initData();
        initListener();
    }

    /**
     * 设置布局
     *
     * @return 布局id
     */
    protected abstract int getContentView();

    protected void initTitleBar(View view) {
    }

    ;

    /**
     * 子类实现
     */
    @Override
    public void initData() {

    }

    /**
     * 点击事件统一处理,子类选择性实现
     */
    protected void initListener() {
    }


    /**
     * 访问服务器postJson
     *
     * @param isShowLoading true:显示loading
     * @param partUrl       partUrl
     * @param id            标识id
     * @param jsonStr       jsonStr
     */
    protected void requestServerPostJson(boolean isShowLoading, String partUrl, int id, String jsonStr) {
        showLoading(isShowLoading);
        HttpHelper.postJson(this, partUrl, id, jsonStr, mStringCallback);
    }

    /**
     * 访问服务器postForm
     *
     * @param isShowLoading true:显示loading
     * @param partUrl       partUrl
     * @param id            标识id
     * @param params        params
     */
    protected void requestServerPostForm(boolean isShowLoading, String partUrl, int id, Map<String, String> params) {
        showLoading(isShowLoading);
        if (null != params) {
            LogUtil.e(params.toString());
        }
        HttpHelper.postForm(this, partUrl, id, params, mStringCallback);
    }

    /**
     * 显示loading
     *
     * @param isShowLoading true:显示loading
     */
    protected void showLoading(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading();
        }
    }

    /**
     * 访问网络成功后，实现该类
     *
     * @param response 返回结果
     * @param id       cf_contract_id
     */
    protected void onSuccess(String response, int id) {

    }

    @Override
    public void showLoading() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new ProgressDialog(mContext);
            ((ProgressDialog) mLoadingDialog).setMessage("加载中...");
            mLoadingDialog.setCancelable(false);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    protected void hideLoading(int id) {
        hideLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoading();
        HttpHelper.getInstance().cancelRequest(this);
    }

}
