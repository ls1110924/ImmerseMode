package com.yunxian.immerse;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yunxian.immerse.impl.NormalImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbwFCImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbwFCwARImmerseMode;
import com.yunxian.immerse.impl.TlSbTlNbImmerseMode;
import com.yunxian.immerse.impl.TlSbTlNbwFCImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbwFCwARImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbwFCImmerseMode;
import com.yunxian.immerse.impl.TpSbTlNbImmerseMode;
import com.yunxian.immerse.impl.TpSbTlNbwFCImmerseMode;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import java.util.Locale;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * 状态栏沉浸工具类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/22 下午8:17
 */
public final class ImmerseHelper {

    private final IImmerseMode immerseMode;

    public ImmerseHelper(@NonNull Activity activity, @NonNull ImmerseConfiguration immerseConfiguration) {
        if (SDK_INT < KITKAT) {
            immerseMode = new NormalImmerseMode(activity, immerseConfiguration);
        } else if (SDK_INT < LOLLIPOP) {
            switch (immerseConfiguration.mImmerseTypeInKK) {
                case NSB_NNB:
                    immerseMode = new NormalImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB:
                    immerseMode = new TlSbNNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB_FC:
                    immerseMode = new TlSbNNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB_FC_AR:
                    immerseMode = new TlSbNNbwFCwARImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_TLNB:
                    immerseMode = new TlSbTlNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_TLNB_FC:
                    immerseMode = new TlSbTlNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                default:
                    throw new IllegalAccessError(String.format(Locale.US, "Kitkat don't support %s mode!", immerseConfiguration.mImmerseTypeInKK.toString()));
            }
        } else {
            switch (immerseConfiguration.mImmerseTypeInL) {
                case NSB_NNB:
                    immerseMode = new NormalImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB:
                    immerseMode = new TlSbNNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB_FC:
                    immerseMode = new TlSbNNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_NNB_FC_AR:
                    immerseMode = new TlSbNNbwFCwARImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_TLNB:
                    immerseMode = new TlSbTlNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TLSB_TLNB_FC:
                    immerseMode = new TlSbTlNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                case TPSB_NNB:
                    immerseMode = new TpSbNNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TPSB_NNB_FC:
                    immerseMode = new TpSbNNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                case TPSB_NNB_FC_AR:
                    immerseMode = new TpSbNNbwFCwARImmerseMode(activity, immerseConfiguration);
                    break;
                case TPSB_TLNB:
                    immerseMode = new TpSbTlNbImmerseMode(activity, immerseConfiguration);
                    break;
                case TPSB_TLNB_FC:
                    immerseMode = new TpSbTlNbwFCImmerseMode(activity, immerseConfiguration);
                    break;
                default:
                    throw new IllegalAccessError(String.format(Locale.US, "Lollipop don't support %s mode!", immerseConfiguration.mImmerseTypeInL.toString()));
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色值
     */
    public void setStatusColor(@ColorInt int color) {
        immerseMode.setStatusColor(color);
    }

    /**
     * 设置状态栏颜色
     *
     * @param colorRes 颜色值资源Id
     */
    public void setStatusColorRes(@ColorRes int colorRes) {
        immerseMode.setStatusColorRes(colorRes);
    }

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawable Drawable对象
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setStatusDrawable(@Nullable Drawable drawable) {
        return immerseMode.setStatusDrawable(drawable);
    }

    /**
     * 如果支持的话，设置状态栏背景Drawable
     *
     * @param drawableRes Drawable资源Id
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setStatusDrawableRes(@DrawableRes int drawableRes) {
        return immerseMode.setStatusDrawableRes(drawableRes);
    }

    /**
     * 设置导航栏颜色
     *
     * @param color 颜色值
     */
    public void setNavigationColor(@ColorInt int color) {
        immerseMode.setNavigationColor(color);
    }

    /**
     * 设置导航栏颜色
     *
     * @param colorRes 颜色值资源Id
     */
    public void setNavigationColorRes(@ColorRes int colorRes) {
        immerseMode.setNavigationColorRes(colorRes);
    }

    /**
     * 如果支持的话，设置导航栏背景Drawable
     *
     * @param drawable Drawable对象
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setNavigationDrawable(@Nullable Drawable drawable) {
        return immerseMode.setNavigationDrawable(drawable);
    }

    /**
     * 如果支持的话，设置导航栏背景Drawable
     *
     * @param drawableRes Drawable资源Id
     * @return 设置成功，返回true；否则返回false
     */
    public boolean setNavigationDrawableRes(@DrawableRes int drawableRes) {
        return immerseMode.setNavigationDrawableRes(drawableRes);
    }

    /**
     * 用于当内容全屏沉浸时，获取Insets应有留白
     * <p>top对应状态栏的高度；left留空；right对应导航栏位于屏幕右侧时的宽度；bottom对应导航栏位于屏幕底部时的高度</p>
     * <p>注：导航栏存在三种情况：无；居于底部；居于右侧</p>
     *
     * @return Insets留白
     */
    @NonNull
    public Rect getInsetsPadding() {
        return immerseMode.getInsetsPadding();
    }

    /**
     * 设置Insets监听器，以监听fitSystemWindow事件
     * <p>该方法并不使用所有情况，现已弃用。如需获取Insets做留白用，可使用{@link #getInsetsPadding()}方法</p>
     *
     * @param operation true为注册，false为反注册
     * @param listener  Insets监听器
     */
    @Deprecated
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        immerseMode.setOnInsetsChangeListener(operation, listener);
    }

}
