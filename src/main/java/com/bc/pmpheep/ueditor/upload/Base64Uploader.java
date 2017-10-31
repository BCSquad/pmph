package com.bc.pmpheep.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.ueditor.PathFormat;
import com.bc.pmpheep.ueditor.define.AppInfo;
import com.bc.pmpheep.ueditor.define.BaseState;
import com.bc.pmpheep.ueditor.define.FileType;
import com.bc.pmpheep.ueditor.define.State;

public final class Base64Uploader {

    public State save(String content, Map<String, Object> conf, HttpServletRequest request) {

        byte[] data = decode(content);

        long maxSize = ((Long) conf.get("maxSize")).longValue();

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix("JPG");

        String savePath =
        PathFormat.parse((String) conf.get("savePath"), (String) conf.get("filename"));

        savePath = savePath + suffix;
        String physicalPath = (String) conf.get("rootPath") + savePath;

        State storageState = StorageManager.saveBinaryFile(data, physicalPath);

        if (storageState.isSuccess()) {
            ApplicationContext ctx =
            WebApplicationContextUtils.getWebApplicationContext(request.getSession()
                                                                       .getServletContext());
            FileService fileService = (FileService) ctx.getBean("fileService");
            File file = FileUpload.getFileByFilePath(physicalPath);
            String picId;
            try {
                picId = fileService.saveLocalFile(file, ImageType.SYS_MESSAGE, 1L);
            } catch (IOException e) {
                return new BaseState(false, AppInfo.IO_ERROR);
            }
            storageState.putInfo("url", "/image/" + picId);
            // storageState.putInfo("url", PathFormat.format(savePath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}