package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

/**
 *职位遴选页面导出名单
 *@author Mr
 *@CreateDate 2017年12月25日 下午2:12:33
 *
 **/
public class ExcelDecAndTextbookVO{
	
	//textbookIds
	private Long textbookId;
	//遴选的职务
	private Integer chosenPosition;
	@ExcelHeader(header = "书序")
	private Integer sort;
	//书籍名称
	@ExcelHeader(header = "书籍名称")
	private String textbookName;
	@ExcelHeader(header = "版次")
	private String textbookRound;
	@ExcelHeader(header = "策划编辑")
	private String editorName;
	//显示职务
	@ExcelHeader(header = "本书担任职务")
	private String showChosenPosition;
	//作家真实姓名
	@ExcelHeader(header = "姓名")
	private String realname;
	//作家账号
	@ExcelHeader(header = "申报账号")
	private String username;
	//申报单位
	private String unitName;
	//工作单位
	@ExcelHeader(header = "单位")
	private String orgName;
	//作家职务
	@ExcelHeader(header = "职务")
	private String position;
	//作家职称
	@ExcelHeader(header = "职称")
	private String title;
	//通讯地址
	@ExcelHeader(header = "通讯地址")
	private String address;
	//邮编
	@ExcelHeader(header = "邮编")
	private String postcode;
	//作家手机
	@ExcelHeader(header = "手机号")
	private String handphone;
	//作家邮箱
	@ExcelHeader(header = "电子邮箱")
	private String email;
	  //显示的证件类型
    @ExcelHeader(header = "证件类型")
    private String showIdtype;
  	//作家证件号码
    @ExcelHeader(header = "证件号码")
  	private String idcard;
	//所选书籍与职务
	private String chooseBooksAndPostions;
	//学校审核进度
	private Integer onlineProgress;
	// 显示的审核进度
	@ExcelHeader(header = "学校审核")
	private String showOnlineProgress;
	//纸质表进度
    private Integer offlineProgress;
	//纸质表进度
	@ExcelHeader(header = "纸质表审核")
    private String showOfflineProgress;


	//出生年月
	@ExcelHeader(header = "出生年月")
	private String birthday;

    //教材名称
    private String materialName;
    //作家证件类型
  	private Short idtype;
  	private Long did;

	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getShowIdtype() {
		return showIdtype;
	}

	public void setShowIdtype(String showIdtype) {
		this.showIdtype = showIdtype;
	}

	public Short getIdtype() {
		return idtype;
	}

	public void setIdtype(Short idtype) {
		this.idtype = idtype;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getChosenPosition() {
		return chosenPosition;
	}

	public void setChosenPosition(Integer chosenPosition) {
		this.chosenPosition = chosenPosition;
	}

	public String getMaterialName() {
		return materialName;
	}
	
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHandphone() {
		return handphone;
	}
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getChooseBooksAndPostions() {
		return chooseBooksAndPostions;
	}
	public void setChooseBooksAndPostions(String chooseBooksAndPostions) {
		this.chooseBooksAndPostions = chooseBooksAndPostions;
	}
	public Integer getOnlineProgress() {
		return onlineProgress;
	}
	public void setOnlineProgress(Integer onlineProgress) {
		this.onlineProgress = onlineProgress;
	}
	public Integer getOfflineProgress() {
		return offlineProgress;
	}
	public void setOfflineProgress(Integer offlineProgress) {
		this.offlineProgress = offlineProgress;
	}
	public String getTextbookName() {
		return textbookName;
	}
	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}
	
	public Long getTextbookId() {
		return textbookId;
	}

	public void setTextbookId(Long textbookId) {
		this.textbookId = textbookId;
	}
	
	public String getShowChosenPosition() {
		return showChosenPosition;
	}

	public void setShowChosenPosition(String showChosenPosition) {
		this.showChosenPosition = showChosenPosition;
	}

	public String getShowOnlineProgress() {
		return showOnlineProgress;
	}

	public void setShowOnlineProgress(String showOnlineProgress) {
		this.showOnlineProgress = showOnlineProgress;
	}

	public String getShowOfflineProgress() {
		return showOfflineProgress;
	}

	public void setShowOfflineProgress(String showOfflineProgress) {
		this.showOfflineProgress = showOfflineProgress;
	}

	public ExcelDecAndTextbookVO() {
		super();
	}

	public ExcelDecAndTextbookVO(Long textbookId, Integer chosenPosition, String showChosenPosition, String realname,
			String username, String unitName, String orgName, String position, String title, String address,
			String postcode, String handphone, String email, String showIdtype, String idcard,
			String chooseBooksAndPostions, Integer onlineProgress, String showOnlineProgress, Integer offlineProgress,
			String showOfflineProgress, String textbookName, String materialName, Short idtype) {
		this.textbookId = textbookId;
		this.chosenPosition = chosenPosition;
		this.showChosenPosition = showChosenPosition;
		this.realname = realname;
		this.username = username;
		this.unitName = unitName;
		this.orgName = orgName;
		this.position = position;
		this.title = title;
		this.address = address;
		this.postcode = postcode;
		this.handphone = handphone;
		this.email = email;
		this.showIdtype = showIdtype;
		this.idcard = idcard;
		this.chooseBooksAndPostions = chooseBooksAndPostions;
		this.onlineProgress = onlineProgress;
		this.showOnlineProgress = showOnlineProgress;
		this.offlineProgress = offlineProgress;
		this.showOfflineProgress = showOfflineProgress;
		this.textbookName = textbookName;
		this.materialName = materialName;
		this.idtype = idtype;
	}

	@Override
	public String toString() {
		return "ExcelDecAndTextbookVO [textbookId=" + textbookId + ", chosenPosition=" + chosenPosition
				+ ", showChosenPosition=" + showChosenPosition + ", realname=" + realname + ", username=" + username
				+ ", unitName=" + unitName + ", orgName=" + orgName + ", position=" + position + ", title=" + title
				+ ", address=" + address + ", postcode=" + postcode + ", handphone=" + handphone + ", email=" + email
				+ ", showIdtype=" + showIdtype + ", idcard=" + idcard + ", chooseBooksAndPostions="
				+ chooseBooksAndPostions + ", onlineProgress=" + onlineProgress + ", showOnlineProgress="
				+ showOnlineProgress + ", offlineProgress=" + offlineProgress + ", showOfflineProgress="
				+ showOfflineProgress + ", textbookName=" + textbookName + ", materialName=" + materialName
				+ ", idtype=" + idtype + "]";
	}


	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTextbookRound() {
		return textbookRound;
	}

	public void setTextbookRound(String textbookRound) {
		this.textbookRound = textbookRound;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}
}
