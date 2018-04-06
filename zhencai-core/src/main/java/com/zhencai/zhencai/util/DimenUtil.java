package com.zhencai.zhencai.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.zhencai.zhencai.app.ZhenCai;

/**
 * Created by Administrator on 2018/4/5 0005.
 */

public class DimenUtil {
    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth(){
        final Resources resources = ZhenCai.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static int getScreenHeight(){
        final Resources resources = ZhenCai.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
