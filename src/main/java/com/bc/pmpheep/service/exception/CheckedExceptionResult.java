/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.service.exception;

/**
 * 业务异常返回结果
 * 
 * @author L.X <gugia@qq.com>
 */
public enum CheckedExceptionResult {

    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功"),
    /**
     * 参数错误
     */
    NULL_PARAM(2, "参数错误"),
    /**
     * 参数为空
     */
    ILLEGAL_PARAM(3, "参数为空"),
    /**
     * 无法找到对象
     */
    OBJECT_NOT_FOUND(4, "无法找到对象"),
    /**
     * 视图对象转换失败
     */
    VO_CONVERSION_FAILED(5, "视图对象转换失败"),
    /**
     * 用户SESSION为空
     */
    USER_SESSION(30, "用户SESSION为空");

    private final int    value;
    private final String message;

    private CheckedExceptionResult(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static CheckedExceptionResult getEnumByValue(int value) {
        for (CheckedExceptionResult result : CheckedExceptionResult.values()) {
            if (result.getValue() == value) {
                return result;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
    // public static final String SUCCESS = "操作成功";
    //
    // public static final String NULL_PARAM = "参数为空";
    //
    // public static final String ILLEGAL_PARAM = "参数错误";
    //
    // public static final String VO_CONVERSION_FAILED = "视图对象转换失败";
}
