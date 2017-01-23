package com.yunxian.immerse.utils;

import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * 资源相关工具类
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/21 下午7:58
 */
public final class ResourcesUtils {

    private ResourcesUtils() {
        throw new IllegalStateException("shouldn't init instance");
    }

    /**
     * 获取指定包名下指定Dimen类型key对应的像素大小
     *
     * @param res        Resource对象
     * @param key        Dimen类型的Key值
     * @param defPackage 所属的包名，系统默认包名为<font color='red'>android</font>
     * @return Dimen值对应的像素大小，若未找到或出错，返回-1
     */
    public static int getDimensionSize(@NonNull Resources res, @Nullable String key, @NonNull String defPackage) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(defPackage)) {
            return -1;
        }
        int resourceId = res.getIdentifier(key, "dimen", defPackage);
        return resourceId > 0 ? res.getDimensionPixelSize(resourceId) : -1;
    }

    /**
     * 获取指定包名下指定Dimen类型key对应的像素大小，如果key不存在，则返回默认值
     *
     * @param res             Resource对象
     * @param key             Dimen类型的Key值
     * @param defPackage      所属的包名，系统默认包名为<font color='red'>android</font>
     * @param defaultDimenRes 默认值对应的资源Id。当key不存在时，则返回该默认值
     * @return
     */
    public static int getDimensionSize(@NonNull Resources res, @Nullable String key, @NonNull String defPackage, @DimenRes int defaultDimenRes) {
        int dimensions = getDimensionSize(res, key, defPackage);
        return dimensions < 0 ? res.getDimensionPixelSize(defaultDimenRes) : dimensions;
    }

}
