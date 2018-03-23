package com.zhencai.zhencai.app;

import android.util.ArrayMap;
import android.util.LruCache;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * app配置信息类
 */
public class Configurator {

    //region 属性
    /*private  static final LruCache<String,Object> ZHENCAI_CONFIGS;
    static {

        ZHENCAI_CONFIGS = new LruCache<>((int)Runtime.getRuntime().maxMemory()/8);
    }*/
    //保存配置信息
    private static final ArrayMap<String,Object> ZHENCAI_CONFIGS = new ArrayMap<String, Object>();
    //保存矢量图库描述器
    private static final ArrayList<IconFontDescriptor> ICONS  = new ArrayList<IconFontDescriptor>();

    //endregion

    private Configurator(){
        ZHENCAI_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    //region 初始化配置
    /**
     * 初始化配置
     */
    public final void configure(){
        ZHENCAI_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
        initIcons();
    }
    //endregion

    /**
     * 获得配置信息
     * @return 配置信息
     */
    final ArrayMap<String,Object> getZhenCaiConfigs(){
        return ZHENCAI_CONFIGS;
    }

    //region withApiHost(配置app对应的服务端地址)
    /**
     * 配置app对应的服务端地址
     * @param host url地址
     * @return 当前对象
     */
    public final Configurator withApiHost(String host){
        ZHENCAI_CONFIGS.put(ConfigType.API_HOST.name(),host);

        return this;
    }
    //endregion

    //region initIcons(初始化矢量图标)
    /**
     * 初始化矢量图标
     */
    private void initIcons(){
        if(ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer =Iconify.with(ICONS.get(0));
            for(int i = 1;i < ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }
    //endregion

    //region withIcon(加入矢量图标的描述器)
    /**
     * 加入矢量图标的描述器
     * @param descriptor 矢量图标的描述器
     * @return 当前对象
     */
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }
    //endregion

    //region checkConfiguration(检查配置是否准备好)
    /**
     * 检查配置是否准备好
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) ZHENCAI_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw  new RuntimeException("Configuration is not ready");
        }
    }
    //endregion

    //region getConfiguration(获取对应的配置对象)
    /**
     * 获取对应的配置对象
     * @param key 根据配置名获取配置对象
     * @param <T> 配置对象的类型
     * @return 配置对象
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(ConfigType key){
        checkConfiguration();
        return  (T)ZHENCAI_CONFIGS.get(key.name());
    }
    //endregion

    //region 单例懒汉模式（静态内部类）
    public static Configurator getInstance(){
        return  Holder.INSTANCE;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }
    //endregion
}
