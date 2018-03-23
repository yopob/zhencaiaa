package com.zhencai.zhencaieclib.icon;

import com.joanzapata.iconify.Icon;

/**
 * 阿里矢量图标枚举，与矢量图的名字对应
 */
public enum EcIcons implements Icon{
    //region 图标unicode名，图标名删除e前面的字符
    icon_scan('\ue601'),
    icon_ali_pay('\ue665');
    //endregion

    private char character;

    EcIcons(char character){
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
