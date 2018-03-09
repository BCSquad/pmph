/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.general.dto;

import java.io.Serializable;

/**
 * SSO用户数据传输对象
 *
 * @author L.X <gugia@qq.com>
 */
public class SsoUser implements Serializable{

    /**
     * 登录名(UserName、Email、Mobile 至少要提交一个)
     */
    private String UserName;

    /**
     * 邮箱(UserName、Email、Mobile 至少要提交一个)
     */
    private String Email;
    /**
     * 手机号码(UserName、Email、Mobile 至少要提交一个)
     */
    private String Mobile;
    /**
     * 密码（必填）
     */
    private String Password;
    /**
     * 密码是否密文，传入Y或N，默认为N
     */
    private String PwdIsEncryption;
    /**
     * 用户状态,默认为1<br/>
     * 1表示启用，2表示邮箱已邮件验证，4表示手机号码已短信验证<br/>
     * 多个状态请使用位或,如:<br/>
     * 1|2=3，提交参数Status=3表示该用户是启用的并且邮箱已经验证<br/>
     * 1|4=5，提交参数Status=5表示用户已启用且手机号码已短信验证
     *
     */
    private Integer Status;
    /**
     * 昵称
     */
    private String NickName;
    /**
     * 真实姓名
     */
    private String RealName;
    /**
     * 固定电话
     */
    private String Phone;
    /**
     * 性别，M表示男，F表示女
     */
    private String Gender;
    /**
     * 生日,日期字符串(yyyy-MM-dd格式,如：1990-01-01)
     */
    private String Birthday;
    /**
     * 通讯地址
     */
    private String Address;

    /**
     * @return the UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param UserName the UserName to set
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return the Mobile
     */
    public String getMobile() {
        return Mobile;
    }

    /**
     * @param Mobile the Mobile to set
     */
    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the PwdIsEncryption
     */
    public String getPwdIsEncryption() {
        return PwdIsEncryption;
    }

    /**
     * @param PwdIsEncryption the PwdIsEncryption to set
     */
    public void setPwdIsEncryption(String PwdIsEncryption) {
        this.PwdIsEncryption = PwdIsEncryption;
    }

    /**
     * @return the Status
     */
    public Integer getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    /**
     * @return the NickName
     */
    public String getNickName() {
        return NickName;
    }

    /**
     * @param NickName the NickName to set
     */
    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    /**
     * @return the RealName
     */
    public String getRealName() {
        return RealName;
    }

    /**
     * @param RealName the RealName to set
     */
    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    /**
     * @return the Phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * @param Phone the Phone to set
     */
    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    /**
     * @return the Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     * @param Gender the Gender to set
     */
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    /**
     * @return the Birthday
     */
    public String getBirthday() {
        return Birthday;
    }

    /**
     * @param Birthday the Birthday to set
     */
    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }
}
