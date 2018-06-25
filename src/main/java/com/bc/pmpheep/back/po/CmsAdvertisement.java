package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 * @CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("CmsAdvertisement")
public class CmsAdvertisement implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 广告名
	 */
	private String adname;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 是否禁用
	 */
	private Boolean isDisabled;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 样式
	 */
	private String style;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 是否自动播放
	 */
	private Boolean autoPlay;
	/**
	 * 导航颜色
	 */
	private String navigationColor;
	/**
	 * 是否显示导航
	 */
	private Boolean isNavigation;
	/**
	 * 动画间隔
	 */
	private Integer animationInterval;
	/**
	 * 动画效果
	 */
	private String animationEffect;
	/**
	 * 是否显示标题
	 */
	private Boolean isShowHeading;

	/**
	 * 应用类型
	 */
	private short apporpc;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdname() {
		return adname;
	}

	public void setAdname(String adname) {
		this.adname = adname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getAutoPlay() {
		return autoPlay;
	}

	public void setAutoPlay(Boolean autoPlay) {
		this.autoPlay = autoPlay;
	}

	public String getNavigationColor() {
		return navigationColor;
	}

	public void setNavigationColor(String navigationColor) {
		this.navigationColor = navigationColor;
	}

	public Boolean getIsNavigation() {
		return isNavigation;
	}

	public void setIsNavigation(Boolean isNavigation) {
		this.isNavigation = isNavigation;
	}

	public Integer getAnimationInterval() {
		return animationInterval;
	}

	public void setAnimationInterval(Integer animationInterval) {
		this.animationInterval = animationInterval;
	}

	public String getAnimationEffect() {
		return animationEffect;
	}

	public void setAnimationEffect(String animationEffect) {
		this.animationEffect = animationEffect;
	}

	public Boolean getIsShowHeading() {
		return isShowHeading;
	}

	public void setIsShowHeading(Boolean isShowHeading) {
		this.isShowHeading = isShowHeading;
	}
	
	
	public CmsAdvertisement() {
		super();
	}

	public CmsAdvertisement(Long id, String adname, String url, Boolean isDisabled, Integer sort, String note,
			String style, Integer type, Boolean autoPlay, String navigationColor, Boolean isNavigation,
			Integer animationInterval, String animationEffect, Boolean isShowHeading, Timestamp gmtCreate,
			Timestamp gmtUpdate) {
		this.id = id;
		this.adname = adname;
		this.url = url;
		this.isDisabled = isDisabled;
		this.sort = sort;
		this.note = note;
		this.style = style;
		this.type = type;
		this.autoPlay = autoPlay;
		this.navigationColor = navigationColor;
		this.isNavigation = isNavigation;
		this.animationInterval = animationInterval;
		this.animationEffect = animationEffect;
		this.isShowHeading = isShowHeading;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	@Override
	public String toString() {
		return "CmsAdvertisement [id=" + id + ", adname=" + adname + ", url=" + url + ", isDisabled=" + isDisabled
				+ ", sort=" + sort + ", note=" + note + ", style=" + style + ", type=" + type + ", autoPlay=" + autoPlay
				+ ", navigationColor=" + navigationColor + ", isNavigation=" + isNavigation + ", animationInterval="
				+ animationInterval + ", animationEffect=" + animationEffect + ", isShowHeading=" + isShowHeading
				+ ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

	public short getApporpc() {
		return apporpc;
	}

	public void setApporpc(short apporpc) {
		this.apporpc = apporpc;
	}
}
