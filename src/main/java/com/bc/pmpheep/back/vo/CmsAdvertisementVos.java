package com.bc.pmpheep.back.vo;

import com.alibaba.druid.support.json.JSONUtils;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mr
 * @CreateDate 2017年12月22日 下午10:10:46
 *
 **/
@SuppressWarnings("serial")
@Alias("CmsAdvertisementVos")
public class CmsAdvertisementVos implements Serializable {
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
	/**
	 * 图片表id
	 */
	// private Long imageId;
	/**
	 * 广告管理id
	 */
	private Long advertId;
	/**
	 * 图片id
	 */
	private List<CmsAdvertisementImage> image;
	/**
	 * 图片是否启用
	 */
	private Boolean isDisplay;
	/**
	 * 能否操作
	 */
	private Boolean isPlay = false;

	// public Long getImageId() {
	// return imageId;
	// }
	//
	// public void setImageId(Long imageId) {
	// this.imageId = imageId;
	// }

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Long getAdvertId() {
		return advertId;
	}

	public void setAdvertId(Long advertId) {
		this.advertId = advertId;
	}

	public void setImage(List<CmsAdvertisementImage> image) {
		this.image = image;
	}

	public Object getImage() {
		return image;
	}

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

	public Boolean getIsPlay() {
		return isPlay;
	}

	public void setIsPlay(Boolean isPlay) {
		this.isPlay = isPlay;
	}

	public CmsAdvertisementVos() {
		super();
	}

	public short getApporpc() {
		return apporpc;
	}

	public void setApporpc(short apporpc) {
		this.apporpc = apporpc;
	}
}
