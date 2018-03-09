/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dto;

import java.io.Serializable;

/**
 * Sso请求
 *
 * @author L.X <gugia@qq.com>
 * @param <T> 泛型
 */
public class SsoRequest<T> implements Serializable {

    private String username = "interfaceuser";
    private String password = "pmph@)!^2016";
    private String language = "zh-cn";
    private String method;
    private T params;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the params
     */
    public T getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(T params) {
        this.params = params;
    }
}
