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
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

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

    @Nullable
    private ConsumeInsetsFrameLayout mNewUserView;

    /**
     * 构造方法
     *
     * @param activity     待应用沉浸模式的Activity
     * @param fullScreen   true为需要全屏显示内容，即用户自己的内容需要延伸到状态栏之下；
     *                     false表示用户内容需要正常布局再状态栏之外
     * @param adjustResize true为适配包含EditText的沉浸Activity，仅在fullScreen为true时有效
     */
    public TranslucentImmerseMode(@NonNull Activity activity, boolean fullScreen, boolean adjustResize) {
        mActivityRef = new SoftReference<>(activity);
        Window window = activity.getWindow();

        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        mStatusBarView = setupStatusBarView(activity, fullScreen, adjustResize);
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

    @Override
    public void setNavigationColor(@ColorInt int color) {

    }

    @Override
    public void setNavigationColorRes(@ColorRes int colorRes) {

    }

    @Override
    public boolean setNavigationDrawable(@Nullable Drawable drawable) {
        return false;
    }

    @Override
    public boolean setNavigationDrawableRes(@DrawableRes int drawableRes) {
        return false;
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        if (mNewUserView != null && operation) {
            mNewUserView.addOnInsetsChangeListener(listener);
        } else if (mNewUserView != null) {
            mNewUserView.removeOnInsetsChangeListener(listener);
        }
    }

    // 对于非全屏模式，工具类帮忙处理fitSystemWindow；
    // 对于全屏模式且非adjustResize时，需用户自行处理fitSystemWindow
    // 对于全屏模式且adjustResize时，工具类嵌套一个修复bug的ConsumeInsets布局且占用fitSystemWindow，
    // 用户不必处理，但用户需要监听时，需设置OnInsetsChangeListener来监听
    @NonNull
    private View setupStatusBarView(@NonNull Activity activity, boolean fullScreen, boolean adjustResize) {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("Plz invode setContentView() method first!");
        }

        if (fullScreen) {
            if (adjustResize) {
                mNewUserView = new ConsumeInsetsFrameLayout(activity);
                mNewUserView.setConsumeInsets(true);
                contentViewGroup.removeView(userView);
                mNewUserView.addView(userView);
                contentViewGroup.addView(mNewUserView, 0);
                mNewUserView.setFitsSystemWindows(true);
            } else {
                userView.setFitsSystemWindows(false);
            }
        } else {
            userView.setFitsSystemWindows(true);
        }

        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }

}
