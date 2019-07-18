package com.housekeeper.mylibrary.okhttp.builder;


import com.housekeeper.mylibrary.okhttp.OkHttpUtils;
import com.housekeeper.mylibrary.okhttp.request.OtherRequest;
import com.housekeeper.mylibrary.okhttp.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
