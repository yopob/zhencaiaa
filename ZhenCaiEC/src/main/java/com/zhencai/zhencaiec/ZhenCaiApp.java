package com.zhencai.zhencaiec;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zhencai.zhencai.app.ZhenCai;
import com.zhencai.zhencaieclib.icon.FontEcModule;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class ZhenCaiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化patch管理类
        myPathManager = new PatchManager(this);
        // 初始化patch版本
        myPathManager.init("1.0");
        // 加载已经添加到PatchManager中的patch
        myPathManager.loadPatch();


// .apatch file path ，这里一定要注意每台手机sd卡路径不同
        String patchFileString = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/zhencai/new.apatch";
        File file = new File(patchFileString);
        if(file.exists()) {
            /*try {

                Log.i("patchFileString", patchFileString);
                //3）添加patch
                myPathManager.addPatch(patchFileString);
                Log.i("info", "add");
            } catch (IOException e) {
                Log.e("exception", e.getMessage());
                e.printStackTrace();
            }
            myPathManager.removeAllPatch();*/
        }
        ZhenCai.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1")
                .configure();
    }



    public PatchManager myPathManager;
}
