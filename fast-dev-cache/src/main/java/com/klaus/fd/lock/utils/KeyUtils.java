package com.klaus.fd.lock.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author Klaus
 */
public final class KeyUtils {

    public static String getCompositeKey(String prefix, String name, String key) {
        return StrUtil.join(":", prefix, name, key);
    }

    public static String getCompositeWildcardKey(String prefix, String name) {
        return StrUtil.join(":", prefix, name, "*");
    }

    public static String getCompositeWildcardKey(String key) {
        return StrUtil.join(":", key, "*");
    }

}
