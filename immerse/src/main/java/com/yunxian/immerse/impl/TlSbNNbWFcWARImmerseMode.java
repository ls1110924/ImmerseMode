package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * 半透明状态栏+普通导航栏+内容全屏+AdjustResize的EditText模式
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/2/3 下午5:14
 */
@TargetApi(KITKAT)
public class TlSbNNbWFcWARImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final ConsumeInsetsFrameLayout mNewUserViewGroup;
    // 兼容性StatusBar，用作设置Drawable时兼容处理使用
    private final View mCompatStatusBarView;

    public TlSbNNbWFcWARImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);

        mNewUserViewGroup = new ConsumeInsetsFrameLayout(activity);
        mCompatStatusBarView = setupStatusBarView(activity, mNewUserViewGroup);

        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
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
        mCompatStatusBarView.setBackground(drawable);
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
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        if (operation) {
            mNewUserViewGroup.addOnInsetsChangeListener(listener);
        } else {
            mNewUserViewGroup.removeOnInsetsChangeListener(listener);
        }
    }

    @NonNull
    private View setupStatusBarView(@NonNull Activity activity, @NonNull ConsumeInsetsFrameLayout newUserViewGroup) {
        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("Plz invode setContentView() method first!");
        }

        newUserViewGroup.setConsumeInsets(true);
        contentViewGroup.removeView(userView);
        newUserViewGroup.addView(userView);
        contentViewGroup.addView(newUserViewGroup, 0);
        newUserViewGroup.setFitsSystemWindows(true);

        View statusBarView = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }

}
