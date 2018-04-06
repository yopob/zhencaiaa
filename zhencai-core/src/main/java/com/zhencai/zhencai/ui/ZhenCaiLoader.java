package com.zhencai.zhencai.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhencai.zhencai.R;
import com.zhencai.zhencai.util.DimenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/5 0005.
 */

public class ZhenCaiLoader {

    //宽高缩放比
    private static final int LOADER_SIZE_SCALE = 8;
    //底部偏移量缩放比
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static void showLoading(Context context,String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);

        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if(dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void showLoading(Context context,LoaderStyle type){
        showLoading(context,type.name());
    }

    public static void stopLoading(){
        for(AppCompatDialog dialog : LOADERS){
            if(dialog != null){
                if(dialog.isShowing()){
                    //会执行一些cancel回调
                    dialog.cancel();
                    //只消失提示框，没有回调
                    //dialog.dismiss();
                }
            }
        }
    }
}
