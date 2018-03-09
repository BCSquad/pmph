/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dto;

import java.io.Serializable;

/**
 * SSO响应
 *
 * @author L.X <gugia@qq.com>
 * @param <T> 泛型
 */
public class SsoResponse<T> implements Serializable {

    private Boolean Success;
    private String Message;
    private T UserData;

    /**
     * @return the Success
     */
    public Boolean getSuccess() {
        return Success;
    }

    /**
     * @param Success the Success to set
     */
    public void setSuccess(Boolean Success) {
        this.Success = Success;
    }

    /**
     * @return the Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message the Message to set
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return the UserData
     */
    public T getUserData() {
        return UserData;
    }

    /**
     * @param UserData the UserData to set
     */
    public void setUserData(T UserData) {
        this.UserData = UserData;
    }
}
