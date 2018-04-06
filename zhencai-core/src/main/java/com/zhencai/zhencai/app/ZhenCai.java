package com.zhencai.zhencai.app;

import android.content.Context;

import android.util.ArrayMap;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public final class ZhenCai {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static ArrayMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getZhenCaiConfigs();
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
