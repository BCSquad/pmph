/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:DeclarationResultBookVO<p>
 * <p>Description:申报结果按书名统计<p>
 * @author lyc
 * @date 2017年11月30日 下午4:30:33
 */

@SuppressWarnings("serial")
@Alias("DeclarationResultBookVO")
public class DeclarationResultBookVO implements Serializable{

	    //教材id
		private Long materialId;
		//序号
		private Long row;
		//书籍id
		private Long id;
		//书籍名称
		private String bookName;
		//主编名单
		private String editorList;
		//副主编名单
		private String subEditorList;
		//编委名单
		private String editorialList;
		//数字编委名单
		private String isDigitalEditorList;
		
		public DeclarationResultBookVO() {
			super();
		}
		
		public Long getMaterialId() {
			return materialId;
		}
		
		public void setMaterialId(Long materialId) {
			this.materialId = materialId;
		}
		
		public Long getRow() {
			return row;
		}
		
		public void setRow(Long row) {
			this.row = row;
		}
		
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getBookName() {
			return bookName;
		}
		
		public void setBookName(String bookName) {
			this.bookName = bookName;
		}
		
		public String getEditorList() {
			return editorList;
		}
		
		public void setEditorList(String editorList) {
			this.editorList = editorList;
		}
		
		public String getSubEditorList() {
			return subEditorList;
		}
		
		public void setSubEditorList(String subEditorList) {
			this.subEditorList = subEditorList;
		}
		
		public String getEditorialList() {
			return editorialList;
		}
		
		public void setEditorialList(String editorialList) {
			this.editorialList = editorialList;
		}
		
		public String getIsDigitalEditorList() {
			return isDigitalEditorList;
		}
		
		public void setIsDigitalEditorList(String isDigitalEditorList) {
			this.isDigitalEditorList = isDigitalEditorList;
		}
		
}
