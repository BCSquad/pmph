package com.bc.pmpheep.back.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;

public class Tools {

    /**
     * 检测对象是否不为空
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    /**
     * 检测对象是否为空
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(Object obj) {
        return null == obj;
    }

    /**
     * 写txt里的单行内容
     * 
     * @param filePath 文件路径
     * @param content 写入的内容
     */
    public static void writeFile(String fileP, String content) {
        String filePath =
        String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))
        + Const.WEB_PROJECT_NAME + "/"; // 项目路径
        filePath = (filePath.trim() + fileP.trim()).substring(6).trim().replace("lib", "webapps");
        if (filePath.indexOf(":") != 1) {
            filePath = File.separator + filePath;
        }
        try {
            OutputStreamWriter write =
            new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证邮箱
     * 
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check =
            "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     * 
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex =
            Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检测KEY是否正确
     * 
     * @param paraname 传入参数
     * @param FKEY 接收的 KEY
     * @return 为空则返回true，不否则返回false
     */
    public static boolean checkKey(String paraname, String FKEY) {
        paraname = (null == paraname) ? "" : paraname;
        return MD5.md5(paraname + DateUtil.getDays() + ",fh,").equals(FKEY);
    }

    /**
     * 读取txt里的单行内容
     * 
     * @param filePath 文件路径
     */
    public static String readTxtFile(String fileP) {
        try {
            String filePath =
            String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))
            + Const.WEB_PROJECT_NAME + "/"; // 项目路径
            filePath = filePath.replaceAll("file:/", "").replace("lib", "webapps");
            filePath = filePath.replaceAll("%20", " ");
            filePath = filePath.trim() + fileP.trim();
            if (filePath.indexOf(":") != 1) {
                filePath = File.separator + filePath;
            }
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    return lineTxt;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return "";
    }

    /**
     * 
     * 判断字符串是否可以转化成数字
     * 
     * @author Mryang
     * @createDate 2017年10月8日 下午10:22:08
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 将 PageParameter的当前页数和页面大小拷贝到PageResult 对象里面去
     * 
     * @author Mryang
     * @createDate 2017年10月8日 下午10:23:51
     * @param PageParameter
     * @param pageResult
     */
    @SuppressWarnings("rawtypes")
    public static void CopyPageParameter(PageParameter pageParameter, PageResult pageResult) {
        pageResult.setPageNumber(pageParameter.getPageNumber());
        pageResult.setPageSize(pageParameter.getPageSize());
    }

}
