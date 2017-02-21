package com.yunxian.immerse;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.yunxian.immerse.enumeration.ImmerseConfigType;
import com.yunxian.immerse.enumeration.ImmerseType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 沉浸配置参数
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/2/3 上午9:01
 */
public final class ImmerseConfiguration {

    final ImmerseType mImmerseTypeInKK;
    final ImmerseType mImmerseTypeInL;

    private ImmerseConfiguration(@NonNull ImmerseType immerseTypeInKK, @NonNull ImmerseType immerseTypeInL) {
        this.mImmerseTypeInKK = immerseTypeInKK;
        this.mImmerseTypeInL = immerseTypeInL;
    }

    public static final int NORMAL = 1;
    public static final int TRANSLUCENT = 2;
    public static final int TRANSPARENT = 3;

    public static class Builder {

        @IntDef({NORMAL, TRANSLUCENT})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ImmerseConfigType4KK {
        }

        @IntDef({NORMAL, TRANSLUCENT, TRANSPARENT})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ImmerseConfigType4L {
        }

        private ImmerseConfigType mStatusBarModeInKK = ImmerseConfigType.NORMAL;
        private ImmerseConfigType mNavigationBarModeInKK = ImmerseConfigType.NORMAL;
        private boolean mFullScreenInKK = false;
        private boolean mAdjustResizeInKK = false;

        private ImmerseConfigType mStatusBarModeInL = ImmerseConfigType.NORMAL;
        private ImmerseConfigType mNavigationBarModeInL = ImmerseConfigType.NORMAL;
        private boolean mFullScreenInL = false;
        private boolean mAdjustResizeInL = false;

        public Builder() {
        }

        public Builder setStatusBarModeInKK(@ImmerseConfigType4KK int statusBarModeInKK) {
            this.mStatusBarModeInKK = ImmerseConfigType.getImmerseConfigType(statusBarModeInKK);
            return this;
        }

        public Builder setNavigationBarModeInKK(@ImmerseConfigType4KK int navigationBarModeInKK) {
            this.mNavigationBarModeInKK = ImmerseConfigType.getImmerseConfigType(navigationBarModeInKK);
            return this;
        }

        public Builder setFullScreenInKK(boolean fullScreenInKK) {
            this.mFullScreenInKK = fullScreenInKK;
            return this;
        }

        public Builder setAdjustResizeInKK(boolean adjustResizeInKK) {
            this.mAdjustResizeInKK = adjustResizeInKK;
            return this;
        }

        public Builder setStatusBarModeInL(@ImmerseConfigType4L int statusBarModeInL) {
            this.mStatusBarModeInL = ImmerseConfigType.getImmerseConfigType(statusBarModeInL);
            return this;
        }

        public Builder setNavigationBarModeInL(@ImmerseConfigType4L int navigationBarModeInL) {
            this.mNavigationBarModeInL = ImmerseConfigType.getImmerseConfigType(navigationBarModeInL);
            return this;
        }

        public Builder setFullScreenInL(boolean fullScreenInL) {
            this.mFullScreenInL = fullScreenInL;
            return this;
        }

        public Builder setAdjustResizeInL(boolean adjustResizeInL) {
            this.mAdjustResizeInL = adjustResizeInL;
            return this;
        }

        public ImmerseConfiguration build() {
            ImmerseType immerseTypeInKK;
            ImmerseType immerseTypeInL;

            if (mStatusBarModeInKK == ImmerseConfigType.TRANSLUCENT && mFullScreenInKK && mAdjustResizeInKK) {
                immerseTypeInKK = ImmerseType.TLSB_NNB_FC_AR;
            } else if (mStatusBarModeInKK == ImmerseConfigType.TRANSLUCENT && mNavigationBarModeInKK == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInKK = mFullScreenInKK ? ImmerseType.TLSB_TLNB_FC : ImmerseType.TLSB_TLNB;
            } else if (mStatusBarModeInKK == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInKK = mFullScreenInKK ? ImmerseType.TLSB_NNB_FC : ImmerseType.TLSB_NNB;
            } else {
                immerseTypeInKK = ImmerseType.NSB_NNB;
            }

            if (mStatusBarModeInL == ImmerseConfigType.TRANSLUCENT && mFullScreenInL && mAdjustResizeInL) {
                immerseTypeInL = ImmerseType.TLSB_NNB_FC_AR;
            } else if (mStatusBarModeInL == ImmerseConfigType.TRANSPARENT && mFullScreenInL && mAdjustResizeInL) {
                immerseTypeInL = ImmerseType.TPSB_NNB_FC_AR;
            } else if (mStatusBarModeInL == ImmerseConfigType.TRANSPARENT && mNavigationBarModeInL == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInL = mFullScreenInL ? ImmerseType.TPSB_TLNB_FC : ImmerseType.TPSB_TLNB;
            } else if (mStatusBarModeInL == ImmerseConfigType.TRANSPARENT) {
                immerseTypeInL = mFullScreenInL ? ImmerseType.TPSB_NNB_FC : ImmerseType.TPSB_NNB;
            } else if (mStatusBarModeInL == ImmerseConfigType.TRANSLUCENT && mNavigationBarModeInL == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInL = mFullScreenInL ? ImmerseType.TLSB_TLNB_FC : ImmerseType.TLSB_TLNB;
            } else if (mStatusBarModeInL == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInL = mFullScreenInL ? ImmerseType.TLSB_NNB_FC : ImmerseType.TLSB_NNB;
            } else {
                immerseTypeInL = ImmerseType.NSB_NNB;
            }

            return new ImmerseConfiguration(immerseTypeInKK, immerseTypeInL);
        }

    }

}
