/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.runnable;

/**
 * 微信公众号文章业务对象
 *
 * @author L.X <gugia@qq.com>
 */
public class WechatArticle {

    private String guid;
    private Boolean done = false;
    private String url;
    private String result;
    private Boolean error = false;

    public WechatArticle(String guid, String url) {
        this.guid = guid;
        this.url = url;
    }

    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return the done
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the error
     */
    public Boolean getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Boolean error) {
        this.error = error;
    }
}
