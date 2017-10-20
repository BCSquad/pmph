package com.bc.pmpheep.back.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 功能描述： 下载文件
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
public class FileDownload {

    /**
     * @param response
     * @param filePath //文件完整路径(包括文件名和扩展名)
     * @param fileName //下载后看到的文件名
     * @return 文件名
     */
    public static void fileDownload(final HttpServletResponse response, String filePath,
    String fileName) throws Exception {

        byte[] data = FileUtil.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();

    }

}
