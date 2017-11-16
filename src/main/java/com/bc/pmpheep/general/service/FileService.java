/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import java.io.FileInputStream;
import org.apache.commons.io.FileUtils;

/**
 * MongoDB 文件存取服务
 *
 * @author L.X <gugia@qq.com>
 */
@Service
public class FileService {

    Logger logger = LoggerFactory.getLogger(FileService.class);
    final String IS_IMAGE = "is_image";
    final String PK = "pk";
    final String TYPE = "type";

    @Resource
    GridFsTemplate gridFsTemplate;

    /**
     * 保存图片
     *
     * @param file 要保存的图片
     * @param imageType 图片所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     */
    public String save(MultipartFile file, ImageType imageType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, true);
        metaData.put(TYPE, imageType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        try (InputStream inputStream = file.getInputStream()) {
            gridFSFile
                    = gridFsTemplate.store(inputStream,
                            file.getOriginalFilename(),
                            "multipart/form-data",
                            metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     * 保存文件
     *
     * @param file 要保存的文件
     * @param fileType 文件所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     */
    public String save(MultipartFile file, FileType fileType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, false);
        metaData.put(TYPE, fileType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        try (InputStream inputStream = file.getInputStream()) {
            gridFSFile
                    = gridFsTemplate.store(inputStream,
                            file.getOriginalFilename(),
                            "multipart/form-data",
                            metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     *
     * <pre>
     * 功能描述： 本地文件保存到MongoDB
     * 使用示范：
     *
     * @param file 要保存的文件
     * @param fileType 文件所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     * </pre>
     */
    public String saveLocalFile(File file, FileType fileType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, false);
        metaData.put(TYPE, fileType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        try (InputStream inputStream = FileUtils.openInputStream(file)) {
            gridFSFile = gridFsTemplate.store(inputStream, file.getName(), metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     *
     * <pre>
     * 功能描述： 本地图片保存到MongoDB
     * 使用示范：
     *
     * @param file 要保存的图片
     * @param imageType 图片所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     * </pre>
     */
    public String saveLocalFile(File file, ImageType imageType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, true);
        metaData.put(TYPE, imageType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        try (InputStream inputStream = FileUtils.openInputStream(file)) {
            gridFSFile = gridFsTemplate.store(inputStream, file.getName(), metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     *
     * <pre>
     * 功能描述： 现有平台文件迁移到MongoDB
     * 使用示范：
     *
     * @param path 旧数据库文件路径
     * @param fileType 文件所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     * </pre>
     */
    public String migrateFile(String path, FileType fileType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, false);
        metaData.put(TYPE, fileType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
//        path = path.replace("/", "\\");
        String fullpath = SQLParameters.FILE_PATH + path;
        File file = new File(fullpath);
        try (InputStream inputStream = new FileInputStream(file)) {
            gridFSFile = gridFsTemplate.store(inputStream, file.getName(), metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     *
     * <pre>
     * 功能描述： 现有平台图片迁移到MongoDB
     * 使用示范：
     *
     * @param path 旧数据库文件路径
     * @param imageType 图片所属类型
     * @param pk 对应的实体类主键(id)
     * @return 返回由MongoDB自动生成的id字符串
     * @throws IOException IO读写错误
     * </pre>
     */
    public String migrateFile(String path, ImageType imageType, long pk) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put(IS_IMAGE, true);
        metaData.put(TYPE, imageType.getType());
        metaData.put(PK, pk);
        GridFSFile gridFSFile;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
//        path = path.replace("/", "\\");
        String fullpath = SQLParameters.FILE_PATH + path;
        File file = new File(fullpath);
        try (InputStream inputStream = new FileInputStream(file)) {
            gridFSFile = gridFsTemplate.store(inputStream, file.getName(), metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    /**
     * 根据MySQL中存储的MongoDB主键获取指定文件
     *
     * @param id 文件在MongoDB中的主键
     * @return GridFSDBFile对象
     */
    public GridFSDBFile get(String id) {
        if (null == id || id.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                    CheckedExceptionResult.NULL_PARAM, "获取文件时ID为空");
        }
        return gridFsTemplate.findOne(Query.query(new GridFsCriteria("_id").is(id)));
    }

    /**
     * 根据MySQL中存储的一些MongoDB主键获取对应文件
     *
     * @param ids 主键集合
     * @return GridFSDBFile对象集合
     */
    public List<GridFSDBFile> list(List<String> ids) {
        List<GridFSDBFile> list = new ArrayList<>(ids.size());
        for (String id : ids) {
            GridFSDBFile file = get(id);
            if (null == file) {
                throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                        CheckedExceptionResult.OBJECT_NOT_FOUND,
                        "找不到id='" + id + "'对应文件");
            } else {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 根据MySQL中存储的MongoDB主键删除指定文件
     *
     * @param id 文件在MongoDB中的主键
     */
    public void remove(String id) {
        if (null == id || id.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                    CheckedExceptionResult.NULL_PARAM, "删除文件时ID为空");
        }
        gridFsTemplate.delete(Query.query(new GridFsCriteria("_id").is(id)));
    }
}
