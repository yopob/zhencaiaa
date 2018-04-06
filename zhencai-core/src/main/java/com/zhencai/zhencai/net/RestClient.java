package com.zhencai.zhencai.net;

import android.content.Context;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.net.callback.RequestCallbacks;
import com.zhencai.zhencai.ui.LoaderStyle;
import com.zhencai.zhencai.ui.ZhenCaiLoader;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Multipart;

/**
 * 与客户交互的类，客户建议调用此类
 * Created by Administrator on 2018/3/23 0023.
 */
public class RestClient {
    private final String URL;
    private final ArrayMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;

    public RestClient(String url,
                      ArrayMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        if(LOADER_STYLE != null){
            ZhenCaiLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = service.upload(URL,body);
                break;
            default:
                break;
        }

        if(call != null){
            //同步请求网络，需要另开线程执行，不然会影响主线程
            //call.execute();
            //异步请求网络，不需要另开线程执行
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(REQUEST,SUCCESS,FAILURE,ERROR,LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if(BODY == null) {
            request(HttpMethod.POST);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("参数必须为空");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if(BODY == null) {
            request(HttpMethod.PUT);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("参数必须为空");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }
}
