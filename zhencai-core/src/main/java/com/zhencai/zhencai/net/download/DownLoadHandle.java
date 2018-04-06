package com.zhencai.zhencai.net.download;

import android.os.AsyncTask;
import android.util.ArrayMap;

import com.zhencai.zhencai.net.RestCreator;
import com.zhencai.zhencai.net.callback.IError;
import com.zhencai.zhencai.net.callback.IFailure;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class DownLoadHandle {

    private final String URL;
    private final ArrayMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public DownLoadHandle(String url,
                          IRequest request,
                          ISuccess success,
                          IFailure failure,
                          IError error,
                          String downloadDir,
                          String extension,
                          String name) {
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public final void handleDownLoad(){
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            final ResponseBody responseBody = response.body();
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);
                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    //SaveFileTask已经调用一次，到时主要检查是否调用了两次
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if(ERROR != null){
                                ERROR.OnError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE != null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
