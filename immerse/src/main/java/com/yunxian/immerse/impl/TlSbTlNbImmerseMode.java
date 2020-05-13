package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
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
        Pair<View, View> viewPair = setupView(activity);
        mCompatStatusBarView = viewPair.first;
        mCompatNavigationBarView = viewPair.second;
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
    public void setOnInsetsChangeListener(boolean operation,
                                          @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
    }

    /**
     * 配置Activity。主要配置Activity的用户视图对状态栏和导航栏的留白
     *
     * @param activity Activity对象，不可为空
     * @return 状态栏和导航栏
     * @throws IllegalStateException
     */
    private Pair<View, View> setupView(@NonNull Activity activity) throws IllegalStateException {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);

        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        View navigationBarView = contentViewGroup.findViewById(R.id.immerse_compat_navigation_bar);

        if (statusBarView != null) {
            return new Pair<>(statusBarView, navigationBarView);
        }

        final int childViewCount = contentViewGroup.getChildCount();
        if (childViewCount == 0) {
            throw new IllegalStateException("Plz invoke setContentView() method first!");
        } else if (childViewCount > 1) {
            throw new IllegalStateException("Plz set one view in setContentView() or shouldn't use merge tag!!");
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
    private View setupStatusBarView(@NonNull Activity activity, @NonNull ViewGroup contentViewGroup) {
        View statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);
        return statusBarView;
    }

    @Nullable
    private View setupNavigationBarView(@NonNull Activity activity, @NonNull ViewGroup contentViewGroup) {
        View navigationBarView = null;
        if (mActivityConfig.hasNavigationBar()) {
            navigationBarView = new View(activity);
            navigationBarView.setId(R.id.immerse_compat_navigation_bar);
            FrameLayout.LayoutParams params;
            if (mActivityConfig.isNavigationAtBottom()) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mActivityConfig.getNavigationBarHeight());
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
