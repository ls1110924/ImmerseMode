package com.yunxian.immerse.utils;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/10/16 11:54
 */
public class ViewUtils {

    /**
     * 为DecorView对象设置或取消一些标志位集合
     *
     * @param decorView  DecorView
     * @param on         true为设置，false为取消
     * @param visibility {@link android.view.View#SYSTEM_UI_FLAG_FULLSCREEN}类标志位集合
     */
    private static void setSystemUiFlags(@NonNull View decorView, boolean on, int visibility) {
        int current = decorView.getSystemUiVisibility();
        if (on) {
            if ((current & visibility) != visibility) {
                decorView.setSystemUiVisibility(current | visibility);
            }
        } else {
            if ((current & visibility) != 0) {
                decorView.setSystemUiVisibility(current & ~visibility);
            }
        }
    }

    /**
     * 为DecorView对象增加标志位
     *
     * @param decorView DecorView
     * @param flags     {@link android.view.View#SYSTEM_UI_FLAG_FULLSCREEN}类标志位集合
     */
    public static void addSystemUiFlags(@NonNull View decorView, int flags) {
        setSystemUiFlags(decorView, true, flags);
    }

    /**
     * 为DecorView对象取消标志位
     *
     * @param decorView DecorView
     * @param flags     {@link android.view.View#SYSTEM_UI_FLAG_FULLSCREEN}类标志位集合
     */
    public static void clearSystemUiFlags(@NonNull View decorView, int flags) {
        setSystemUiFlags(decorView, false, flags);
    }

}
