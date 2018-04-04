package com.zhencai.zhencaiec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;

import com.zhencai.zhencai.activities.ProxyActivity;
import com.zhencai.zhencai.app.ZhenCai;
import com.zhencai.zhencai.delegate.ZhenCaiDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public ZhenCaiDelegate setRootDelegate() {
        return new ZhenCaiECDelegate();
    }


}
