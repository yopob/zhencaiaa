package com.zhencai.zhencai.net.rx;

import android.content.Context;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.HttpMethod;
import com.zhencai.zhencai.net.RestCreator;
import com.zhencai.zhencai.net.RestService;
import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.net.callback.RequestCallbacks;
import com.zhencai.zhencai.net.download.DownLoadHandle;
import com.zhencai.zhencai.ui.ZhenCaiLoader;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 与客户交互的类，客户建议调用此类
 * Created by Administrator on 2018/3/23 0023.
 */
public class RxRestClient {
    //region 参数列表
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    //raw相关
    private final RequestBody BODY;
    private final String LOADER_STYLE;
    private final WeakReference<Context> CONTEXT;
    //上传相关
    private final File FILE;
    //endregion

    public RxRestClient(String url,
                        WeakHashMap<String, Object> params,
                        RequestBody body,
                        File file,
                        WeakReference<Context> context,
                        String loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder(){
        return new RxRestClientBuilder();
    }

    private Observable<String> request(String httpMethod){
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;

        if(CONTEXT != null && LOADER_STYLE != null){
            ZhenCaiLoader.showLoading(CONTEXT.get(),LOADER_STYLE);
        }
        if(httpMethod.equals(HttpMethod.GET)){
            observable = service.get(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.POST)){
            observable = service.post(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.POST_RAW)){
            observable = service.postRaw(URL,BODY);
        }else if(httpMethod.equals(HttpMethod.PUT)){
            observable = service.put(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.PUT_RAW)){
            observable = service.putRaw(URL, BODY);
        }else if(httpMethod.equals(HttpMethod.DELETE)){
            observable = service.delete(URL,PARAMS);
        }else if(httpMethod.equals(HttpMethod.UPLOAD)){
            final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
            observable = service.upload(URL,body);
        }else {

        }

        return observable;
    }

    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        if(BODY == null) {
            return request(HttpMethod.POST);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("参数必须为空");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put(){
        if(BODY == null) {
            return request(HttpMethod.PUT);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("参数必须为空");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload(){
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> downLoad(){
        final Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestService().download(URL,PARAMS);
        return responseBodyObservable;
    }
}
