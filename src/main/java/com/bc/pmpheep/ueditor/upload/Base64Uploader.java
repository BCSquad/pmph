package com.bc.pmpheep.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.ueditor.PathFormat;
import com.bc.pmpheep.ueditor.define.AppInfo;
import com.bc.pmpheep.ueditor.define.BaseState;
import com.bc.pmpheep.ueditor.define.FileType;
import com.bc.pmpheep.ueditor.define.State;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

public final class Base64Uploader {
    static final String   IS_IMAGE = "is_image";
    static final String   PK       = "pk";
    static final String   TYPE     = "type";
    @Resource
    static GridFsTemplate gridFsTemplate;

    public static State save(String content, Map<String, Object> conf) {

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
            File file = FileUpload.getFileByFilePath(physicalPath);
            DBObject metaData = new BasicDBObject();
            metaData.put(IS_IMAGE, true);
            metaData.put(TYPE, "系统消息图片");
            metaData.put(PK, 1L);
            GridFSFile gridFSFile = null;
            try (InputStream inputStream = FileUtils.openInputStream(file)) {
                gridFSFile = gridFsTemplate.store(inputStream, file.getName(), metaData);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            storageState.putInfo("url", "//image//" + gridFSFile.getId().toString());
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