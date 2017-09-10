package com.bc.pmpheep.back.controller;

/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件上传控制器
 *
 * @author L.X <gugia@qq.com>
 */
@Controller
public class FileUploadController {

    Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Resource
    GridFsTemplate gridFsTemplate;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 上传文件并保存在Mongodb中
     *
     * @param model 状态
     * @param file 文件
     * @return 返回上传结果
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView index(Model model, @RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView("index");
        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");
        InputStream inputStream = null;//GridFsTemplate g=new GridFsTemplate(new MongoDB, converter)
        try {
            inputStream = file.getInputStream();
            gridFsTemplate.store(inputStream, file.getOriginalFilename(), "multipart/form-data", metaData);
        } catch (IOException ex) {
            logger.error("文件上传时出现IO异常：{}", ex.getMessage());
            mv.addObject("msg", "上传失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    mv.addObject("msg", "上传成功");
                } catch (IOException ex) {
                    logger.error("文件上传时出现IO异常：{}", ex.getMessage());
                    mv.addObject("msg", "上传失败");
                }
            }
        }
        return mv;
    }
}
