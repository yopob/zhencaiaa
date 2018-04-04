package com.zhencai.zhencaiec;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.euler.andfix.AndFix;
import com.alipay.euler.andfix.patch.PatchManager;
import com.zhencai.zhencai.delegate.ZhenCaiDelegate;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ZhenCaiECDelegate extends ZhenCaiDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_zhencaiec;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private Button test;
    private Button fix;
    private Button check;

    @Override
    public void onResume() {
        test = getActivity().findViewById(R.id.Test1);
        fix = getActivity().findViewById(R.id.Fix);
        check = getActivity().findViewById(R.id.Check);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"修复Bug",Toast.LENGTH_LONG).show();
            }
        });

        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int write = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int read = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
                    } else {
                        String patchFileString = Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/zhencai/new.apatch";
                        File file = new File(patchFileString);
                        if (file.exists()) {
                            Log.i("wytings", "permission -------------> " + file.getAbsolutePath());
                            try {
                                ((ZhenCaiApp)getActivity().getApplicationContext()).myPathManager.addPatch(patchFileString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("wytings", "permission -------------fail to make file ");
                        }
                    }
                }
                /*Toast.makeText(getActivity(),"Fix",Toast.LENGTH_LONG).show();
                PatchManager myPathManager = ((ZhenCaiApp)getActivity().getApplicationContext()).myPathManager;
                //myPathManager.removeAllPatch();
                try {
                    // .apatch file path ，这里一定要注意每台手机sd卡路径不同
                    String patchFileString = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/zhencai/new.apatch";
                    Log.i("patchFileString",patchFileString);
                    //3）添加patch
                    myPathManager.addPatch(patchFileString);
                    Log.i("info","add");
                } catch (IOException e) {
                    Log.e("exception",e.getMessage());
                    e.printStackTrace();
                }*/
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),String.valueOf(AndFix.setup()),Toast.LENGTH_LONG).show();
            }
        });

        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 300) {
            Log.i("wytings", "--------------requestCode == 300->" + requestCode + "," + permissions.length + "," + grantResults.length);
        } else {
            Log.i("wytings", "--------------requestCode != 300->" + requestCode + "," + permissions + "," + grantResults);
        }
    }
}
