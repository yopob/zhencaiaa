package com.zhencai.zhencai.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public abstract class BaseDelegate extends SwipeBackFragment {
    private Unbinder mUnbinder;
    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if(setLayout() instanceof  Integer){
            //只获取setLayout的数据，不需要把setLayout添加到container下面，最后一个参数为false
            rootView = inflater.inflate((Integer)setLayout(),container,false);
        }else if(setLayout() instanceof  View){
            rootView = (View)setLayout();
        }
        if(rootView != null){
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
