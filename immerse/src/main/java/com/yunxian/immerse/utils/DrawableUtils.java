package com.yunxian.immerse.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Drawable工具类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/24 下午3:56
 */
public final class DrawableUtils {

    private DrawableUtils() {
        throw new IllegalStateException("shouldn't init instance!");
    }

    /**
     * 给View设置一个Background Drawable
     *
     * @param view     View对象
     * @param drawable Drawable对象
     */
    @SuppressWarnings("Deprecated")
    public static void setViewBackgroundDrawable(@Nullable View view, @Nullable Drawable drawable) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
