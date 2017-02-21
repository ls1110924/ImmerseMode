package com.yunxian.immerse.enumeration;

import com.yunxian.immerse.ImmerseConfiguration;

/**
 * 沉浸配置选项的枚举值
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/2/3 上午9:04
 */
public enum ImmerseConfigType {

    /**
     * 常规
     */
    NORMAL(ImmerseConfiguration.NORMAL),

    /**
     * 半透明
     */
    TRANSLUCENT(ImmerseConfiguration.TRANSLUCENT),

    /**
     * 全透明
     */
    TRANSPARENT(ImmerseConfiguration.TRANSPARENT);

    private final int id;

    ImmerseConfigType(int id) {
        this.id = id;
    }

    /**
     * 根据指定的参数转换为合适的枚举值
     *
     * @param id ID
     * @return 沉浸配置枚举值
     * @throws IllegalStateException 如果参数非法，将抛出异常
     */
    public static ImmerseConfigType ofImmerseConfigType(int id) throws IllegalStateException {
        for (ImmerseConfigType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalStateException("Plz use legal parameter!");
    }

    /**
     * 根据指定的参数转换为合适的枚举值
     *
     * @param id ID
     * @return 沉浸配置枚举值
     */
    public static ImmerseConfigType getImmerseConfigType(int id) {
        for (ImmerseConfigType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return NORMAL;
    }

}
