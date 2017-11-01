/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB CMS内容对象
 * @author L.X <gugia@qq.com>
 */
@SuppressWarnings("all")
@Document(collection = "cms_content")
public class Content implements Serializable {

    @Id
    private String id;
    private String content;

    public Content() {
        super();
    }

    public Content(String content) {
        this.content = content;
    }

    public Content(String id, String content) {
        super();
        this.id = id;
        this.content = content;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
