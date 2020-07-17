package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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

import com.yunxian.immerse.IImmerseMode;
import com.yunxian.immerse.R;
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
 * 半透明状态栏半透明导航栏且内容全屏模式
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/2/2 17:49
 */
@TargetApi(KITKAT)
public class TlSbTlNbwFCImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final ActivityConfig mActivityConfig;

    private final View mCompatStatusBarView;
    // 当一些手机没有导航栏时，该对象为空
    @Nullable
    private final View mCompatNavigationBarView;

    public TlSbTlNbwFCImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);

        mActivityConfig = new ActivityConfig(activity);
        Pair<View, View> viewPair = setupView(activity);
        mCompatStatusBarView = viewPair.first;
        mCompatNavigationBarView = viewPair.second;

        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
        if (mCompatNavigationBarView != null) {
            mCompatNavigationBarView.setBackgroundColor(Color.TRANSPARENT);
        }
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
        Rect mInsetsRect = new Rect();
        mInsetsRect.top = ImmerseGlobalConfig.getInstance().getStatusBarHeight();
        if (mActivityConfig.hasNavigationBar()) {
            if (mActivityConfig.isNavigationAtBottom()) {
                mInsetsRect.bottom = mActivityConfig.getNavigationBarHeight();
            } else {
                mInsetsRect.right = mActivityConfig.getNavigationBarWidth();
            }
        }
        return mInsetsRect;
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
    }

    @NonNull
    private Pair<View, View> setupView(@NonNull Activity activity) throws IllegalStateException {
        ViewGroup contentViewGroup = activity.findViewById(android.R.id.content);

        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        View navigationBarView = contentViewGroup.findViewById(R.id.immerse_compat_navigation_bar);

        if (statusBarView != null) {
            return new Pair<>(statusBarView, navigationBarView);
        }

        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("plz invoke setContentView() method first!");
        }

        userView.setFitsSystemWindows(false);

        statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        navigationBarView = setupNavigationBarView(activity, contentViewGroup);

        return new Pair<>(statusBarView, navigationBarView);
    }

    @Nullable
    private View setupNavigationBarView(@NonNull Activity activity, @NonNull ViewGroup contentViewGroup) {
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
                params.gravity = Gravity.RIGHT;
            }
            contentViewGroup.addView(navigationBarView, params);
        }
        return navigationBarView;
    }
}
