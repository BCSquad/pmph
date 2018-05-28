package com.bc.pmpheep.back.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <pre>
 * 功能描述：字符串工具类
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-10-19
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
public final class StringUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static void main(String[] args) {
        System.out.println(toAllCheck(null));
        System.out.println(toAllCheck(""));
        System.out.println(toAllCheck("   "));
        System.out.println(toAllCheck("a b c  defg"));
        System.out.println(toAllCheck("abcdefg"));
    }

    /**
     * 将字符串拼装装成全局可搜索量；如(null/" "-->null;张-->张;王 麻子-->王%麻%子)
     * 
     * @author Mryang
     * @createDate 2017年11月23日 上午9:41:04
     * @param str
     * @return
     */
    public static String toAllCheck(String str) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        str = str.replace(" ", "");
        if (str.length() == 1) {
            return "%"+str+"%";
        }
        StringBuilder strTemp = new StringBuilder("%");
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            strTemp.append(temp+"%");
        }
        return strTemp.toString();
    }

    /**
     * 十进制非负整数 转换成8位二进制字符串，左边不够用0补齐
     * 
     * @author Mryang
     * @createDate 2017年11月22日 上午10:25:40
     * @param p
     * @return
     */
    public static String tentToBinary(Integer p) {
        int digit = 8;
        if (null == p) {
            return "00000000";
        }
        String str = Integer.toBinaryString(p);
        if (null == str) {
            return "00000000";
        } else {
            StringBuilder temp = new StringBuilder("");
            for (int i = 0; i < digit - str.length(); i++) {
                temp.append("0");
            }
            return temp.append(str).toString();
        }
    }

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
     * 将以逗号分隔的字符串转换成字符串数组
     * 
     * @param valStr
     * @return String[]
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

            i++;
        }
        return returnStr;
    }

    /**
     * 
     * <pre>
	 * 功能描述：获取字符串长度
	 * 使用示范：
	 *
	 * &#64;param str
	 * &#64;return
	 * </pre>
     */
    public static int strLength(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.length();
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 
     * <pre>
	 * 功能描述：得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 使用示范：
	 *
	 * &#64;param s 需要得到长度的字符串
	 * &#64;return int 得到的字符串长度
	 * </pre>
     */
    public static int length(String s) {
        if (isEmpty(s)) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 
     * <pre>
	 * 功能描述：得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
	 * 使用示范：
	 *
	 * &#64;param s 需要得到长度的字符串
	 * &#64;return 得到的字符串长度
	 * </pre>
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        // 进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 
     * Description:解析Excel文件时动态判断单元格类型并返回String字符串
     * 
     * @author:lyc
     * @date:2017年11月30日上午10:47:52
     * @param
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cell) {
        if (null == cell) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("0");
        int type = cell.getCellType();
        String value = "";
        switch (type) {
        case 0:// 数字 Cell.CELL_TYPE_NUMERIC
            value = String.valueOf(df.format(cell.getNumericCellValue())).trim();
            break;
        case 1:// 字符串Cell.CELL_TYPE_STRING
            value = cell.getStringCellValue().trim();
            break;
        case 2:// 公式 Cell.CELL_TYPE_FORMULA
            value = cell.getCellFormula().trim();
            break;
        case 3:// 空值 Cell.CELL_TYPE_BLANK
            value = "";
            break;
        case 4:// Boolean Cell.CELL_TYPE_BOOLEAN
            Boolean v = cell.getBooleanCellValue();
            value = v == null ? "" : String.valueOf(v);
            break;
        case 5:// 错误 Cell.CELL_TYPE_ERROR
            value = "";
            break;
        default:
            value = "";
            break;
        }
        return value;
    }

    /***
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
     * 
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        // String fromSource = "X-Real-IP";
        // String ip = request.getHeader("X-Real-IP");
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        // fromSource = "X-Forwarded-For";
        // ip = request.getHeader(fromSource);
        // if (!StringUtils.isEmpty(ip)) {
        // String[] tmps = ip.split(",");
        // if (tmps.length > 0) {
        // ip = tmps[0];
        // }
        // }
        // }
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        // fromSource = "Proxy-Client-IP";
        // ip = request.getHeader(fromSource);
        // }
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        // fromSource = "WL-Proxy-Client-IP";
        // ip = request.getHeader(fromSource);
        // }
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        // ip = request.getRemoteAddr();
        // fromSource = "request.getRemoteAddr";
        // }
        // return ip;
        String ip = request.getHeader("x-forwarded-for");
        // LOGGER.info("x-forwarded-for={}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            // LOGGER.info("Proxy-Client-IP={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            // LOGGER.info("WL-Proxy-Client-IP={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            // LOGGER.info("RemoteAddr-IP={}", ip);
        }
        if (StringUtil.notEmpty(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 判断字符串是否是整形
     * @param string
     * @return
     */
    public static boolean isInt(String string) {
        if (string == null)
            return false;

        String regEx1 = "[\\-|\\+]?\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

}
