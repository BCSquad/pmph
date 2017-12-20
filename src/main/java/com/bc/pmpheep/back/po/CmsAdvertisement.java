package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("CmsAdvertisement")
public class CmsAdvertisement implements Serializable {
	/**
     * 主键
     */
    private Long    id;
    /**
     * 广告名
     */
    private String  adname ;
    /**
     * 图片id
     */
    private String  image ;
    /**
     * 链接地址
     */
    private String  url ;
    /**
     * 是否禁用
     */
    private Boolean isDisabled ;
    /**
     * 显示顺序
     */
    private Integer sort ;
    /**
     * 备注
     */
    private String  note ;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate;
    
	public CmsAdvertisement() {
		super();
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	@Override
	public String toString() {
		return "{id:" + id + ", adname:" + adname + ", image:" + image
				+ ", url:" + url + ", isDisabled:" + isDisabled + ", sort:"
				+ sort + ", note:" + note + ", gmtCreate:" + gmtCreate
				+ ", gmtUpdate:" + gmtUpdate + "}";
	}
    
    
    

}
