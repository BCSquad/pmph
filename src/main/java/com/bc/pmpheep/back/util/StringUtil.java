package com.bc.pmpheep.back.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 
 * <pre>
 * 功能描述：字符串工具类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public final class StringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为数字
     */
    public static boolean isNumeric(String str) {
        return NumberUtils.isDigits(str);
    }

    /**
     * 正向查找指定字符串
     */
    public static int indexOf(String str, String searchStr, boolean ignoreCase) {
        if (ignoreCase) {
            return StringUtils.indexOfIgnoreCase(str, searchStr);
        } else {
            return str.indexOf(searchStr);
        }
    }

    /**
     * 反向查找指定字符串
     */
    public static int lastIndexOf(String str, String searchStr, boolean ignoreCase) {
        if (ignoreCase) {
            return StringUtils.lastIndexOfIgnoreCase(str, searchStr);
        } else {
            return str.lastIndexOf(searchStr);
        }
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     * 
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 字符串转换为字符串数组
     * 
     * @param str 字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 
     * <pre>
     * 功能描述：获取字符串长度
     * 使用示范：
     *
     * @param str
     * @return
     * </pre>
     */
    public static int strLength(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.length();
    }

    public static void main(String[] args) {
        System.out.println(indexOf("rewqreqwr", "q", true));
    }
}
