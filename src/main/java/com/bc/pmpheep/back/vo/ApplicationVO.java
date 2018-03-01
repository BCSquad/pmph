package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

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
import com.bc.pmpheep.back.po.Material;

/**
 * @author MrYang
 * @CreateDate 2017年9月30日 下午4:34:28
 * 修改：tyc
 * @Date 20180301
 **/
@SuppressWarnings("serial")
@Alias("ApplicationVO")
public class ApplicationVO implements Serializable {
	// 申报职位
	private List<DecPositionDisplayVO> decPositionList = new ArrayList<DecPositionDisplayVO>(5);
	// 作家申报表
	private DeclarationOrDisplayVO declaration = new DeclarationOrDisplayVO();
	// 主要学习经历
	private List<DecEduExp> decEduExpList = new ArrayList<DecEduExp>(5);
	// 主要工作经历
	private List<DecWorkExp> decWorkExpList = new ArrayList<DecWorkExp>(5);
	// 主要教学经历
	private List<DecTeachExp> decTeachExpList = new ArrayList<DecTeachExp>(5);
	// 个人成就
	private DecAchievement decAchievement = new DecAchievement();
	// 主要学术兼职
	private List<DecAcade> decAcadeList = new ArrayList<DecAcade>(5);
	// 作家本套上版教材参编情况表
	private List<DecLastPosition> decLastPositionList = new ArrayList<DecLastPosition>(5);
	// 主编国家规划教材情况
	private List<DecNationalPlan> decNationalPlanList = new ArrayList<DecNationalPlan>(5);
	// 精品课程建设情况
	private List<DecCourseConstruction> decCourseConstruction = new ArrayList<DecCourseConstruction>(5);
	// 其他社教材编写情况表
	private List<DecTextbook> decTextbookList = new ArrayList<DecTextbook>(5);
	// 科研情况
	private List<DecResearch> decResearchList = new ArrayList<DecResearch>(5);
	// 主编学术专著情况
	private List<DecMonograph> decMonographList = new ArrayList<DecMonograph>(5);
	// 出版行业获奖情况
	private List<DecPublishReward> decPublishRewardList = new ArrayList<DecPublishReward>(5);
	// SCI论文投稿及影响因子情况
	private List<DecSci> decSciList = new ArrayList<DecSci>(5);
	// 临床医学获奖情况
	private List<DecClinicalReward> decClinicalRewardList = new ArrayList<DecClinicalReward>(5);
	// 学术荣誉授予情况
	private List<DecAcadeReward> decAcadeRewardList = new ArrayList<DecAcadeReward>(5);
	// 作家扩展项
	private List<DecExtensionVO> decExtensionList = new ArrayList<DecExtensionVO>(5);
	// 人卫社教材编写情况表
	private List<DecTextbookPmph> decTextbookPmphList = new ArrayList<DecTextbookPmph>(5);
	// 参加人卫慕课、数字教材编写情况
	private DecMoocDigital decMoocDigital = new DecMoocDigital();
	// 编写内容意向表
	private DecIntention decIntention = new DecIntention();
	// 是否选择必填
	private Material material = new Material();

	public ApplicationVO() {
		super();
	}

	public List<DecPositionDisplayVO> getDecPositionList() {
		return decPositionList;
	}

	public void setDecPositionList(List<DecPositionDisplayVO> decPositionList) {
		this.decPositionList = decPositionList;
	}

	public DeclarationOrDisplayVO getDeclaration() {
		return declaration;
	}

	public void setDeclaration(DeclarationOrDisplayVO declaration) {
		this.declaration = declaration;
	}

	public List<DecEduExp> getDecEduExpList() {
		return decEduExpList;
	}

	public void setDecEduExpList(List<DecEduExp> decEduExpList) {
		this.decEduExpList = decEduExpList;
	}

	public List<DecWorkExp> getDecWorkExpList() {
		return decWorkExpList;
	}

	public void setDecWorkExpList(List<DecWorkExp> decWorkExpList) {
		this.decWorkExpList = decWorkExpList;
	}

	public List<DecTeachExp> getDecTeachExpList() {
		return decTeachExpList;
	}

	public void setDecTeachExpList(List<DecTeachExp> decTeachExpList) {
		this.decTeachExpList = decTeachExpList;
	}

	public DecAchievement getDecAchievement() {
		return decAchievement;
	}

	public void setDecAchievement(DecAchievement decAchievement) {
		this.decAchievement = decAchievement;
	}

	public List<DecAcade> getDecAcadeList() {
		return decAcadeList;
	}

	public void setDecAcadeList(List<DecAcade> decAcadeList) {
		this.decAcadeList = decAcadeList;
	}

	public List<DecLastPosition> getDecLastPositionList() {
		return decLastPositionList;
	}

	public void setDecLastPositionList(List<DecLastPosition> decLastPositionList) {
		this.decLastPositionList = decLastPositionList;
	}

	public List<DecCourseConstruction> getDecCourseConstruction() {
		return decCourseConstruction;
	}

	public void setDecCourseConstruction(List<DecCourseConstruction> decCourseConstruction) {
		this.decCourseConstruction = decCourseConstruction;
	}

	public List<DecNationalPlan> getDecNationalPlanList() {
		return decNationalPlanList;
	}

	public void setDecNationalPlanList(List<DecNationalPlan> decNationalPlanList) {
		this.decNationalPlanList = decNationalPlanList;
	}

	public List<DecTextbook> getDecTextbookList() {
		return decTextbookList;
	}

	public void setDecTextbookList(List<DecTextbook> decTextbookList) {
		this.decTextbookList = decTextbookList;
	}

	public List<DecResearch> getDecResearchList() {
		return decResearchList;
	}

	public void setDecResearchList(List<DecResearch> decResearchList) {
		this.decResearchList = decResearchList;
	}

	public List<DecExtensionVO> getDecExtensionList() {
		return decExtensionList;
	}

	public void setDecExtensionList(List<DecExtensionVO> decExtensionList) {
		this.decExtensionList = decExtensionList;
	}

	public List<DecMonograph> getDecMonographList() {
		return decMonographList;
	}

	public void setDecMonographList(List<DecMonograph> decMonographList) {
		this.decMonographList = decMonographList;
	}

	public List<DecPublishReward> getDecPublishRewardList() {
		return decPublishRewardList;
	}

	public void setDecPublishRewardList(List<DecPublishReward> decPublishRewardList) {
		this.decPublishRewardList = decPublishRewardList;
	}

	public List<DecSci> getDecSciList() {
		return decSciList;
	}

	public void setDecSciList(List<DecSci> decSciList) {
		this.decSciList = decSciList;
	}

	public List<DecClinicalReward> getDecClinicalRewardList() {
		return decClinicalRewardList;
	}

	public void setDecClinicalRewardList(
			List<DecClinicalReward> decClinicalRewardList) {
		this.decClinicalRewardList = decClinicalRewardList;
	}

	public List<DecAcadeReward> getDecAcadeRewardList() {
		return decAcadeRewardList;
	}

	public void setDecAcadeRewardList(List<DecAcadeReward> decAcadeRewardList) {
		this.decAcadeRewardList = decAcadeRewardList;
	}
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<DecTextbookPmph> getDecTextbookPmphList() {
		return decTextbookPmphList;
	}

	public void setDecTextbookPmphList(List<DecTextbookPmph> decTextbookPmphList) {
		this.decTextbookPmphList = decTextbookPmphList;
	}

	public DecMoocDigital getDecMoocDigital() {
		return decMoocDigital;
	}

	public void setDecMoocDigital(DecMoocDigital decMoocDigital) {
		this.decMoocDigital = decMoocDigital;
	}

	public DecIntention getDecIntention() {
		return decIntention;
	}

	public void setDecIntention(DecIntention decIntention) {
		this.decIntention = decIntention;
	}

}
