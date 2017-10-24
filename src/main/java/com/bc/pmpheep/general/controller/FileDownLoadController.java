/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件下载控制器
 *
 * @author L.X <gugia@qq.com>
 */
@Controller
public class FileDownLoadController {

    Logger logger = LoggerFactory.getLogger(FileDownLoadController.class);

    @Resource
    FileService fileService;
    @Resource
    PmphGroupFileService groupFileService;

    /**
     * 普通文件下载
     *
     * @param id 文件在MongoDB中的id
     * @param response 服务响应
     */
    @RequestMapping(value = "/file/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("id") String id, HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的文件", id);
            return;
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getFilename());
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.error("文件下载时出现IO异常：{}", ex.getMessage());
        }
    }

    /**
     * 小组文件下载
     *
     * @param id 图片在MongoDB中的id
     * @param groupId 小组id
     * @param response 服务响应
     * @return ResponseBean对象
     */
    @RequestMapping(value = "/groupfile/download/{id}", method = RequestMethod.GET)
    public ResponseBean download(@PathVariable("id") String id, @RequestParam("groupId") long groupId, HttpServletResponse response) {
        if (groupId < 1) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE, CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "小组id错误（负数或零）");
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的文件", id);
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE, CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "未找到对应文件");
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getFilename());
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.warn("文件下载时出现IO异常：{}", ex.getMessage());
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE, CheckedExceptionResult.FILE_DOWNLOAD_FAILED, "文件在传输时中断");
        }
        return new ResponseBean(groupFileService.updatePmphGroupFileOfDown(groupId, id));
    }
}