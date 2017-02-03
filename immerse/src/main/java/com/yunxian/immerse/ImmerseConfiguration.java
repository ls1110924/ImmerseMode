package com.yunxian.immerse;

import android.support.annotation.NonNull;

import com.yunxian.immerse.enumeration.ImmerseConfigType;
import com.yunxian.immerse.enumeration.ImmerseType;

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

    public static class Builder {

        private ImmerseConfigType mStatusBarModeInKK = ImmerseConfigType.NORMAL;
        private ImmerseConfigType mNavigationBarModeInKK = ImmerseConfigType.NORMAL;
        private boolean mFullScreenInKK = false;

        private ImmerseConfigType mStatusBarModeInL = ImmerseConfigType.NORMAL;
        private ImmerseConfigType mNavigationBarModeInL = ImmerseConfigType.NORMAL;
        private boolean mFullScreenInL = false;

        public Builder() {
        }

        public Builder setStatusBarModeInKK(@NonNull ImmerseConfigType statusBarModeInKK) {
            this.mStatusBarModeInKK = statusBarModeInKK;
            return this;
        }

        public Builder setNavigationBarModeInKK(@NonNull ImmerseConfigType navigationBarModeInKK) {
            this.mNavigationBarModeInKK = navigationBarModeInKK;
            return this;
        }

        public Builder setFullScreenInKK(boolean fullScreenInKK) {
            this.mFullScreenInKK = fullScreenInKK;
            return this;
        }

        public Builder setStatusBarModeInL(@NonNull ImmerseConfigType statusBarModeInL) {
            this.mStatusBarModeInL = statusBarModeInL;
            return this;
        }

        public Builder setNavigationBarModeInL(@NonNull ImmerseConfigType navigationBarModeInL) {
            this.mNavigationBarModeInL = navigationBarModeInL;
            return this;
        }

        public Builder setFullScreenInL(boolean fullScreenInL) {
            this.mFullScreenInL = fullScreenInL;
            return this;
        }

        public ImmerseConfiguration build() {
            ImmerseType immerseTypeInKK;
            ImmerseType immerseTypeInL;

            if (mStatusBarModeInKK == ImmerseConfigType.TRANSLUCENT && mNavigationBarModeInKK == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInKK = mFullScreenInKK ? ImmerseType.TLSB_TLNB_FC : ImmerseType.TLSB_TLNB;
            } else if (mStatusBarModeInKK == ImmerseConfigType.TRANSLUCENT) {
                immerseTypeInKK = mFullScreenInKK ? ImmerseType.TLSB_NNB_FC : ImmerseType.TLSB_NNB;
            } else {
                immerseTypeInKK = ImmerseType.NSB_NNB;
            }

            if (mStatusBarModeInL == ImmerseConfigType.TRANSPARENT && mNavigationBarModeInL == ImmerseConfigType.TRANSLUCENT) {
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
