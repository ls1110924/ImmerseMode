package com.yunxian.immerse;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunxian.immerse.enumeration.StatusBarImmerseType;
import com.yunxian.immerse.impl.NormalImmerseMode;
import com.yunxian.immerse.impl.TranslucentImmerseMode;
import com.yunxian.immerse.impl.TransparentImmerseMode;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * 状态栏沉浸工具类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/22 下午8:17
 */
public final class ImmerseHelper {

    private final IImmerseMode immerseMode;

    public ImmerseHelper(@NonNull Activity activity) {
        this(activity, StatusBarImmerseType.NORMAL, StatusBarImmerseType.NORMAL);
    }

    public ImmerseHelper(@NonNull Activity activity, @NonNull StatusBarImmerseType levelInKK, @NonNull StatusBarImmerseType levelInL) {
        if (SDK_INT < KITKAT) {
            immerseMode = new NormalImmerseMode(activity);
        } else if (SDK_INT < LOLLIPOP) {
            switch (levelInKK) {
                case NORMAL:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TRANSLUCENT:
                    immerseMode = new TranslucentImmerseMode(activity, false);
                    break;
                case TRANSPARENT:
                    throw new IllegalAccessError("Kitkat don't support transparent mode!");
                default:
                    throw new IllegalArgumentException("Plz set correct parameter!");
            }
        } else {
            switch (levelInL) {
                case NORMAL:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TRANSLUCENT:
                    immerseMode = new TranslucentImmerseMode(activity, false);
                    break;
                case TRANSPARENT:
                    immerseMode = new TransparentImmerseMode(activity, false);
                    break;
                default:
                    throw new IllegalArgumentException("Plz set correct parameter!");
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色值
     */
    public void setStatusColor(@ColorInt int color) {
        immerseMode.setStatusColor(color);
    }

    /**
     * 设置状态栏颜色
     *
     * @param colorRes 颜色值资源Id
     */
    public void setStatusColorRes(@ColorRes int colorRes) {
        immerseMode.setStatusColorRes(colorRes);
    }

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawable Drawable对象
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setStatusDrawable(@Nullable Drawable drawable) {
        return immerseMode.setStatusDrawable(drawable);
    }

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawableRes Drawable资源Id
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setStatusDrawableRes(@DrawableRes int drawableRes) {
        return immerseMode.setStatusDrawableRes(drawableRes);
    }

}
