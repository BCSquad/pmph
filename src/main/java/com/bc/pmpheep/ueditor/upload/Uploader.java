package com.bc.pmpheep.ueditor.upload;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.ueditor.define.State;

public class Uploader {
    public HttpServletRequest  request = null;
    public Map<String, Object> conf    = null;
    @Resource
    FileService                fileService;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;
        BinaryUploader binaryUploader = new BinaryUploader();
        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName), this.conf);
        } else {
            state = binaryUploader.save(this.request, this.conf);
        }
        return state;
    }
}
