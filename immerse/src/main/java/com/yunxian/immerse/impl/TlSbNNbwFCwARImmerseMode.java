package com.yunxian.immerse.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yunxian.immerse.ImmerseConfiguration;
import com.yunxian.immerse.R;
import com.yunxian.immerse.manager.ImmerseGlobalConfig;
import com.yunxian.immerse.utils.ViewUtils;
import com.yunxian.immerse.utils.WindowUtils;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import static android.os.Build.VERSION.SDK_INT;
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
public class TlSbNNbwFCwARImmerseMode extends AbsImmerseMode {

    private final ConsumeInsetsFrameLayout mNewUserViewGroup;
    // 兼容性StatusBar，模拟状态栏
    private final View mCompatStatusBarView;

    @Nullable
    private Rect mInsetsRect = null;

    public TlSbNNbwFCwARImmerseMode(@NonNull Activity activity, @NonNull ImmerseConfiguration immerseConfiguration) {
        super(activity, immerseConfiguration);
        Window window = activity.getWindow();
        WindowUtils.clearWindowFlags(window, FLAG_TRANSLUCENT_NAVIGATION);
        WindowUtils.addWindowFlags(window, FLAG_TRANSLUCENT_STATUS);

        if (immerseConfiguration.lightStatusBar && SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtils.addSystemUiFlags(window.getDecorView(), View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mNewUserViewGroup = new ConsumeInsetsFrameLayout(activity);
        mCompatStatusBarView = setupView(activity, mNewUserViewGroup);
        mCompatStatusBarView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setStatusColor(@ColorInt int color) {
        mCompatStatusBarView.setBackgroundColor(generateCompatStatusBarColor(color));
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

    @NonNull
    @Override
    public Rect getInsetsPadding() {
        if (mInsetsRect == null) {
            mInsetsRect = new Rect();
            mInsetsRect.top = ImmerseGlobalConfig.getInstance().getStatusBarHeight();
        }
        return mInsetsRect;
    }

    @Override
    public void setOnInsetsChangeListener(boolean operation,
                                          @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        if (operation) {
            mNewUserViewGroup.addOnInsetsChangeListener(listener);
        } else {
            mNewUserViewGroup.removeOnInsetsChangeListener(listener);
        }
    }

    @NonNull
    private View setupView(@NonNull Activity activity, @NonNull ConsumeInsetsFrameLayout newUserViewGroup) {
        ViewGroup contentViewGroup = activity.findViewById(android.R.id.content);

        View statusBarView = contentViewGroup.findViewById(R.id.immerse_compat_status_bar);
        if (statusBarView != null) {
            return statusBarView;
        }

        View userView = contentViewGroup.getChildAt(0);
        if (userView == null) {
            throw new IllegalStateException("plz invoke setContentView() method first!");
        }

        newUserViewGroup.setFitsSystemWindows(true);
        newUserViewGroup.setConsumeInsets(true);
        contentViewGroup.removeView(userView);
        newUserViewGroup.addView(userView);
        contentViewGroup.addView(newUserViewGroup, 0);

        statusBarView = new View(activity);
        statusBarView.setId(R.id.immerse_compat_status_bar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ImmerseGlobalConfig.getInstance().getStatusBarHeight());
        contentViewGroup.addView(statusBarView, params);

        return statusBarView;
    }

}
