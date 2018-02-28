package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("BookVO")
public class BookVO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 图书名称
	 */
	private String bookname;
	/**
	 * ISBN号
	 */
	private String isbn;
	/**
	 * isbn/图书名称
	 */
	private String name;
	/**
	 * 是否新书
	 */
	private Boolean isNew;
	/**
	 * 是否推荐
	 */
	private Boolean isPromote;
	/**
	 * 图书是否上架
	 */
	private Boolean isOnSale;
	/**
	 * 类别
	 */
	private Long type;
	/**
	 * 书籍类别根目录
	 */
	private String path;
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 教材名称
	 */
	private String materialName;
	/**
	 * 教材id
	 */
	private Long materialId;
	/**
	 * 是否重点学科
	 */
	private Boolean isKey;
	/**
	 * 重点学科显示顺序
	 */
	private Integer sortKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsPromote() {
		return isPromote;
	}

	public void setIsPromote(Boolean isPromote) {
		this.isPromote = isPromote;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(Boolean isKey) {
		this.isKey = isKey;
	}

	public Integer getSortKey() {
		return sortKey;
	}

	public void setSortKey(Integer sortKey) {
		this.sortKey = sortKey;
	}
	
}
