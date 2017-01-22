package com.yunxian.immerse.manager;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.yunxian.immerse.R;
import com.yunxian.immerse.utils.ResourcesUtils;

/**
 * 全局配置参数类
 *
 * @author AShuai
 * @email lishuai.ls@alibaba-inc.com
 * @date 17/1/21 下午7:50
 */
public final class GlobalConfig {

    private static GlobalConfig INSTANCE = null;

    public GlobalConfig getInstance(@NonNull Activity activity) {
        if (INSTANCE == null) {
            synchronized (GlobalConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlobalConfig(activity);
                }
            }
        }
        return INSTANCE;
    }

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    private final int mStatusBarHeight;

    private GlobalConfig(@NonNull Activity activity) {
        Resources res = activity.getResources();
        mStatusBarHeight = ResourcesUtils.getDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME, "android", R.dimen.immerse_status_bar_height);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }
}
