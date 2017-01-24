package com.yunxian.immerse.impl;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.utils.WindowUtils;

import java.lang.ref.SoftReference;

import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION.SDK_INT;

/**
 * 常规模式下的实现
 * <p>不限制SDK版本，但仅在5.0以上系统可设置状态栏颜色</p>
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/23 上午9:52
 */
public class NormalImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    public NormalImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);
        Window window = activity.getWindow();
        if (SDK_INT >= KITKAT) {
            WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_STATUS | FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (SDK_INT >= LOLLIPOP) {
            WindowUtils.addWindowFlags(window, FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        Activity activity = mActivityRef.get();
        if (activity != null && SDK_INT >= LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void setStatusColorRes(@ColorRes int colorRes) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            int color = ContextCompat.getColor(activity, colorRes);
            setStatusColor(color);
        }
    }

    @Override
    public boolean setStatusDrawable(@Nullable Drawable drawable) {
        return false;
    }

    @Override
    public boolean setStatusDrawableRes(@DrawableRes int drawableRes) {
        return false;
    }
}
