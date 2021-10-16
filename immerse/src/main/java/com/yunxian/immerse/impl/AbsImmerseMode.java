package com.yunxian.immerse.impl;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.ImmerseConfiguration;

import java.lang.ref.SoftReference;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/10/16 11:06
 */
public abstract class AbsImmerseMode implements IImmerseMode {

    protected final SoftReference<Activity> mActivityRef;
    protected final ImmerseConfiguration immerseConfiguration;

    public AbsImmerseMode(@NonNull Activity activity, @NonNull ImmerseConfiguration immerseConfiguration) {
        this.mActivityRef = new SoftReference<>(activity);
        this.immerseConfiguration = immerseConfiguration;
    }

    int generateCompatStatusBarColor(@ColorInt int statusBarColor) {
        if (immerseConfiguration.coverCompatMask && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            statusBarColor = ColorUtils.compositeColors(immerseConfiguration.coverMaskColor, statusBarColor);
        }
        return statusBarColor;
    }

}
