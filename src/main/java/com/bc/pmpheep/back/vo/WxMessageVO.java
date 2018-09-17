package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Alias("WxMessageVO")
public class WxMessageVO implements Serializable {
    private Long id;
    private String content;
    private Boolean isDeal;
    private int  msgdbtype;
    private Long userid;
    private Timestamp gmtCreate;
    private Timestamp gmtUpdate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDeal() {
        return isDeal;
    }

    public void setDeal(Boolean deal) {
        this.isDeal = deal;
    }

    public int getMsgdbtype() {
        return msgdbtype;
    }

    public void setMsgdbtype(int msgdbtype) {
        this.msgdbtype = msgdbtype;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
