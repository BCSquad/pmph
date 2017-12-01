/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bc.pmpheep.back.service.CmsExtraService;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * 文件下载控制器
 * 
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class FileDownLoadController {

    Logger                          logger = LoggerFactory.getLogger(FileDownLoadController.class);

    @Resource
    FileService                     fileService;
    @Resource
    PmphGroupFileService            groupFileService;
    @Resource
    CmsExtraService                 cmsExtraService;
    @Resource
    MaterialNoticeAttachmentService materialNoticeAttachmentService;
    @Resource
    MaterialNoteAttachmentService   materialNoteAttachmentService;

    /**
     * 普通文件下载
     * 
     * @param id 文件在MongoDB中的id
     * @param response 服务响应
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/file/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("id") String id, HttpServletResponse response)
    throws UnsupportedEncodingException {

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的文件", id);
            return;
        }
        response.setHeader("Content-Disposition",
                           "attachment;fileName="
                           + new String(file.getFilename().getBytes("utf-8"), "ISO8859-1"));
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.error("文件下载时出现IO异常：{}", ex.getMessage());
        }
    }

    /**
     * 
     * <pre>
	 * 功能描述：普通文件下载(更新下载数)
	 * 使用示范：
	 *
	 * &#64;param type 模块类型
	 * &#64;param id 文件在MongoDB中的id
	 * &#64;param response 服务响应
	 * </pre>
     * 
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/file/{type}/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("type") String type, @PathVariable("id") String id,
    HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的文件", id);
            return;
        }
        response.setHeader("Content-Disposition",
                           "attachment;fileName="
                           + new String(file.getFilename().getBytes("utf-8"), "ISO8859-1"));
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
            if (Const.CMS_TYPE.equals(type)) {// CMS附件
                cmsExtraService.updateCmsExtraDownLoadCountsByAttachment(id);
            }
            if (Const.MATERIAL_NOTICE_TYPE.equals(type)) {// 教材通知
                materialNoticeAttachmentService.updateMaterialNoticeAttachmentDownLoadCountsByAttachment(id);
            }
            if (Const.MATERIAL_NOTE_TYPE.equals(type)) {// 教材备注
                materialNoteAttachmentService.updateMaterialNoteAttachmentDownLoadCountsByAttachment(id);
            }
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
    public ResponseBean download(@PathVariable("id") String id,
    @RequestParam("groupId") long groupId, HttpServletResponse response) {
        if (groupId < 1) {
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                                              CheckedExceptionResult.FILE_DOWNLOAD_FAILED,
                                              "小组id错误（负数或零）");
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        GridFSDBFile file = fileService.get(id);
        if (null == file) {
            logger.warn("未找到id为'{}'的文件", id);
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                                              CheckedExceptionResult.FILE_DOWNLOAD_FAILED,
                                              "未找到对应文件");
        }
        String file_name;
        try {
            file_name = new String(file.getFilename().getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;fileName=" + file_name);
        } catch (UnsupportedEncodingException e) {
            logger.warn("修改编码格式的时候失败");
        }
        try (OutputStream out = response.getOutputStream()) {
            file.writeTo(out);
            out.flush();
            out.close();
            return new ResponseBean(groupFileService.updatePmphGroupFileOfDown(groupId, id));
        } catch (IOException ex) {
            logger.warn("文件下载时出现IO异常：{}", ex.getMessage());
            throw new CheckedServiceException(CheckedExceptionBusiness.FILE,
                                              CheckedExceptionResult.FILE_DOWNLOAD_FAILED,
                                              "文件在传输时中断");
        }

    }
}
