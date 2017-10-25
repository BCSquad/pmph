package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * 作家用户图书收藏夹表
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:34:35
 *
 */
@SuppressWarnings("serial")
@Alias("WriterBookFavorite")
public class WriterBookFavorite implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Long writerId;
	/**
	 * 收藏夹名称
	 */
	private String favoriteName;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public WriterBookFavorite() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public String getFavoriteName() {
		return favoriteName;
	}

	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", writerId:" + writerId + ", favoriteName:"
				+ favoriteName + ", gmtCreate:" + gmtCreate + "}";
	}

	

}