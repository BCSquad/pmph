/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import com.bc.pmpheep.general.service.FileService;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MongoDB 图片控制器
 *
 * @author L.X <gugia@qq.com>
 */
@Controller
public class ImageController {

    Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Resource
    FileService fileService;

    /**
     * 头像显示
     *
     * @param id 图片在MongoDB中的id
     * @param response 服务响应
     */
    @RequestMapping(value = "/avatar/{id}", method = RequestMethod.GET)
    public void avatar(@PathVariable("id") String id, HttpServletResponse response) {
        response.setContentType("image/png");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的图片文件", id);
            return;
        }
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.error("文件下载时出现IO异常：{}", ex.getMessage());
        }
    }
}
