package com.bc.pmpheep.back.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 
 * <pre>
 * 功能描述：Text 工具类
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
public final class TextUtil {
    private TextUtil() {
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
}
