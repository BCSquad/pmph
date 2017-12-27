/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.bo;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 * 教材申报表业务对象，用于Excel/Word(批量)导出
 *
 * @author L.X <gugia@qq.com>
 */
public class DeclarationBO {

    @ExcelHeader(header = "申报图书")
    private String textbookName;

    @ExcelHeader(header = "申报职位")
    private String presetPosition;

    @ExcelHeader(header = "姓名")
    private String realname;

    @ExcelHeader(header = "账号")
    private String username;

    @ExcelHeader(header = "性别")
    private String sex;

    @ExcelHeader(header = "出生年月")
    private String birthday;

    @ExcelHeader(header = "教龄")
    private Integer experience;

    @ExcelHeader(header = "工作单位")
    private String orgName;

    @ExcelHeader(header = "职务")
    private String position;

    @ExcelHeader(header = "职称")
    private String title;

    @ExcelHeader(header = "地址")
    private String address;

    @ExcelHeader(header = "邮编")
    private String postcode;

    @ExcelHeader(header = "联系电话")
    private String telephone;

    @ExcelHeader(header = "传真")
    private String fax;

    @ExcelHeader(header = "手机")
    private String handphone;

    @ExcelHeader(header = "邮箱")
    private String email;

    @ExcelHeader(header = "学校审核")
    private String onlineProgress;

    @ExcelHeader(header = "出版社审核")
    private String offlineProgress;

    @ExcelHeader(header = "申报单位")
    private String chosenOrgName;

    public DeclarationBO() {
    }

    /**
     * @return the textbookName
     */
    public String getTextbookName() {
        return textbookName;
    }

    /**
     * @param textbookName the textbookName to set
     */
    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }

    /**
     * @return the presetPosition
     */
    public String getPresetPosition() {
        return presetPosition;
    }

    /**
     * @param presetPosition the presetPosition to set
     */
    public void setPresetPosition(String presetPosition) {
        this.presetPosition = presetPosition;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
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
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the experience
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the handphone
     */
    public String getHandphone() {
        return handphone;
    }

    /**
     * @param handphone the handphone to set
     */
    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the onlineProgress
     */
    public String getOnlineProgress() {
        return onlineProgress;
    }

    /**
     * @param onlineProgress the onlineProgress to set
     */
    public void setOnlineProgress(String onlineProgress) {
        this.onlineProgress = onlineProgress;
    }

    /**
     * @return the offlineProgress
     */
    public String getOfflineProgress() {
        return offlineProgress;
    }

    /**
     * @param offlineProgress the offlineProgress to set
     */
    public void setOfflineProgress(String offlineProgress) {
        this.offlineProgress = offlineProgress;
    }

    /**
     * @return the chosenOrgName
     */
    public String getChosenOrgName() {
        return chosenOrgName;
    }

    /**
     * @param chosenOrgName the chosenOrgName to set
     */
    public void setChosenOrgName(String chosenOrgName) {
        this.chosenOrgName = chosenOrgName;
    }
}
