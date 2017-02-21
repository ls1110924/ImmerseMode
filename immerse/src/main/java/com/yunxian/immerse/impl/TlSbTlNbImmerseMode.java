package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.manager.ActivityConfig;
import com.yunxian.immerse.manager.ImmerseGlobalConfig;
import com.yunxian.immerse.utils.DrawableUtils;
import com.yunxian.immerse.utils.WindowUtils;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import java.lang.ref.SoftReference;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * 半透明状态栏半透明导航栏
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/31 下午5:19
 */
@TargetApi(KITKAT)
public class TlSbTlNbImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final ActivityConfig mActivityConfig;

    private final View mCompatStatusBarView;
    // 当一些手机没有导航栏时，该对象为空
    @Nullable
    private final View mCompatNavigationBarView;

    public TlSbTlNbImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);

        mActivityConfig = new ActivityConfig(activity);
        setupUserView(activity);
        mCompatStatusBarView = setupStatusBarView(activity);
        mCompatNavigationBarView = setupNavigationBarView(activity);
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        mCompatStatusBarView.setBackgroundColor(color);
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
        DrawableUtils.setViewBackgroundDrawable(mCompatStatusBarView, drawable);
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
        if (mCompatNavigationBarView != null) {
            mCompatNavigationBarView.setBackgroundColor(color);
        }
    }

    @Override
    public void setNavigationColorRes(@ColorRes int colorRes) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            int color = ContextCompat.getColor(activity, colorRes);
            setNavigationColor(color);
        }
    }

    @Override
    public boolean setNavigationDrawable(@Nullable Drawable drawable) {
        if (mCompatNavigationBarView != null) {
            mCompatNavigationBarView.setBackground(drawable);
        }
        return true;
    }

    @Override
    public boolean setNavigationDrawableRes(@DrawableRes int drawableRes) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            Drawable drawable = ContextCompat.getDrawable(activity, drawableRes);
            setNavigationDrawable(drawable);
        }
        return true;
    }

    @NonNull
    @Override
    public Rect getInsetsPadding() {
        return new Rect(0, 0, 0, 0);
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {

    }

    /**
     * 配置Activity。主要配置Activity的用户视图对状态栏和导航栏的留白
     *
     * @param activity Activity对象，不可为空
     * @throws IllegalStateException
     */
    private void setupUserView(@NonNull Activity activity) throws IllegalStateException {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        final int childViewCount = contentViewGroup.getChildCount();
        if (childViewCount == 0) {
            throw new IllegalStateException("Plz invoke setContentView() method first!");
        } else if (childViewCount > 1) {
            throw new IllegalStateException("Plz set one view in SetContentView() or shouldn't use merge tag!!");
        }

        View userView = contentViewGroup.getChildAt(0);
        userView.setFitsSystemWindows(false);
        ViewGroup.MarginLayoutParams userViewParams = (ViewGroup.MarginLayoutParams) userView.getLayoutParams();
        userViewParams.topMargin += ImmerseGlobalConfig.getInstance().getStatusBarHeight();
        if (mActivityConfig.hasNavigtionBar()) {
            if (mActivityConfig.isNavigationAtBottom()) {
                userViewParams.bottomMargin += mActivityConfig.getNavigationBarHeight();
            } else {
                userViewParams.rightMargin += mActivityConfig.getNavigationBarWidth();
            }
        }
        userView.setLayoutParams(userViewParams);
    }

    @NonNull
    private View setupStatusBarView(@NonNull Activity activity) {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);
        return statusBarView;
    }

    @Nullable
    private View setupNavigationBarView(@NonNull Activity activity) {
        View navigationBarView = null;
        if (mActivityConfig.hasNavigtionBar()) {
            ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);

            navigationBarView = new View(activity);
            FrameLayout.LayoutParams params;
            if (mActivityConfig.isNavigationAtBottom()) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mActivityConfig.getNavigationBarHeight());
                params.gravity = Gravity.BOTTOM;
            } else {
                params = new FrameLayout.LayoutParams(mActivityConfig.getNavigationBarWidth(), FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.END;
            }
            contentViewGroup.addView(navigationBarView, params);
        }
        return navigationBarView;
    }

}
