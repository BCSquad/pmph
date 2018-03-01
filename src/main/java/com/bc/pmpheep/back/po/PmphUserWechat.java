package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * PmphUserWechat 实体类
 */
@Alias("PmphUserWechat")
public class PmphUserWechat implements Serializable {
    //
    private static final long serialVersionUID = -3710313820260792236L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 用户名
     */
    private String            username;
    /**
     * 微信账号
     */
    private String            wechatId;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @return the wechatId
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * @param wechatId the wechatId to set
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public PmphUserWechat() {
    }

    public PmphUserWechat(String username, String wechatId) {
        this.username = username;
        this.wechatId = wechatId;
    }

    /**
     * <pre>
     * 构造器描述：
     *
     * @param id
     * @param username
     * @param wechatId
     *</pre>
     */
    public PmphUserWechat(Long id, String username, String wechatId) {
        super();
        this.id = id;
        this.username = username;
        this.wechatId = wechatId;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "PmphUserWechat [id=" + id + ", username=" + username + ", wechatId=" + wechatId
               + "]";
    }

}
