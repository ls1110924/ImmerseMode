package com.yunxian.immerse;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

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

    /**
     * 设置导航栏颜色
     *
     * @param color 颜色值
     */
    void setNavigationColor(@ColorInt int color);

    /**
     * 设置导航栏颜色
     *
     * @param colorRes 颜色值资源Id
     */
    void setNavigationColorRes(@ColorRes int colorRes);

    /**
     * 如果支持的话，设置导航栏背景Drawable
     *
     * @param drawable Drawable对象
     * @return 设置成功，返回true；否则返回false
     */
    boolean setNavigationDrawable(@Nullable Drawable drawable);

    /**
     * 如果支持的话，设置导航栏背景Drawable
     *
     * @param drawableRes Drawable资源Id
     * @return 设置成功，返回true；否则返回false
     */
    boolean setNavigationDrawableRes(@DrawableRes int drawableRes);

    /**
     * 用于当内容全屏沉浸时，获取Insets应有的留白
     *
     * @return Insets留白
     */
    @NonNull
    Rect getInsetsPadding();

    /**
     * 设置Insets监听器，以监听fitSystemWindow事件
     *
     * @param operation true为注册，false为反注册
     * @param listener  Insets监听器
     */
    void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener);

}
