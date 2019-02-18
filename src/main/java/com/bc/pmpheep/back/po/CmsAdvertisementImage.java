package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * @author mr
 * @CreateDate 2017年12月22日 下午09:02:46
 *
 **/


@SuppressWarnings("serial")
@Alias("CmsAdvertisementImage")
public class CmsAdvertisementImage implements Serializable {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 广告管理id
	 */
	private Long advertId;
	/**
	 * 图片id
	 */
	private String image;
	/**
	 * 图片转跳地址
	 */
	private String imageJumpUrl;

	/**
	 * 是否禁用
	 */
	private Boolean isDisabled;
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
	public Long getAdvertId() {
		return advertId;
	}
	public void setAdvertId(Long advertId) {
		this.advertId = advertId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
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
	
	public CmsAdvertisementImage() {
		super();
	}
	public CmsAdvertisementImage(Long id, Long advertId, String image, Boolean isDisabled, Timestamp gmtCreate,
			Timestamp gmtUpdate,String imageJumpUrl) {
		this.id = id;
		this.advertId = advertId;
		this.image = image;
		this.isDisabled = isDisabled;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.imageJumpUrl = imageJumpUrl;;
	}
	@Override
	public String toString() {
		return "CmsAdvertisementImage [id=" + id + ", advertId=" + advertId + ", image=" + image + ", isDisabled="
				+ isDisabled + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

	public String getImageJumpUrl() {
		return imageJumpUrl;
	}

	public void setImageJumpUrl(String imageJumpUrl) {
		this.imageJumpUrl = imageJumpUrl;
	}
}
