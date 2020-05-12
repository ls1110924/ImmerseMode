package com.yunxian.immerse.manager;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.yunxian.immerse.R;
import com.yunxian.immerse.utils.ResourcesUtils;

/**
 * 全局配置参数类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/21 下午7:50
 */
public final class ImmerseGlobalConfig {

    private static ImmerseGlobalConfig INSTANCE = null;

    /**
     * 初始化方法，请在Application中调用该方法完成初始化
     *
     * @param context Context上下文对象
     */
    public static void init(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (ImmerseGlobalConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImmerseGlobalConfig(context);
                }
            }
        }
    }

    /**
     * 获取单实例对象的方法
     *
     * @return Global单实例对象
     * @throws IllegalStateException
     */
    @NonNull
    public static ImmerseGlobalConfig getInstance() throws IllegalStateException {
        if (INSTANCE == null) {
            throw new IllegalStateException("Plz init first on Application onCreate()!");
        }
        return INSTANCE;
    }


    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    private final int mStatusBarHeight;

    private ImmerseGlobalConfig(@NonNull Context context) {
        Resources res = context.getResources();
        mStatusBarHeight = ResourcesUtils.getDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME, "android", R.dimen.immerse_status_bar_height);
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }
}
