package com.housekeeper.mylibrary.okhttp.builder;

import com.housekeeper.mylibrary.okhttp.request.PostFileRequest;
import com.housekeeper.mylibrary.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }


}
