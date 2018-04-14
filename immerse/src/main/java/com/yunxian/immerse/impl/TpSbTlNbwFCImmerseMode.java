package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.R;
import com.yunxian.immerse.manager.ActivityConfig;
import com.yunxian.immerse.manager.ImmerseGlobalConfig;
import com.yunxian.immerse.utils.WindowUtils;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import java.lang.ref.SoftReference;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/2/2 18:45
 */
@TargetApi(LOLLIPOP)
public class TpSbTlNbwFCImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final ActivityConfig mActivityConfig;

    // 兼容性StatusBar，用作设置Drawable时兼容处理使用
    private final View mCompatStatusBarView;
    @Nullable
    private final View mCompatNavigationBarView;

    private final Rect mInsetsRect = new Rect();

    public TpSbTlNbwFCImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mActivityConfig = new ActivityConfig(activity);
        Pair<View, View> viewPair = setupContentViewAndStatusBarView(activity);
        mCompatStatusBarView = viewPair.first;
        mCompatNavigationBarView = viewPair.second;

        window.setStatusBarColor(Color.TRANSPARENT);
        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        if (mCompatNavigationBarView != null) {
            mCompatNavigationBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        Activity activity = mActivityRef.get();
        if (activity != null) {
            activity.getWindow().setStatusBarColor(color);
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
        return mInsetsRect;
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {

    }

    @NonNull
    private Pair<View, View> setupContentViewAndStatusBarView(@NonNull Activity activity) throws IllegalStateException {
        mInsetsRect.top = ImmerseGlobalConfig.getInstance().getStatusBarHeight();

        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);

        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        View navigationBarView = contentViewGroup.findViewById(R.id.immerse_compat_navigation_bar);

        if (statusBarView != null) {
            return new Pair<>(statusBarView, navigationBarView);
        }

        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("Plz invode setContentView() method first!");
        }

        userView.setFitsSystemWindows(false);

        statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mInsetsRect.top);
        contentViewGroup.addView(statusBarView, params);

        navigationBarView = setupNavigationBarView(activity, contentViewGroup);

        return new Pair<>(statusBarView, navigationBarView);
    }

    @Nullable
    private View setupNavigationBarView(@NonNull Activity activity, ViewGroup contentViewGroup) throws IllegalStateException {
        View navigationBarView = null;
        if (mActivityConfig.hasNavigtionBar()) {
            navigationBarView = new View(activity);
            FrameLayout.LayoutParams params;
            if (mActivityConfig.isNavigationAtBottom()) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mActivityConfig.getNavigationBarHeight());
                params.gravity = Gravity.BOTTOM;
                mInsetsRect.bottom = mActivityConfig.getNavigationBarHeight();
            } else {
                params = new FrameLayout.LayoutParams(mActivityConfig.getNavigationBarWidth(), FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.RIGHT;
                mInsetsRect.right = mActivityConfig.getNavigationBarWidth();
            }
            contentViewGroup.addView(navigationBarView, params);
        }
        return navigationBarView;
    }

}
