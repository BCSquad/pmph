/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.bo;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import java.util.ArrayList;

/**
 * 教材申报表业务对象，用于Excel/Word(批量)导出
 *
 * @author L.X <gugia@qq.com>
 */
public class DeclarationEtcBO {

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

	@ExcelHeader(header = "学习经历")
	private ArrayList<DecEduExp> decEduExps;

	@ExcelHeader(header = "工作经历")
	private ArrayList<DecWorkExp> decWorkExps;

	@ExcelHeader(header = "教学经历")
	private ArrayList<DecTeachExp> decTeachExps;
        
	private DecAchievement decAchievement;

	@ExcelHeader(header = "学术兼职")
	private ArrayList<DecAcade> decAcades;

	@ExcelHeader(header = "上版教材参编情况")
	private ArrayList<DecLastPosition> decLastPositions;

	@ExcelHeader(header = "精品课程建设情况")
	private ArrayList<DecCourseConstruction> decCourseConstructions;

	@ExcelHeader(header = "主编国家级规划教材情况")
	private ArrayList<DecNationalPlan> decNationalPlans;

	@ExcelHeader(header = "教材编写情况")
	private ArrayList<DecTextbook> decTextbooks;

	@ExcelHeader(header = "科研情况")
	private ArrayList<DecResearch> decResearchs;

	public DeclarationEtcBO() {
	}

	public DeclarationEtcBO(String textbookName, String presetPosition, String realname, String username, String sex,
			String birthday, Integer experience, String orgName, String position, String title, String address,
			String postcode, String telephone, String fax, String handphone, String email, String onlineProgress,
			String offlineProgress, String chosenOrgName, ArrayList<DecEduExp> decEduExps,
			ArrayList<DecWorkExp> decWorkExps, ArrayList<DecTeachExp> decTeachExps, ArrayList<DecAcade> decAcades,
			ArrayList<DecLastPosition> decLastPositions, ArrayList<DecCourseConstruction> decCourseConstructions,
			ArrayList<DecNationalPlan> decNationalPlans, ArrayList<DecTextbook> decTextbooks,
			ArrayList<DecResearch> decResearchs) {
		this.textbookName = textbookName;
		this.presetPosition = presetPosition;
		this.realname = realname;
		this.username = username;
		this.sex = sex;
		this.birthday = birthday;
		this.experience = experience;
		this.orgName = orgName;
		this.position = position;
		this.title = title;
		this.address = address;
		this.postcode = postcode;
		this.telephone = telephone;
		this.fax = fax;
		this.handphone = handphone;
		this.email = email;
		this.onlineProgress = onlineProgress;
		this.offlineProgress = offlineProgress;
		this.chosenOrgName = chosenOrgName;
		this.decEduExps = decEduExps;
		this.decWorkExps = decWorkExps;
		this.decTeachExps = decTeachExps;
		this.decAcades = decAcades;
		this.decLastPositions = decLastPositions;
		this.decCourseConstructions = decCourseConstructions;
		this.decNationalPlans = decNationalPlans;
		this.decTextbooks = decTextbooks;
		this.decResearchs = decResearchs;
	}

	/**
	 * @return the textbookName
	 */
	public String getTextbookName() {
		return textbookName;
	}

	/**
	 * @param textbookName
	 *            the textbookName to set
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
	 * @param presetPosition
	 *            the presetPosition to set
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
	 * @param realname
	 *            the realname to set
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
	 * @param username
	 *            the username to set
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
	 * @param sex
	 *            the sex to set
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
	 * @param birthday
	 *            the birthday to set
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
	 * @param experience
	 *            the experience to set
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
	 * @param orgName
	 *            the orgName to set
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
	 * @param position
	 *            the position to set
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
	 * @param title
	 *            the title to set
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
	 * @param address
	 *            the address to set
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
	 * @param postcode
	 *            the postcode to set
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
	 * @param telephone
	 *            the telephone to set
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
	 * @param fax
	 *            the fax to set
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
	 * @param handphone
	 *            the handphone to set
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
	 * @param email
	 *            the email to set
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
	 * @param onlineProgress
	 *            the onlineProgress to set
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
	 * @param offlineProgress
	 *            the offlineProgress to set
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
	 * @param chosenOrgName
	 *            the chosenOrgName to set
	 */
	public void setChosenOrgName(String chosenOrgName) {
		this.chosenOrgName = chosenOrgName;
	}

	/**
	 * @return the decEduExps
	 */
	public ArrayList<DecEduExp> getDecEduExps() {
		return decEduExps;
	}

	/**
	 * @param decEduExps
	 *            the decEduExps to set
	 */
	public void setDecEduExps(ArrayList<DecEduExp> decEduExps) {
		this.decEduExps = decEduExps;
	}

	/**
	 * @return the decWorkExps
	 */
	public ArrayList<DecWorkExp> getDecWorkExps() {
		return decWorkExps;
	}

	/**
	 * @param decWorkExps
	 *            the decWorkExps to set
	 */
	public void setDecWorkExps(ArrayList<DecWorkExp> decWorkExps) {
		this.decWorkExps = decWorkExps;
	}

	/**
	 * @return the decTeachExps
	 */
	public ArrayList<DecTeachExp> getDecTeachExps() {
		return decTeachExps;
	}

	/**
	 * @param decTeachExps
	 *            the decTeachExps to set
	 */
	public void setDecTeachExps(ArrayList<DecTeachExp> decTeachExps) {
		this.decTeachExps = decTeachExps;
	}

    /**
     * @return the decAchievement
     */
    public DecAchievement getDecAchievement() {
        return decAchievement;
    }

    /**
     * @param decAchievement the decAchievement to set
     */
    public void setDecAchievement(DecAchievement decAchievement) {
        this.decAchievement = decAchievement;
    }

	/**
	 * @return the decAcades
	 */
	public ArrayList<DecAcade> getDecAcades() {
		return decAcades;
	}

	/**
	 * @param decAcades
	 *            the decAcades to set
	 */
	public void setDecAcades(ArrayList<DecAcade> decAcades) {
		this.decAcades = decAcades;
	}

	/**
	 * @return the decLastPositions
	 */
	public ArrayList<DecLastPosition> getDecLastPositions() {
		return decLastPositions;
	}

	/**
	 * @param decLastPositions
	 *            the decLastPositions to set
	 */
	public void setDecLastPositions(ArrayList<DecLastPosition> decLastPositions) {
		this.decLastPositions = decLastPositions;
	}

	/**
	 * @return the decCourseConstructions
	 */
	public ArrayList<DecCourseConstruction> getDecCourseConstructions() {
		return decCourseConstructions;
	}

	/**
	 * @param decCourseConstructions
	 *            the decCourseConstructions to set
	 */
	public void setDecCourseConstructions(ArrayList<DecCourseConstruction> decCourseConstructions) {
		this.decCourseConstructions = decCourseConstructions;
	}

	/**
	 * @return the decNationalPlans
	 */
	public ArrayList<DecNationalPlan> getDecNationalPlans() {
		return decNationalPlans;
	}

	/**
	 * @param decNationalPlans
	 *            the decNationalPlans to set
	 */
	public void setDecNationalPlans(ArrayList<DecNationalPlan> decNationalPlans) {
		this.decNationalPlans = decNationalPlans;
	}

	/**
	 * @return the decTextbooks
	 */
	public ArrayList<DecTextbook> getDecTextbooks() {
		return decTextbooks;
	}

	/**
	 * @param decTextbooks
	 *            the decTextbooks to set
	 */
	public void setDecTextbooks(ArrayList<DecTextbook> decTextbooks) {
		this.decTextbooks = decTextbooks;
	}

	/**
	 * @return the decResearchs
	 */
	public ArrayList<DecResearch> getDecResearchs() {
		return decResearchs;
	}

	/**
	 * @param decResearchs
	 *            the decResearchs to set
	 */
	public void setDecResearchs(ArrayList<DecResearch> decResearchs) {
		this.decResearchs = decResearchs;
	}
}
