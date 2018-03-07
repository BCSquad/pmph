package com.bc.pmpheep.general.controller;

/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;

/**
 * 文件上传控制器
 * 
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings("all")
@Controller
public class FileUploadController {

    Logger      logger = LoggerFactory.getLogger(FileUploadController.class);

    @Resource
    FileService fileService;

    /**
     * 上传文件并保存在Mongodb中
     * 
     * @param model 状态
     * @param file 文件
     * @return 返回上传结果
     */
    @ResponseBody
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public ResponseBean upload(Model model, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseBean(fileService.save(file, FileType.GROUP_FILE, 0));
        } catch (IOException ex) {
            return new ResponseBean(ex);
        }
    }

    /**
     * 上传文件并保存在Mongodb中
     * 
     * @param file 文件
     * @return 返回上传结果
     */
    @ResponseBody
    @RequestMapping(value = "/file/image/upload", method = RequestMethod.POST)
    public ResponseBean imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseBean(fileService.save(file, ImageType.CMS_CONTENT_COVER_IMG, 0));
        } catch (IOException ex) {
            return new ResponseBean(ex);
        }
    }
}
