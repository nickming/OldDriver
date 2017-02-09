package com.wennuan.olddriver.util;

import android.content.Context;

import com.wennuan.olddriver.base.BaseApplication;


/**
 * nickming
 */
public class SharePreferenceUtil {
    private static final String DEFAULT_CATEGORY = "default";

    /**
     * 读取值
     *
     * @param key          键值
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String key, String defaultValue) {
        return getString(DEFAULT_CATEGORY, key, defaultValue);
    }

    /**
     * 读取值
     *
     * @param category     分类名
     * @param key          键值
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String category, String key, String defaultValue) {
        String result = defaultValue;

        Context application = BaseApplication.getsContext();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.getString(key, defaultValue);
        return result;
    }

    /**
     * 保存值
     *
     * @param key   键值
     * @param value 值
     * @return
     */
    public static boolean setString(String key, String value) {
        return setString(DEFAULT_CATEGORY, key, value);
    }

    /**
     * 保存值
     *
     * @param category 分类名
     * @param key      键值
     * @param value    值
     * @return
     */
    public static boolean setString(String category, String key, String value) {
        boolean result = false;
        Context application = BaseApplication.getsContext();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.edit().putString(key, value).commit();
        return result;
    }
}
