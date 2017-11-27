package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;

/**
 * 职位遴选VO
 *@author MrYang 
 *@CreateDate 2017年11月21日 上午10:11:50
 *
 **/
@SuppressWarnings("serial")
@Alias("BookPositionVO")
public class BookPositionVO implements Serializable{
	    //教材id
		private Long materialId;
		//是否所有书籍已公布
		private boolean isAllTextbookPublished;
		//是否被强制结束
		private boolean isForceEnd;
	    //书籍主键
		private Long textBookId;
		//图书序号
		private Integer sort;
		//书籍名称
		private String textbookName;
		//书籍轮次
		private Integer textbookRound;
		//书籍申报数
		private Integer applyNum;
		//策划编辑id
		private Long planningEditor ;
		//策划编辑名字
		private String planningEditorName ;
		//主编副主编名字
		private String editorsAndAssociateEditors;
		//主编副主编数目
	    private Integer editorsAndAssociateEditorsNum;
	    //编委名字
	    private String bianWeis;
	    //编委数目
		private Integer bianWeisNum;	
//		//是否有进行策划编辑选择操作
//		private boolean  choosePlanningEditor ;
//		//是否有进行主编副主编选择操作
//		private boolean  chooseEditorAndAssociateEditor ;
//		//是否有进行编委选择操作
//		private boolean  chooseBianWei ;
//		//是否有进行名单确认操作
//		private boolean  confirmForm ;
//		//是否有进行结果公布操作
//		private boolean  publishResults ;
//		//是否有进行创建小组操作
//		private boolean  createGroup ;
//		//是否有进行强制结束操作
//		private boolean  settingEnd ;
		
		//是否已公布
		private Boolean isPublished;
		//是否锁定（通过）
		private Boolean isLocked;
		
		public Boolean getIsPublished() {
			return isPublished;
		}

		public void setIsPublished(Boolean isPublished) {
			this.isPublished = isPublished;
		}

		public Boolean getIsLocked() {
			return isLocked;
		}

		public void setIsLocked(Boolean isLocked) {
			this.isLocked = isLocked;
		}

		public BookPositionVO() {
			super();
		}

		public Long getMaterialId() {
			return materialId;
		}

		public void setMaterialId(Long materialId) {
			this.materialId = materialId;
		}

		public boolean isAllTextbookPublished() {
			return isAllTextbookPublished;
		}

		public void setAllTextbookPublished(boolean isAllTextbookPublished) {
			this.isAllTextbookPublished = isAllTextbookPublished;
		}

		public boolean isForceEnd() {
			return isForceEnd;
		}

		public void setForceEnd(boolean isForceEnd) {
			this.isForceEnd = isForceEnd;
		}

		public Long getTextBookId() {
			return textBookId;
		}

		public void setTextBookId(Long textBookId) {
			this.textBookId = textBookId;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		public String getTextbookName() {
			return textbookName;
		}

		public void setTextbookName(String textbookName) {
			this.textbookName = textbookName;
		}

		public Integer getTextbookRound() {
			return textbookRound;
		}

		public void setTextbookRound(Integer textbookRound) {
			this.textbookRound = textbookRound;
		}

		public Integer getApplyNum() {
			return applyNum;
		}

		public void setApplyNum(Integer applyNum) {
			this.applyNum = applyNum;
		}

		public Long getPlanningEditor() {
			return planningEditor;
		}

		public void setPlanningEditor(Long planningEditor) {
			this.planningEditor = planningEditor;
		}

		public String getPlanningEditorName() {
			return planningEditorName;
		}

		public void setPlanningEditorName(String planningEditorName) {
			this.planningEditorName = planningEditorName;
		}

		public String getEditorsAndAssociateEditors() {
			return editorsAndAssociateEditors;
		}

		public void setEditorsAndAssociateEditors(String editorsAndAssociateEditors) {
			this.editorsAndAssociateEditors = editorsAndAssociateEditors;
		}

		public Integer getEditorsAndAssociateEditorsNum() {
			return editorsAndAssociateEditorsNum;
		}

		public void setEditorsAndAssociateEditorsNum(
				Integer editorsAndAssociateEditorsNum) {
			this.editorsAndAssociateEditorsNum = editorsAndAssociateEditorsNum;
		}

		public String getBianWeis() {
			return bianWeis;
		}

		public void setBianWeis(String bianWeis) {
			this.bianWeis = bianWeis;
		}

		public Integer getBianWeisNum() {
			return bianWeisNum;
		}

		public void setBianWeisNum(Integer bianWeisNum) {
			this.bianWeisNum = bianWeisNum;
		}

		

		@Override
		public String toString() {
			return "BookPositionVO [materialId=" + materialId + ", isAllTextbookPublished=" + isAllTextbookPublished
					+ ", isForceEnd=" + isForceEnd + ", textBookId=" + textBookId + ", sort=" + sort + ", textbookName="
					+ textbookName + ", textbookRound=" + textbookRound + ", applyNum=" + applyNum + ", planningEditor="
					+ planningEditor + ", planningEditorName=" + planningEditorName + ", editorsAndAssociateEditors="
					+ editorsAndAssociateEditors + ", editorsAndAssociateEditorsNum=" + editorsAndAssociateEditorsNum
					+ ", bianWeis=" + bianWeis + ", bianWeisNum=" + bianWeisNum + ", isPublished=" + isPublished
					+ ", isLocked=" + isLocked + "]";
		}
		
		
}










