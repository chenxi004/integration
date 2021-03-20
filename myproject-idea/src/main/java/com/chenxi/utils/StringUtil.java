package com.chenxi.utils;

public class StringUtil {
	/**
     * תΪ�շ�����
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
     * �ж��Ƿ�Ϊ�մ�
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
     * �ж��Ƿ�Ϊ�մ� �������� ����ô��д�˸�һ���ķ�����������
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * ����һ����ĸ�滻Ϊ��д
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}
