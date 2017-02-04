package com.yunxian.immerse.enumeration;

/**
 * 沉浸类型枚举
 * <p>N代表Normal；TL代表Translucent半透明；TP代表Transparent全透明；FC代表FullScreen内容全屏</p>
 * <p>SB代表StatusBAR状态栏；NB代表NavigationBar导航栏</p>
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/1/30 11:48
 */
public enum ImmerseType {

    /**
     * 普通状态栏+普通导航栏
     */
    NSB_NNB,

    /**
     * 半透明状态栏+普通导航栏
     */
    TLSB_NNB,

    /**
     * 半透明状态栏+普通导航栏+内容全屏
     */
    TLSB_NNB_FC,

    /**
     * 半透明状态栏+普通导航栏+内容全屏+EditText AdjustResize
     */
    TLSB_NNB_FC_AR,

    /**
     * 半透明状态栏+半透明导航栏
     */
    TLSB_TLNB,

    /**
     * 半透明状态栏+半透明导航栏+内容全屏
     */
    TLSB_TLNB_FC,

    /**
     * 全透明状态栏+普通导航栏
     */
    TPSB_NNB,

    /**
     * 全透明状态栏+普通导航栏+内容全屏
     */
    TPSB_NNB_FC,

    /**
     * 全透明状态栏+普通导航栏+内容全屏+EditText AdjustResize
     */
    TPSB_NNB_FC_AR,

    /**
     * 全透明状态栏+半透明导航啦
     */
    TPSB_TLNB,

    /**
     * 全透明状态栏+半透明导航栏+内容全屏
     */
    TPSB_TLNB_FC

}
