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
    NULL_PARAM(2, "参数为空"),
    /**
     * 参数为空
     */
    ILLEGAL_PARAM(3, "参数错误"),
    /**
     * 无法找到对象
     */
    OBJECT_NOT_FOUND(4, "无法找到对象"),
    /**
     * 视图对象转换失败
     */
    VO_CONVERSION_FAILED(5, "视图对象转换失败"),
    /**
     * 创建文件失败
     */
    FILE_CREATION_FAILED(6, "创建文件失败"),
    /**
     * 创建目录失败
     */
    DIRECTORY_CREATION_FAILED(7, "创建目录失败"),
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(8, "文件上传失败"),
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILED(9, "文件下载失败"),
    /**
     * 文件下载失败
     */
    PO_ADD_FAILED(10, "持久化对象保存失败"),
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
