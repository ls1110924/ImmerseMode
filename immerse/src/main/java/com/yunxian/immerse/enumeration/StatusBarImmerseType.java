package com.yunxian.immerse.enumeration;

/**
 * 状态栏沉浸类型枚举
 *
 * @author AShuai
 * @email ls1110924@163.com
 * @date 17/1/21 下午7:22
 */
public enum StatusBarImmerseType {

    /**
     * 普通模式
     * <p>不限制SDK使用版本，但只有5.0系统可以改变状态栏颜色</p>
     */
    NORMAL,

    /**
     * 半透明模式，自带状态栏遮罩
     * <p>支持4.4及以上。4.4是一层渐变遮罩蒙版，5.0及以上是一层半透明蒙版</p>
     */
    TRANSLUCENT,

    /**
     * 透明模式，无状态栏遮罩
     * <p>支持5.0及以上系统</p>
     */
    TRANSPARENT

}
