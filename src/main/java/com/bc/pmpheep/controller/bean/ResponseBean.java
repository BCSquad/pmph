/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.controller.bean;

import java.io.Serializable;

/**
 * 控制器通用返回格式
 *
 * @author L.X <gugia@qq.com>
 * @param <T> 要返回的数据类型
 */
public class ResponseBean<T> implements Serializable {

    /**
     * 成功
     */
    public static final int SUCCESS = 1;

    /**
     * 失败
     */
    public static final int FAILURE = 2;

    /**
     * 错误的请求参数
     */
    public static final int WRONG_REQ_PARA = 3;

    /**
     * 没有操作权限
     */
    public static final int NO_PERMISSION = 4;

    private int code = SUCCESS;
    private String msg = "success";
    private T data;

    public ResponseBean() {
        super();
    }

    public ResponseBean(T data) {
        super();
        this.data = data;
    }

    public ResponseBean(Throwable ex) {
        super();
        this.msg = ex.toString();
        this.code = FAILURE;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
}
