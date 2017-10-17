/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.bean;

/**
 * 用于MongoDB图片存取服务的图片类型枚举
 *
 * @author L.X <gugia@qq.com>
 */
public enum ImageType {

    /**
     * 社内用户头像
     */
    PMPH_USER_AVATAR("社内用户头像"),
    /**
     * 作家用户头像
     */
    WRITER_USER_AVATAR("作家用户头像"),
    /**
     * 教师资格证
     */
    WRITER_USER_CERT("教师资格证"),
    /**
     * 机构用户委托书
     */
    ORG_USER_PROXY("机构用户委托书"),
    /**
     * 小组头像
     */
    GROUP_AVATAR("小组头像");

    private final String type;

    private ImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
