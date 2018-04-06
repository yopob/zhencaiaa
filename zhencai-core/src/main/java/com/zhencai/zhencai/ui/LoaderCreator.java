package com.zhencai.zhencai.ui;

import android.content.Context;
import android.util.ArrayMap;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

/**
 * Created by Administrator on 2018/4/5 0005.
 */

public final class LoaderCreator {

    //AvloadingIndicatorview框架是在运行时，每次使用都反射加载对象，这样运行时消耗很大，这里使用缓存
    //将
    private static final ArrayMap<String,Indicator> LOADING_MAP = new ArrayMap<>();

    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(type) == null){
            final Indicator indicator =getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if(name == null || name.isEmpty()){
            return null;
        }
        final StringBuffer drawableClassName = new StringBuffer();
        if(!name.contains(".")){
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName
                    .append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator)drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
