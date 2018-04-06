package com.zhencai.zhencai.net;

import android.content.Context;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 建造者模式 创建RestClient
 * Created by Administrator on 2018/4/4 0004.
 */
public class RestClientBuilder {
    private String mUrl = null;
    private static final ArrayMap<String,Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIErro = null;
    private RequestBody mBody = null;
    private LoaderStyle loaderStyle = null;
    private Context context = null;
    private File mfile = null;
    private String downLoadDir = null;
    private String name = null;
    private String extension = null;

    protected RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(ArrayMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mfile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath){
        this.mfile = new File(filePath);
        return this;
    }

    public final RestClientBuilder downLoadDir(String downLoadDir){
        this.downLoadDir = downLoadDir;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.name = name;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.extension = extension;
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIErro = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.context = context;
        this.loaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.context = context;
        this.loaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISuccess,mIFailure,mIErro,mBody,mfile,context,loaderStyle,downLoadDir,extension,name);
    }
}
