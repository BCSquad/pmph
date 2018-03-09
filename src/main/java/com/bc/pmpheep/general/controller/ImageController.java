/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * MongoDB 图片控制器
 * 
 * @author L.X <gugia@qq.com>
 */
@RestController
public class ImageController {

    Logger      logger = LoggerFactory.getLogger(ImageController.class);

    @Resource
    FileService fileService;

    /**
     * 图片显示
     * 
     * @param id 图片在MongoDB中的id
     * @param response 服务响应
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ResponseBean avatar(@PathVariable("id") String id, HttpServletResponse response) {
        response.setContentType("image/png");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            // logger.warn("未找到id为'{}'的图片文件", id);
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.FILE,
                                                                CheckedExceptionResult.NULL_PARAM,
                                                                "未找到id为" + id + "的图片文件"));
        }
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            return new ResponseBean(ex);
            // logger.error("文件下载时出现IO异常：{}", ex.getMessage());
        } catch (Exception ex) {
            logger.warn("图片查看时出现异常：{}", ex.getMessage());
            throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK,
                                              CheckedExceptionResult.OBJECT_NOT_FOUND, "图片不存在");
            // ResponseBean responseBean = new ResponseBean(ex);
            // responseBean.setMsg("图片不存在");
            // responseBean.setCode(ResponseBean.MONGO_EXCEPTION);
            // return responseBean;
        }
        return new ResponseBean();
    }

}
