package com.zhencai.zhencai.net.rx;

import android.content.Context;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.RestClient;
import com.zhencai.zhencai.net.RestCreator;
import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.ui.LoaderStyle;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 建造者模式 创建RestClient
 * Created by Administrator on 2018/4/4 0004.
 */
public class RxRestClientBuilder {
    private String mUrl = null;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private String loaderStyle = null;
    private WeakReference<Context> context = null;
    private File mfile = null;

    protected RxRestClientBuilder(){

    }

    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RxRestClientBuilder file(File file){
        this.mfile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath){
        this.mfile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, String loaderStyle){
        this.context = new WeakReference<>(context);
        this.loaderStyle = loaderStyle;
        return this;
    }

    public final RxRestClientBuilder loader(Context context){
        this.context = new WeakReference<>(context);
        this.loaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build(){
        return new RxRestClient(mUrl,PARAMS,mBody,mfile,context,loaderStyle);
    }
}
