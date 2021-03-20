package com.chenxi.utils;

public class StringUtil {
	/**
     * 转为驼峰命名
     * @param str
     * @return string
     */
    public static String camelName(String str) {
        if (!isEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0, len = str.length(); i < len; i++) {
                if (str.charAt(i) == '_') {
                    while (str.charAt(i + 1) == '_') {
                        i++;
                    }
                    stringBuilder.append(("" + str.charAt(++i)).toUpperCase());
                } else {
                    stringBuilder.append(str.charAt(i));
                }
            }
            return stringBuilder.toString();
        }
        return str;
    }

    /**
     * 判断是否为空串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str != null && str.length() > 0) {
            for (int i = 0, len = str.length(); i < len; i++) {
                if (!Character.isSpaceChar(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否为空串 ？！！！ 我怎么又写了个一样的方法？！！！
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 将第一个字母替换为大写
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}
