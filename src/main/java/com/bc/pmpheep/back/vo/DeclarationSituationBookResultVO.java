/**
 * 
 */
package com.bc.pmpheep.back.vo;

/**
 * <p>Title:DeclarationSituationBookResultVO<p>
 * <p>Description:申报情况按书名统计<p>
 * @author lyc
 * @date 2017年11月30日 下午4:26:10
 */
public class DeclarationSituationBookResultVO {

	    //教材id
		private Long materialId;
		//书籍id
		private Long id;
		//序号
		private Long row;
		//申报学校
		private String bookName;
		//主编申报数
		private Integer presetPositionEditor;
		//副主编申报数
		private Integer presetPositionSubeditor;
		//编委申报数
		private Integer presetPositionEditorial;
		//主编当选数
		private Integer chosenPositionEditor;
		//副主编当选数
		private Integer chosenPositionSubeditor;
		//编委当选数
		private Integer chosenPositionEditorial;
		//数字编委当选数
		private Boolean isDigitalEditor;
		//申报人数
		private Integer declarationPersons;
		//当选人数
		private Integer presetPersons;
		
		public DeclarationSituationBookResultVO() {
			super();
		}

		public Long getMaterialId() {
			return materialId;
		}

		public void setMaterialId(Long materialId) {
			this.materialId = materialId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getRow() {
			return row;
		}

		public void setRow(Long row) {
			this.row = row;
		}

		public String getBookName() {
			return bookName;
		}

		public void setBookName(String bookName) {
			this.bookName = bookName;
		}

		public Integer getPresetPositionEditor() {
			return presetPositionEditor;
		}

		public void setPresetPositionEditor(Integer presetPositionEditor) {
			this.presetPositionEditor = presetPositionEditor;
		}

		public Integer getPresetPositionSubeditor() {
			return presetPositionSubeditor;
		}

		public void setPresetPositionSubeditor(Integer presetPositionSubeditor) {
			this.presetPositionSubeditor = presetPositionSubeditor;
		}

		public Integer getPresetPositionEditorial() {
			return presetPositionEditorial;
		}

		public void setPresetPositionEditorial(Integer presetPositionEditorial) {
			this.presetPositionEditorial = presetPositionEditorial;
		}

		public Integer getChosenPositionEditor() {
			return chosenPositionEditor;
		}

		public void setChosenPositionEditor(Integer chosenPositionEditor) {
			this.chosenPositionEditor = chosenPositionEditor;
		}

		public Integer getChosenPositionSubeditor() {
			return chosenPositionSubeditor;
		}

		public void setChosenPositionSubeditor(Integer chosenPositionSubeditor) {
			this.chosenPositionSubeditor = chosenPositionSubeditor;
		}

		public Integer getChosenPositionEditorial() {
			return chosenPositionEditorial;
		}

		public void setChosenPositionEditorial(Integer chosenPositionEditorial) {
			this.chosenPositionEditorial = chosenPositionEditorial;
		}

		public Boolean getIsDigitalEditor() {
			return isDigitalEditor;
		}

		public void setIsDigitalEditor(Boolean isDigitalEditor) {
			this.isDigitalEditor = isDigitalEditor;
		}

		public Integer getDeclarationPersons() {
			return declarationPersons;
		}

		public void setDeclarationPersons(Integer declarationPersons) {
			this.declarationPersons = declarationPersons;
		}

		public Integer getPresetPersons() {
			return presetPersons;
		}

		public void setPresetPersons(Integer presetPersons) {
			this.presetPersons = presetPersons;
		}
		
}