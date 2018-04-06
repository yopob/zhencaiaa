package com.zhencai.zhencai.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.zhencai.zhencai.app.ZhenCai;
import com.zhencai.zhencai.net.callback.IRequest;
import com.zhencai.zhencai.net.callback.ISuccess;
import com.zhencai.zhencai.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/4/6 0006.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }


    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String)params[0];
        String extension = (String)params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final InputStream is = body.byteStream();
        final String name = (String)params[3];
        if(downloadDir == null || downloadDir.isEmpty()){
            downloadDir = "down_loads";
        }
        if(extension == null || extension.isEmpty()){
            extension = "";
        }
        if(name == null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS != null){
            SUCCESS.OnSuccess(file.getPath());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
        //autoInstallApk(file);
    }

    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            ZhenCai.getApplicationContext().startActivity(intent);
        }
    }
}
