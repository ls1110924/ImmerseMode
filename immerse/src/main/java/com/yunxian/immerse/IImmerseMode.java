package com.yunxian.immerse;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

/**
 * 半沉浸模式接口类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/22 下午8:29
 */
public interface IImmerseMode {

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色值
     */
    void setStatusColor(@ColorInt int color);

    /**
     * 设置状态栏颜色
     *
     * @param colorRes 颜色值资源Id
     */
    void setStatusColorRes(@ColorRes int colorRes);

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawable Drawable对象
     * @return 设置成功，返回true；否则返回false
     */
    boolean setStatusDrawable(@Nullable Drawable drawable);

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawableRes Drawable资源Id
     * @return 设置成功，返回true；否则返回false
     */
    boolean setStatusDrawableRes(@DrawableRes int drawableRes);

}
