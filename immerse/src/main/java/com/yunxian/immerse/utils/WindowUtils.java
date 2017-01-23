package com.yunxian.immerse.utils;

import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

/**
 * Window相关工具类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/23 上午10:07
 */
public final class WindowUtils {

    private WindowUtils() {
        throw new IllegalStateException("shouldn't init instance!");
    }

    /**
     * 为Window对象设置或取消一些标志位集合
     *
     * @param window Window对象
     * @param on     true为设置，false为取消
     * @param flags  {@link android.view.WindowManager.LayoutParams}中的标志位集合
     */
    private static void setWindowFlags(@NonNull Window window, boolean on, int flags) {
        WindowManager.LayoutParams params = window.getAttributes();
        if (on) {
            if ((params.flags & flags) == 0) {
                params.flags |= flags;
                window.setAttributes(params);
            }
        } else {
            if ((params.flags & flags) != 0) {
                params.flags &= ~flags;
                window.setAttributes(params);
            }
        }
    }

    /**
     * 为Window对象增加标志位
     *
     * @param window Window对象
     * @param flags  {@link android.view.WindowManager.LayoutParams}中的标志位集合
     */
    public static void addWindowFlags(@NonNull Window window, int flags) {
        setWindowFlags(window, true, flags);
    }

    /**
     * 为Window对象取消标志位
     *
     * @param window Window对象
     * @param flags  {@link android.view.WindowManager.LayoutParams}中的标志位集合
     */
    public static void clearWindowFlags(@NonNull Window window, int flags) {
        setWindowFlags(window, false, flags);
    }

}
