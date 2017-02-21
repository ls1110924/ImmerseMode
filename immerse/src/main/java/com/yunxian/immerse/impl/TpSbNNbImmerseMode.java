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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yunxian.immerse.IImmerseMode;
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
 * 全透明状态栏普通导航栏
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/31 下午3:41
 */
@TargetApi(LOLLIPOP)
public class TpSbNNbImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    // 兼容性StatusBar，用作设置Drawable时兼容处理使用
    private final View mCompatStatusBarView;

    public TpSbNNbImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setupUserView(activity);
        mCompatStatusBarView = setupStatusBarView(activity);
        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
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
        Activity activity = mActivityRef.get();
        if (activity != null) {
            activity.getWindow().setNavigationBarColor(color);
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
        return false;
    }

    @Override
    public boolean setNavigationDrawableRes(@DrawableRes int drawableRes) {
        return false;
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

}

