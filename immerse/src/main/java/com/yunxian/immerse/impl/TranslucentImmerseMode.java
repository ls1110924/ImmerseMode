package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.manager.GlobalConfig;
import com.yunxian.immerse.utils.DrawableUtils;
import com.yunxian.immerse.utils.WindowUtils;

import java.lang.ref.SoftReference;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 半透明状态栏模式下的实现
 * <p>该模式下状态栏盖有一层蒙版。4.4为渐变蒙版，5.0以上为半透明蒙版</p>
 * <p>仅在4.4以上系统可设置状态栏颜色</p>
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/24 下午3:26
 */
@TargetApi(KITKAT)
public class TranslucentImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final View mStatusBarView;

    /**
     * 构造方法
     *
     * @param activity   待应用沉浸模式的Activity
     * @param fullScreen true为需要全屏显示内容，即用户自己的内容需要延伸到状态栏之下；false表示用户内容需要正常布局再状态栏之外
     */
    public TranslucentImmerseMode(@NonNull Activity activity, boolean fullScreen) {
        mActivityRef = new SoftReference<>(activity);
        Window window = activity.getWindow();

        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        mStatusBarView = setupStatusBarView(activity, fullScreen);
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        mStatusBarView.setBackgroundColor(color);
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
        DrawableUtils.setViewBackgroundDrawable(mStatusBarView, drawable);
        return true;
    }

    @Override
    public boolean setStatusDrawableRes(@DrawableRes int drawableRes) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            Drawable drawable = ContextCompat.getDrawable(activity, drawableRes);
            setStatusDrawable(drawable);
        }
        return true;
    }

    @NonNull
    private View setupStatusBarView(@NonNull Activity activity, boolean fullScreen) {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("Plz invode setContentView() method first!");
        }

        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        userView.setFitsSystemWindows(!fullScreen);

        return statusBarView;
    }

}
