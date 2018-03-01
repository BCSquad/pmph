package com.bc.pmpheep.wechat.po;

/**
 * js_ticket
 * 
 */
public class WXjsTicket {
    // 接口访问凭证
    private String jsTicket;
    // 凭证有效期，单位：秒
    private int    jsTicketExpiresIn;

    public String getJsTicket() {
        return jsTicket;
    }

    public void setJsTicket(String jsTicket) {
        this.jsTicket = jsTicket;
    }

    public int getJsTicketExpiresIn() {
        return jsTicketExpiresIn;
    }

    public void setJsTicketExpiresIn(int jsTicketExpiresIn) {
        this.jsTicketExpiresIn = jsTicketExpiresIn;
    }

}
