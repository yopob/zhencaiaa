package com.zhencai.zhencaiec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zhencai.zhencai.app.ZhenCai;
import com.zhencai.zhencaieclib.icon.FontEcModule;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class ZhenCaiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZhenCai.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1")
                .configure();
    }
}
