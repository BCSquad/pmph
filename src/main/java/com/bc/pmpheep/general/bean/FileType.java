/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.bean;

/**
 * 用于MongoDB文件存取服务的文件类型枚举
 * 
 * @author L.X <gugia@qq.com>
 */
public enum FileType {

    /**
     * 小组文件
     */
    GROUP_FILE("小组文件"),
    /**
     * 消息附件
     */
    MSG_FILE("消息附件"),
    /**
     * 消息内嵌图片
     */
    MSG_IMG("消息内嵌图片"),
    /**
     * 教材通知内容附件
     */
    MATERIAL_NOTICE_ATTACHMENT("教材通知内容附件"),
    /**
     * 教材备注附件
     */
    MATERIAL_NOTE_ATTACHMENT("教材备注附件"),

    /**
     * 临床决策附件
     */
    CLINIC_DECISION_ATTACHMENT("临床决策附件"),

    /**
     * 教学大纲
     */
    SYLLABUS("教学大纲"),
    /**
     * CMS附件
     */
    CMS_ATTACHMENT("CMS附件"),
    /**
     * CMS内嵌图片
     */
    CMS_IMG("CMS内嵌图片"),
    /**
     * CMS附件
     */
    CMS_ADVERTISEMENT("CMS广告图片"),
    /**
     * shipinfengmian
     */
    BOOKVEDIO_CONER("CMS广告图片");
    
    private final String type;

    private FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
