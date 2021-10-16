package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.R;
import com.yunxian.immerse.manager.ActivityConfig;
import com.yunxian.immerse.manager.ImmerseGlobalConfig;
import com.yunxian.immerse.utils.ViewUtils;
import com.yunxian.immerse.utils.WindowUtils;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * 全透明状态栏半透明导航栏的沉浸模式
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/2/2 17:54
 */
@TargetApi(LOLLIPOP)
    public class TpSbTlNbImmerseMode extends AbsImmerseMode {

    private final ActivityConfig mActivityConfig;

    // 兼容性StatusBar，用作设置Drawable时兼容处理使用
    private final View mCompatStatusBarView;
    @Nullable
    private final View mCompatNavigationBarView;

    public TpSbTlNbImmerseMode(@NonNull Activity activity, @NonNull ImmerseConfiguration immerseConfiguration) {
        super(activity, immerseConfiguration);
        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewUtils.addSystemUiFlags(window.getDecorView(), View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (immerseConfiguration.lightStatusBar && SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.addSystemUiFlags(window.getDecorView(), View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mActivityConfig = new ActivityConfig(activity);
        Pair<View, View> viewPair = setupView(activity);
        mCompatStatusBarView = viewPair.first;
        mCompatNavigationBarView = viewPair.second;

        assert mCompatStatusBarView != null;
        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            activity.getWindow().setStatusBarColor(generateCompatStatusBarColor(color));
            mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setStatusColorRes(@ColorRes int colorRes) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            setStatusColor(ContextCompat.getColor(activity, colorRes));
        }
    }

    @Override
    public boolean setStatusDrawable(@Nullable Drawable drawable) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            mCompatStatusBarView.setBackground(drawable);
        }
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
     */
    private Pair<View, View> setupView(@NonNull Activity activity) throws IllegalStateException {
        ViewGroup contentViewGroup = activity.findViewById(android.R.id.content);

        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        View navigationBarView = contentViewGroup.findViewById(R.id.immerse_compat_navigation_bar);

        if (statusBarView != null) {
            return new Pair<>(statusBarView, navigationBarView);
        }

        final int childViewCount = contentViewGroup.getChildCount();
        if (childViewCount == 0) {
            throw new IllegalStateException("plz invoke setContentView() method first!");
        } else if (childViewCount > 1) {
            throw new IllegalStateException("plz set one view in setContentView() or shouldn't use merge tag!!");
        }

        View userView = contentViewGroup.getChildAt(0);
        userView.setFitsSystemWindows(false);
        ViewGroup.MarginLayoutParams userViewParams = (ViewGroup.MarginLayoutParams) userView.getLayoutParams();
        userViewParams.topMargin += ImmerseGlobalConfig.getInstance().getStatusBarHeight();
        if (mActivityConfig.hasNavigationBar()) {
            if (mActivityConfig.isNavigationAtBottom()) {
                userViewParams.bottomMargin += mActivityConfig.getNavigationBarHeight();
            } else {
                userViewParams.rightMargin += mActivityConfig.getNavigationBarWidth();
            }
        }
        userView.setLayoutParams(userViewParams);

        statusBarView = setupStatusBarView(activity, contentViewGroup);
        navigationBarView = setupNavigationBarView(activity, contentViewGroup);
        return new Pair<>(statusBarView, navigationBarView);
    }

    @NonNull
    private View setupStatusBarView(@NonNull Activity activity, ViewGroup contentViewGroup) {
        View statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }

    @Nullable
    private View setupNavigationBarView(@NonNull Activity activity, ViewGroup contentViewGroup) {
        View navigationBarView = null;
        if (mActivityConfig.hasNavigationBar()) {
            navigationBarView = new View(activity);
            navigationBarView.setId(R.id.immerse_compat_navigation_bar);
            FrameLayout.LayoutParams params;
            if (mActivityConfig.isNavigationAtBottom()) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        mActivityConfig.getNavigationBarHeight());
                params.gravity = Gravity.BOTTOM;
            } else {
                params = new FrameLayout.LayoutParams(mActivityConfig.getNavigationBarWidth(),
                        FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.END;
            }
            contentViewGroup.addView(navigationBarView, params);
        }
        return navigationBarView;
    }

}
