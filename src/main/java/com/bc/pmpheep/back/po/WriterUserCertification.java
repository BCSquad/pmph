package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * @introduction WriterUserCertification 作家用户教师资格认证表
 * 
 * @author Mryang
 * 
 * @createDate 2017年9月19日 上午9:41:59
 * 
 */
@SuppressWarnings("serial")
@Alias("WriterUserCertification")
public class WriterUserCertification implements Serializable {
    // 主键
    private Long      id;
    // 作家id
    private Long      userId;
    // 对应学校id
    private Long      orgId;
    // 手机
    private String    handphone;
    // 身份证
    private String    idcard;
    // 认证进度 --- 0=未提交/1=已提交/2=被退回/3=通过
    private Short     progress;
    // 教师资格证--- 资格证图片的资源地址
    private String    cert;
    // 创建时间
    private Timestamp gmtCreate;
    // 修改时间
    private Timestamp gmtUpdate;

    /** default constructor */
    public WriterUserCertification() {
    }

    public WriterUserCertification(Long userId, Short progress) {
        this.userId = userId;
        this.progress = progress;
    }

    public WriterUserCertification(Long id) {
        super();
        this.id = id;
    }

    /** full constructor */
    public WriterUserCertification(Long userId, Long orgId, String handphone, String idcard,
    Short progress, String cert, Timestamp gmtCreate, Timestamp gmtUpdate) {
        this.userId = userId;
        this.orgId = orgId;
        this.handphone = handphone;
        this.idcard = idcard;
        this.progress = progress;
        this.cert = cert;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getHandphone() {
        return this.handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Short getProgress() {
        return this.progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
    }

    public String getCert() {
        return this.cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtUpdate() {
        return this.gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", userId:" + userId + ", orgId:" + orgId + ", handphone:" + handphone
               + ", idcard:" + idcard + ", progress:" + progress + ", cert:" + cert
               + ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
    }

}