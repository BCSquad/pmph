/*
 * Copyright 2017 Masslink technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.service.exception;

/**
 * 自定义服务层异常，用于抛出已知异常
 *
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings("serial")
public class CheckedServiceException extends RuntimeException {

    private final String business;

    private final String result;

    public CheckedServiceException(String business, String result, String message) {
        super(message);
        this.business = business;
        this.result = result;
    }

    public CheckedServiceException(String business, String result, String message, Throwable cause) {
        super(message, cause);
        this.business = business;
        this.result = result;
    }

    public CheckedServiceException(String business, String result, Throwable cause) {
        super(cause);
        this.business = business;
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" - [业务类型: ");
        sb.append(this.business).append("] <");
        sb.append("异常结果: ").append(this.result).append("> ");
        sb.append(this.getMessage());
        return sb.toString();
    }

    public String getBusiness() {
        return business;
    }

    public String getResult() {
        return result;
    }
}
