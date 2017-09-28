/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.service;

import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
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

/**
 * MongoDB 文件存取服务
 *
 * @author L.X <gugia@qq.com>
 */
@Service
public class FileService {

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Resource
    GridFsTemplate gridFsTemplate;

    public String save(MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");
        GridFSFile gridFSFile;
        try (InputStream inputStream = file.getInputStream()) {
            gridFSFile = gridFsTemplate.store(inputStream, file.getOriginalFilename(), "multipart/form-data", metaData);
            inputStream.close();
        }
        return gridFSFile.getId().toString();
    }

    public GridFSDBFile get(String id) {
        if (null == id || id.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                    CheckedExceptionResult.NULL_PARAM, "获取文件时ID为空");
        }
        return gridFsTemplate.findOne(Query.query(new GridFsCriteria("_id").is(id)));
    }

    public List<GridFSDBFile> list(List<String> ids) {
        List<GridFSDBFile> list = new ArrayList<>(ids.size());
        for (String id : ids) {
            GridFSDBFile file = get(id);
            if (null == file) {
                throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                        CheckedExceptionResult.OBJECT_NOT_FOUND, "找不到id='" + id + "'对应文件");
            } else {
                list.add(file);
            }
        }
        return list;
    }

    public void remove(String id) {
        if (null == id || id.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                    CheckedExceptionResult.NULL_PARAM, "删除文件时ID为空");
        }
        gridFsTemplate.delete(Query.query(new GridFsCriteria("_id").is(id)));
    }
}
