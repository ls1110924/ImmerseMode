package com.yunxian.immerse;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunxian.immerse.enumeration.ImmerseConfigType;
import com.yunxian.immerse.enumeration.ImmerseType;
import com.yunxian.immerse.enumeration.StatusBarImmerseType;
import com.yunxian.immerse.impl.NormalImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbWFcImmerseMode;
import com.yunxian.immerse.impl.TlSbNNbWFcWARImmerseMode;
import com.yunxian.immerse.impl.TlSbTlNbImmerseMode;
import com.yunxian.immerse.impl.TlSbTlNbWFcImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbWFcWARImmerseMode;
import com.yunxian.immerse.impl.TpSbNNbwFcImmerseMode;
import com.yunxian.immerse.impl.TpSbTlNbImmerseMode;
import com.yunxian.immerse.impl.TpSbTlNbWFcImmerseMode;
import com.yunxian.immerse.impl.TranslucentImmerseMode;
import com.yunxian.immerse.impl.TransparentImmerseMode;
import com.yunxian.immerse.widget.ConsumeInsetsFrameLayout;

import java.util.Locale;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * 状态栏沉浸工具类
 * <p>
 * 本工具类主要提供三种类型设置参数，分别为非全屏，全屏不调整adjustResize，全屏且调整adjustResize
 * <ol>
 * <li>对于非全屏类型：无任何问题，用户内容正常布局，且不影响状态栏着色。
 * 本工具类已占用fitSystemWindow属性，故用户不必使用</li>
 * <li>对于全屏不调整adjustResize类型：用户内容布局延伸至状态栏之下，
 * 用户自行处理状态栏之下的内容以及fitSystemWindow属性的应用。
 * 注：页面不可有EditText元素，否则会影响软键盘弹出。</li>
 * <li>对于全屏且调整adjustResize类型：用户内容布局延伸至状态栏之下，
 * 用户可自行处理状态栏之下的内容，但该工具类已占用fitSystemWindow属性，用户不必使用。
 * 如需使用，则需调用{@link #setOnInsetsChangeListener(boolean, ConsumeInsetsFrameLayout.OnInsetsChangeListener)}
 * 方法进行监听处理</li>
 * </ol>
 * </p>
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/22 下午8:17
 */
public final class ImmerseHelper {

    private final IImmerseMode immerseMode;

    public ImmerseHelper(@NonNull Activity activity) {
        this(activity, StatusBarImmerseType.NORMAL, StatusBarImmerseType.NORMAL, false, false);
    }

    public ImmerseHelper(@NonNull Activity activity, @NonNull StatusBarImmerseType levelInKK,
                         @NonNull StatusBarImmerseType levelInL) {
        this(activity, levelInKK, levelInL, false, false);
    }

    public ImmerseHelper(@NonNull Activity activity, @NonNull StatusBarImmerseType levelInKK,
                         @NonNull StatusBarImmerseType levelInL, boolean fullScreen) {
        this(activity, levelInKK, levelInL, fullScreen, false);
    }

    /**
     * 沉浸模式的助手工具类
     *
     * @param activity     待应用沉浸模式的Activity
     * @param levelInKK    应用于4.4版本的模式
     * @param levelInL     应用于5.0及一上版本的模式
     * @param fullScreen   true为全屏，用户内容布局延伸到状态栏之下；
     *                     否则用户内容正常布局，但本工具类仍然提供方法对状态栏进行着色
     * @param adjustResize true当全屏时是否处理软键盘的AdjustResize问题
     */
    public ImmerseHelper(@NonNull Activity activity, @NonNull StatusBarImmerseType levelInKK,
                         @NonNull StatusBarImmerseType levelInL, boolean fullScreen, boolean adjustResize) {
        if (SDK_INT < KITKAT) {
            immerseMode = new NormalImmerseMode(activity);
        } else if (SDK_INT < LOLLIPOP) {
            switch (levelInKK) {
                case NORMAL:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TRANSLUCENT:
                    immerseMode = new TranslucentImmerseMode(activity, fullScreen, adjustResize);
                    break;
                case TRANSPARENT:
                    throw new IllegalAccessError("Kitkat don't support transparent mode!");
                default:
                    throw new IllegalArgumentException("Plz set correct parameter!");
            }
        } else {
            switch (levelInL) {
                case NORMAL:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TRANSLUCENT:
                    immerseMode = new TranslucentImmerseMode(activity, fullScreen, adjustResize);
                    break;
                case TRANSPARENT:
                    immerseMode = new TransparentImmerseMode(activity, fullScreen, adjustResize);
                    break;
                default:
                    throw new IllegalArgumentException("Plz set correct parameter!");
            }
        }
    }

    public ImmerseHelper(@NonNull Activity activity, @NonNull ImmerseConfiguration immerseConfiguration) {
        this(activity, immerseConfiguration.mImmerseTypeInKK, immerseConfiguration.mImmerseTypeInL);
    }

    public ImmerseHelper(@NonNull Activity activity, @NonNull ImmerseType immerseTypeInKK,
                         @NonNull ImmerseType immerseTypeInL) {
        if (SDK_INT < KITKAT) {
            immerseMode = new NormalImmerseMode(activity);
        } else if (SDK_INT < LOLLIPOP) {
            switch (immerseTypeInKK) {
                case NSB_NNB:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TLSB_NNB:
                    immerseMode = new TlSbNNbImmerseMode(activity);
                    break;
                case TLSB_NNB_FC:
                    immerseMode = new TlSbNNbWFcImmerseMode(activity);
                    break;
                case TLSB_NNB_FC_AR:
                    immerseMode = new TlSbNNbWFcWARImmerseMode(activity);
                    break;
                case TLSB_TLNB:
                    immerseMode = new TlSbTlNbImmerseMode(activity);
                    break;
                case TLSB_TLNB_FC:
                    immerseMode = new TlSbTlNbWFcImmerseMode(activity);
                    break;
                default:
                    throw new IllegalAccessError(String.format(Locale.US, "Kitkat don't support %s mode!", immerseTypeInKK.toString()));
            }
        } else {
            switch (immerseTypeInL) {
                case NSB_NNB:
                    immerseMode = new NormalImmerseMode(activity);
                    break;
                case TLSB_NNB:
                    immerseMode = new TlSbNNbImmerseMode(activity);
                    break;
                case TLSB_NNB_FC:
                    immerseMode = new TlSbNNbWFcImmerseMode(activity);
                    break;
                case TLSB_NNB_FC_AR:
                    immerseMode = new TlSbNNbWFcWARImmerseMode(activity);
                    break;
                case TLSB_TLNB:
                    immerseMode = new TlSbTlNbImmerseMode(activity);
                    break;
                case TLSB_TLNB_FC:
                    immerseMode = new TlSbTlNbWFcImmerseMode(activity);
                    break;
                case TPSB_NNB:
                    immerseMode = new TpSbNNbImmerseMode(activity);
                    break;
                case TPSB_NNB_FC:
                    immerseMode = new TpSbNNbwFcImmerseMode(activity);
                    break;
                case TPSB_NNB_FC_AR:
                    immerseMode = new TpSbNNbWFcWARImmerseMode(activity);
                    break;
                case TPSB_TLNB:
                    immerseMode = new TpSbTlNbImmerseMode(activity);
                    break;
                case TPSB_TLNB_FC:
                    immerseMode = new TpSbTlNbWFcImmerseMode(activity);
                    break;
                default:
                    throw new IllegalAccessError(String.format(Locale.US, "Lollipop don't support %s mode!", immerseTypeInKK.toString()));
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
     * 设置Insets监听器，以监听fitSystemWindow事件
     *
     * @param operation true为注册，false为反注册
     * @param listener  Insets监听器
     */
    public void setOnInsetsChangeListener(boolean operation, @Nullable ConsumeInsetsFrameLayout.OnInsetsChangeListener listener) {
        immerseMode.setOnInsetsChangeListener(operation, listener);
    }

}
