package com.bc.pmpheep.back.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * 
 * 创建人：admin
 * 
 * 创建时间：2015年12月23日
 * 
 * @version
 */
public class FileUpload {

    /**
     * @param file //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public static String fileUp(MultipartFile file, String filePath, String fileName) {
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
                extName =
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileName + extName;
    }

    /**
     * 写文件到当前目录的upload目录中
     * 
     * @param in
     * @param fileName
     * @throws IOException
     */
    public static String copyFile(InputStream in, String dir, String realName) throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }

    /**
     * 
     * <pre>
     * 功能描述：根据文件路径获取文件对象
     * 使用示范：
     *
     * @param filePath 文件路径
     * @return  file
     * </pre>
     */
    public static File getFileByFilePath(String filePath) {
        File file = null;
        if (StringUtil.notEmpty(filePath)) {
            file = FileUtils.getFile(filePath);
        }
        return file;
    }
}
