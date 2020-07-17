package com.yunxian.immerse.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;

import com.yunxian.immerse.utils.ResourcesUtils;

import java.lang.reflect.Method;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 每一个Activity页面自己特有的配置信息包装类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/31 下午8:28
 */
public class ActivityConfig {

    private static String sNavBarOverride;

    static {
        // Android allows a system property to override the presence of the navigation bar.
        // Used by the emulator.
        // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (SDK_INT >= KITKAT) {
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }

    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";


    private final Activity activity;
    private final Resources resources;
    private final float mSmallestWidthDp;
    private final int mNavigationBarWidth;

    public ActivityConfig(@NonNull Activity activity) {
        this.activity = activity;
        resources = activity.getResources();
        mSmallestWidthDp = getSmallestWidthDp(activity);
        mNavigationBarWidth = getNavigationBarWidth(activity);
    }

    public boolean hasNavigationBar() {
        return getNavigationBarHeight(activity) > 0;
    }

    public boolean isNavigationAtBottom() {
        return (mSmallestWidthDp >= 600 || resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public int getNavigationBarHeight() {
        return getNavigationBarHeight(activity);
    }

    public int getNavigationBarWidth() {
        return mNavigationBarWidth;
    }

    private float getSmallestWidthDp(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        } else {
            // TODO this is not correct, but we don't really care pre-kitkat
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        float widthDp = metrics.widthPixels / metrics.density;
        float heightDp = metrics.heightPixels / metrics.density;
        return Math.min(widthDp, heightDp);
    }

    private int getNavigationBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                String key;
                // 这里需要加上平板的判断
                if (isNavigationAtBottom()) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return ResourcesUtils.getDimensionSize(resources, key, "android");
            }
        }
        return result;
    }

    private int getNavigationBarWidth(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                return ResourcesUtils.getDimensionSize(res, NAV_BAR_WIDTH_RES_NAME, "android");
            }
        }
        return result;
    }

    @TargetApi(ICE_CREAM_SANDWICH)
    private boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

}
