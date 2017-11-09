package com.bc.pmpheep.ueditor.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.ueditor.define.State;

public class Uploader {
    public HttpServletRequest  request = null;
    public Map<String, Object> conf    = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            Base64Uploader base64Uploader = new Base64Uploader();
            state =
            base64Uploader.save(this.request.getParameter(filedName), this.conf, this.request);
        } else {
            BinaryUploader binaryUploader = new BinaryUploader();
            state = binaryUploader.save(this.request, this.conf);
        }
        return state;
    }
}
