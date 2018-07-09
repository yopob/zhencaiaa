package com.zhencai.zhencai.net;

import android.content.Context;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.net.callback.RequestCallbacks;
import com.zhencai.zhencai.net.download.DownLoadHandle;
import com.zhencai.zhencai.ui.LoaderStyle;
import com.zhencai.zhencai.ui.ZhenCaiLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

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
    //region 参数列表
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //raw相关
    private final RequestBody BODY;
    private final String LOADER_STYLE;
    private final Context CONTEXT;
    //上传相关
    private final File FILE;
    //下载相关
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    //endregion

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      String loaderStyle,
                      String downLoadDir,
                      String extension,
                      String name) {
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
        this.DOWNLOAD_DIR = downLoadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(String httpMethod){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        if(LOADER_STYLE != null){
            ZhenCaiLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        if(httpMethod.equals(HttpMethod.GET)){
            call = service.get(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.POST)){
            call = service.post(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.POST_RAW)){
            call = service.postRaw(URL,BODY);
        }else if(httpMethod.equals(HttpMethod.PUT)){
            call = service.put(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.PUT_RAW)){
            call = service.putRaw(URL, BODY);
        }else if(httpMethod.equals(HttpMethod.DELETE)){
            call = service.delete(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.UPLOAD)){
            final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
            call = service.upload(URL,body);
        }else {

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

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void downLoad(){
        new DownLoadHandle(URL,REQUEST,SUCCESS,FAILURE,ERROR,DOWNLOAD_DIR,EXTENSION,NAME).handleDownLoad();
    }
}
