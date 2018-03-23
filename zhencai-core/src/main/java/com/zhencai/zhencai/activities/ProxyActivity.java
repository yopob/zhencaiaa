package com.zhencai.zhencai.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import com.zhencai.zhencai.R;

import com.zhencai.zhencai.delegate.ZhenCaiDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public abstract class ProxyActivity extends SupportActivity {
    public abstract ZhenCaiDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private  void initContainer(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState == null){
            //加载根fragment，关联装载fragment的容器
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
