/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.bo;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecIntention;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.po.DecMoocDigital;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.vo.DecExtensionVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 教材申报表业务对象，用于Excel/Word(批量)导出
 *
 * @author L.X <gugia@qq.com>
 */
public class DeclarationEtcBO {

	@ExcelHeader(header = "申报图书")
	private List<String> textbookName;

	@ExcelHeader(header = "申报职位")
	private List<String> presetPosition;

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

	@ExcelHeader(header = "学历")
	private String degree;

	@ExcelHeader(header = "邮箱")
	private String email;

	@ExcelHeader(header = "证件类型")
	private String idtype;

	@ExcelHeader(header = "证件号码")
	private String idcard;

	@ExcelHeader(header = "专业特长")
	private String expertise;

	//提交日期
	@ExcelHeader(header = "提交日期")
	private String commitDate;

	@ExcelHeader(header = "是否服从调剂")
	private String isDispensed;

	@ExcelHeader(header = "是否参与本科教学评估认证")
	private String isUtec;

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

	@ExcelHeader(header = "个人成就")
	private DecAchievement decAchievement;

	@ExcelHeader(header = "学术兼职")
	private ArrayList<DecAcade> decAcades;

	@ExcelHeader(header = "本套上版教材参编情况")
	private ArrayList<DecLastPosition> decLastPositions;

	@ExcelHeader(header = "精品课程建设情况")
	private ArrayList<DecCourseConstruction> decCourseConstructions;

	@ExcelHeader(header = "主编国家级规划教材情况")
	private ArrayList<DecNationalPlan> decNationalPlans;

	@ExcelHeader(header = "人卫社教材编写情况")
	private ArrayList<DecTextbookPmph> decTextbookPmphs;

	@ExcelHeader(header = "其他社教材编写情况")
	private ArrayList<DecTextbook> decTextbooks;

	@ExcelHeader(header = "参加人卫慕课、数字教材编写情况")
	private DecMoocDigital decMoocDigital;

	@ExcelHeader(header = "科研情况")
	private ArrayList<DecResearch> decResearchs;

	@ExcelHeader(header = "学术专著")
	private ArrayList<DecMonograph> decMonographs;

	@ExcelHeader(header = "出版行业获奖情况")
	private ArrayList<DecPublishReward> publishRewards;

	@ExcelHeader(header = "SCI论文投稿及影响因子情况")
	private ArrayList<DecSci> decScis;

	@ExcelHeader(header = "临床医学获奖情况")
	private ArrayList<DecClinicalReward> decClinicalRewards;

	@ExcelHeader(header = "学术荣誉授予情况")
	private ArrayList<DecAcadeReward> decAcadeRewards;

	@ExcelHeader(header = "编写内容意向")
	private DecIntention decIntention;

	@ExcelHeader(header = "作家扩展项")
	private ArrayList<DecExtensionVO> decExtensionVOs;

	private Long materialId;

	public DeclarationEtcBO() {
	}

	public DeclarationEtcBO(String realname, String username, String sex, String birthday, Integer experience, String commitDate,
			String orgName, String position, String title, String address, String postcode, String telephone,
			String fax, String handphone, String degree, String email, String idtype, String idcard, String expertise,
			String isDispensed, String isUtec, String onlineProgress, String offlineProgress, String chosenOrgName,
			ArrayList<DecEduExp> decEduExps, ArrayList<DecWorkExp> decWorkExps, ArrayList<DecTeachExp> decTeachExps,
			DecAchievement decAchievement, ArrayList<DecAcade> decAcades, ArrayList<DecLastPosition> decLastPositions,
			ArrayList<DecCourseConstruction> decCourseConstructions, ArrayList<DecNationalPlan> decNationalPlans,
			ArrayList<DecTextbookPmph> decTextbookPmphs, DecMoocDigital decMoocDigital,
			ArrayList<DecTextbook> decTextbooks, ArrayList<DecResearch> decResearchs,
			ArrayList<DecMonograph> decMonographs, ArrayList<DecPublishReward> publishRewards,
			ArrayList<DecSci> decScis, ArrayList<DecClinicalReward> decClinicalRewards,
			ArrayList<DecAcadeReward> decAcadeRewards, ArrayList<DecExtensionVO> decExtensionVOs,
			DecIntention decIntention) {
		super();
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
		this.degree = degree;
		this.email = email;
		this.idtype = idtype;
		this.idcard = idcard;
		this.expertise = expertise;
		this.isDispensed = isDispensed;
		this.isUtec = isUtec;
		this.onlineProgress = onlineProgress;
		this.offlineProgress = offlineProgress;
		this.chosenOrgName = chosenOrgName;
		this.decEduExps = decEduExps;
		this.decWorkExps = decWorkExps;
		this.decTeachExps = decTeachExps;
		this.decAchievement = decAchievement;
		this.decAcades = decAcades;
		this.decLastPositions = decLastPositions;
		this.decCourseConstructions = decCourseConstructions;
		this.decNationalPlans = decNationalPlans;
		this.decTextbookPmphs = decTextbookPmphs;
		this.decMoocDigital = decMoocDigital;
		this.decTextbooks = decTextbooks;
		this.decResearchs = decResearchs;
		this.decMonographs = decMonographs;
		this.publishRewards = publishRewards;
		this.decScis = decScis;
		this.decClinicalRewards = decClinicalRewards;
		this.decAcadeRewards = decAcadeRewards;
		this.decExtensionVOs = decExtensionVOs;
		this.decIntention = decIntention;
		this.commitDate = commitDate;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public List<String> getTextbookName() {
		return textbookName;
	}

	public void setTextbookName(List<String> textbookName) {
		this.textbookName = textbookName;
	}

	public List<String> getPresetPosition() {
		return presetPosition;
	}

	public void setPresetPosition(List<String> presetPosition) {
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
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
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
	 * @return the idtype
	 */
	public String getIdtype() {
		return idtype;
	}

	/**
	 * @param idtype
	 *            the idtype to set
	 */
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	/**
	 * @return the idcard
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * @param idcard
	 *            the idcard to set
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * @return the expertise
	 */
	public String getExpertise() {
		return expertise;
	}

	/**
	 * @param expertise
	 *            the expertise to set
	 */
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getIsDispensed() {
		return isDispensed;
	}

	public void setIsDispensed(String isDispensed) {
		this.isDispensed = isDispensed;
	}

	public String getIsUtec() {
		return isUtec;
	}

	public void setIsUtec(String isUtec) {
		this.isUtec = isUtec;
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
	 * @param decAchievement
	 *            the decAchievement to set
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

	/**
	 * @return the decMonographs
	 */
	public ArrayList<DecMonograph> getDecMonographs() {
		return decMonographs;
	}

	/**
	 * @param decMonographs
	 *            the decMonographs to set
	 */
	public void setDecMonographs(ArrayList<DecMonograph> decMonographs) {
		this.decMonographs = decMonographs;
	}

	/**
	 * @return the publishRewards
	 */
	public ArrayList<DecPublishReward> getPublishRewards() {
		return publishRewards;
	}

	/**
	 * @param publishRewards
	 *            the publishRewards to set
	 */
	public void setPublishRewards(ArrayList<DecPublishReward> publishRewards) {
		this.publishRewards = publishRewards;
	}

	/**
	 * @return the decScis
	 */
	public ArrayList<DecSci> getDecScis() {
		return decScis;
	}

	/**
	 * @param decScis
	 *            the decScis to set
	 */
	public void setDecScis(ArrayList<DecSci> decScis) {
		this.decScis = decScis;
	}

	/**
	 * @return the decClinicalRewards
	 */
	public ArrayList<DecClinicalReward> getDecClinicalRewards() {
		return decClinicalRewards;
	}

	/**
	 * @param decClinicalRewards
	 *            the decClinicalRewards to set
	 */
	public void setDecClinicalRewards(ArrayList<DecClinicalReward> decClinicalRewards) {
		this.decClinicalRewards = decClinicalRewards;
	}

	/**
	 * @return the decAcadeRewards
	 */
	public ArrayList<DecAcadeReward> getDecAcadeRewards() {
		return decAcadeRewards;
	}

	/**
	 * @param decAcadeRewards
	 *            the decAcadeRewards to set
	 */
	public void setDecAcadeRewards(ArrayList<DecAcadeReward> decAcadeRewards) {
		this.decAcadeRewards = decAcadeRewards;
	}

	public ArrayList<DecTextbookPmph> getDecTextbookPmphs() {
		return decTextbookPmphs;
	}

	public void setDecTextbookPmphs(ArrayList<DecTextbookPmph> decTextbookPmphs) {
		this.decTextbookPmphs = decTextbookPmphs;
	}

	public DecMoocDigital getDecMoocDigital() {
		return decMoocDigital;
	}

	public void setDecMoocDigital(DecMoocDigital decMoocDigital) {
		this.decMoocDigital = decMoocDigital;
	}

	public ArrayList<DecExtensionVO> getDecExtensionVOs() {
		return decExtensionVOs;
	}

	public void setDecExtensionVOs(ArrayList<DecExtensionVO> decExtensionVOs) {
		this.decExtensionVOs = decExtensionVOs;
	}

	public DecIntention getDecIntention() {
		return decIntention;
	}

	public void setDecIntention(DecIntention decIntention) {
		this.decIntention = decIntention;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
}
