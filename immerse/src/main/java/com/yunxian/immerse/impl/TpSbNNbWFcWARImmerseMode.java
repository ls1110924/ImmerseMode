package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
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
 * 全透明状态栏+普通导航栏+内容全屏+EditText Adjust-Resize模式
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/2/3 下午5:55
 */
@TargetApi(LOLLIPOP)
public class TpSbNNbWFcWARImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final ConsumeInsetsFrameLayout mNewUserViewGroup;
    // 兼容性StatusBar，用作设置Drawable时兼容处理使用
    private final View mCompatStatusBarView;

    public TpSbNNbWFcWARImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_STATUS);
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mNewUserViewGroup = new ConsumeInsetsFrameLayout(activity);
        mCompatStatusBarView = setupContentViewAndStatusBarView(activity, mNewUserViewGroup);

        window.setStatusBarColor(Color.TRANSPARENT);
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
            setStatusDrawable(ContextCompat.getDrawable(activity, drawableRes));
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
            setNavigationColor(ContextCompat.getColor(activity, colorRes));
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

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        if (operation) {
            mNewUserViewGroup.addOnInsetsChangeListener(listener);
        } else {
            mNewUserViewGroup.removeOnInsetsChangeListener(listener);
        }
    }

    @NonNull
    private View setupContentViewAndStatusBarView(@NonNull Activity activity,
                                                  @NonNull ConsumeInsetsFrameLayout newUserViewGroup) throws IllegalStateException {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("Plz invode setContentView() method first!");
        }

        newUserViewGroup.setConsumeInsets(true);
        newUserViewGroup.setFitsSystemWindows(true);
        contentViewGroup.removeView(userView);
        newUserViewGroup.addView(userView);
        contentViewGroup.addView(newUserViewGroup, 0);

        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }

}
