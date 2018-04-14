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
import com.yunxian.immerse.R;
import com.yunxian.immerse.manager.ImmerseGlobalConfig;
import com.yunxian.immerse.utils.DrawableUtils;
import com.yunxian.immerse.utils.WindowUtils;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import java.lang.ref.SoftReference;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * 半透明状态栏普通导航栏的全屏模式
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/31 下午3:17
 */
@TargetApi(KITKAT)
public class TlSbNNbwFCImmerseMode implements IImmerseMode {

    private final SoftReference<Activity> mActivityRef;

    private final View mCompatStatusBarView;

    private final Rect mInsetsRect = new Rect();

    public TlSbNNbwFCImmerseMode(@NonNull Activity activity) {
        mActivityRef = new SoftReference<>(activity);

        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);

        mCompatStatusBarView = setupStatusBarView(activity);

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
        Activity activity = mActivityRef.get();
        if (activity != null && SDK_INT >= LOLLIPOP) {
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
        return mInsetsRect;
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {

    }

    @NonNull
    private View setupStatusBarView(@NonNull Activity activity) throws IllegalStateException {
        mInsetsRect.top = ImmerseGlobalConfig.getInstance().getStatusBarHeight();

        ViewGroup contentViewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        if (statusBarView != null) {
            return statusBarView;
        }

        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("plz invoke setContentView() method first!");
        }

        userView.setFitsSystemWindows(false);

        statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mInsetsRect.top);
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }
}
