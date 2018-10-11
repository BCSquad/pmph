/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.DecExtensionVO;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Microsoft Offic Word 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class ClicWordHelper {

	private final Logger logger = LoggerFactory.getLogger(ClicWordHelper.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * 批量导出产品申报的word 到指定的目录
	 * @param product_name 产品的名字
	 * @param filePath 系统唯一临时文件目录/产品名/书序和书名的组合，例如"/home/temp35723882/产品名称/"
	 * @param list
	 * 				已完成修改的数据
	 * @param filter
	 * 				填充的二进制数据--用于勾选的模板 以及对word table 中的的过滤
	 */

	public void export(String product_name, String filePath, List<ExpertationVO> list, String filter) {
		HashMap<String, XWPFDocument> map = fromDeclarationEtcBOList(product_name, list, filter);
		if (createPath(filePath)) {
			if (!filePath.endsWith(File.separator)) {
				filePath = filePath.concat(File.separator);
			}
			File file;
			for (Map.Entry<String, XWPFDocument> entry : map.entrySet()) {
				file = new File(filePath.concat(FileUtil.replaceIllegalCharForFileName(entry.getKey())));
				try {
					FileOutputStream out = new FileOutputStream(file);
					entry.getValue().write(out);
					out.flush();
					out.close();
				} catch (IOException ex) {
					logger.error("Word导出错误：{}", ex.getMessage());
					throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
							CheckedExceptionResult.FILE_CREATION_FAILED, "未能创建Word文件");
				}
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
					CheckedExceptionResult.DIRECTORY_CREATION_FAILED, "未能创建目录");
		}

	}

	/**
	 * 创建文件路径
	 * @param filePath 文件的路径
	 * @return
	 */
	private boolean createPath(String filePath) {
		File destDir;
		if (filePath.endsWith(File.separator)) {
			destDir = new File(filePath);// 给出的是路径时
		} else {
			destDir = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			return destDir.mkdirs();
		}
		return true;
	}

	/**
	 * 填充 word 数据
	 * @param product_name
	 * @param list
	 * @param filter
	 * @return
	 */
	private HashMap<String,XWPFDocument> fromDeclarationEtcBOList(String product_name, List<ExpertationVO> list, String filter) {
		InputStream is;
		XWPFDocument document;
		String path = this.getClass().getClassLoader().getResource("ClicResumeTemplate.docx").getPath();
		HashMap<String, XWPFDocument> map = new HashMap<>(list.size());
		int decSequese =0;
		for (ExpertationVO bo : list) {
			try {
				is = new FileInputStream(path);
				document = new XWPFDocument(is);
			} catch (IOException ex) {
				logger.error("读取Word模板文件'ClicResumeTemplate.docx'时出现错误：{}", ex.getMessage());
				throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,
						CheckedExceptionResult.FILE_CREATION_FAILED, "未找到模板文件");
			}
			if (StringUtil.notEmpty(product_name)) {
				List<XWPFRun> runs = document.getParagraphs().get(0).getRuns();
				runs.get(0).setText(product_name.concat("申报表"), 0);
			}
			List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
			List<XWPFTable> tables = document.getTables();
			String filename = generateFileName(bo,(++decSequese)+".");
			int i = 14;
			XWPFTable old = tables.get(12);  //获取上一个table 是为了指定新增table的样式
			//扩展项填充
			for (DecExtension extension :bo.getDecExtensionList()) {
				XmlCursor cursor = xwpfParagraphs.get(i).getCTP().newCursor();  //获取一个游标
				XWPFParagraph xwpfParagraph = document.insertNewParagraph(cursor);// ---这个是关键 插入一个新的段落
				CTPPr pPPr = xwpfParagraph.getCTP().getPPr() != null ? xwpfParagraph.getCTP().getPPr()
						: xwpfParagraph.getCTP().addNewPPr();
				CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing();
				pSpacing.setLine(BigInteger.valueOf(360L)); //设置 段落 spacing（间隔）的线
				pSpacing.setBeforeLines(BigInteger.valueOf(100L));
				XWPFRun xwpfRun = xwpfParagraph.createRun(); //创建文本对象
				xwpfRun.setText(extension.getExtension_name());
				xwpfRun.setFontSize(12);
				xwpfRun.setFontFamily("宋体");
				xwpfRun.setBold(true);
				/* 以下填充扩展项内容 */
				cursor = xwpfParagraphs.get(i + 1).getCTP().newCursor();
				XWPFTable t = document.insertNewTbl(cursor);  //插入新的table
				XWPFTableRow row = t.getRow(0);
				XWPFTableCell cell = row.getCell(0);
				CTTc cttc = cell.getCTTc();  //单元格中的一个属性
				CTTcPr tcpr = cttc.addNewTcPr(); //单元格中的一个属性
				CTTblWidth tcw = tcpr.addNewTcW();
				tcw.setType(old.getRow(0).getCell(0).getCTTc().getTcPr().getTcW().getType());
				tcw.setW(old.getRow(0).getCell(0).getCTTc().getTcPr().getTcW().getW());
				if (old.getRow(0).getCell(0).getCTTc().getTcPr().getGridSpan() != null) {
					tcpr.setGridSpan(old.getRow(0).getCell(0).getCTTc().getTcPr().getGridSpan());
				}
				String content = extension.getContent();
				if (StringUtil.notEmpty(content)) {
					XWPFRun run = cell.getParagraphs().get(0).createRun();
					run.setText(content);
				}
				i++;
			}
			/* 申报单位 */
			String chosenOrgName = bo.getDeclare_name();
			if (StringUtil.notEmpty(chosenOrgName)) {
				xwpfParagraphs.get(i).createRun().setText(chosenOrgName);
			}
			//填充勾选的数据
			fillDeclarationData(tables.get(0),bo);
			fillDecEduExpData(tables.get(1),bo.getDecEduExpList()); // 学习经历
			fillDecWorkExpData(tables.get(2),bo.getDecWorkExpList()); //工作经历
			fillDecAcadeData(tables.get(3),bo.getDecAcadeList()); //学术兼职
			fillDecTextbookPmphData(tables.get(4),bo.getDecTextbookPmphList()); // 人卫社教材编写情况
			fillDecMonographData(tables.get(5),bo.getDecMonographList()); // 图书出版情况
			fillDecEditorBookData(tables.get(6),bo.getDecEditorBookList()); // 主编或参编图书情况
			fillDecArticlePublishedData(tables.get(7),bo.getDecArticlePublishedList()); // 文章发表情况
			fillDecProfessionAwardData(tables.get(8),bo.getDecProfessionAwardList()); // 本专业获奖情况
			fillProductTypeData(tables.get(9),bo.getProductSubjectTypeList()); // 学科分类
			fillProductTypeData(tables.get(10),bo.getProductContentTypeList()); // 内容分类
			fillProductProfessionTypeData(tables.get(11),bo.getProductProfessionTypeList()); // 申报专业
			fillRemarkData(tables.get(12),bo.getRemark()); // 备注


			map.put(filename, removeEmptyTables(document, filter));
			map.put(filename, document);
		}
		return map;
	}




	private String generateFileName(ExpertationVO bo ,String decSequese) throws CheckedServiceException {
		String realname = bo.getRealname();
		String filename;
		if (StringUtil.isEmpty(realname)) {
			realname = "未署名";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(decSequese);
		sb.append(realname);
		sb.append(".docx");
		filename = sb.toString();
		return filename;
	}

	// 直接使用字符串判断
	private XWPFDocument removeEmptyTables(XWPFDocument document, String filter) {
		List<XWPFTable> tables = document.getTables();
		for (int i = 11; i >= 1; i--) {
			String str = String.valueOf(filter.charAt(i - 1));
			if (str.equals("0")) {
				int index = document.getPosOfTable(tables.get(i + 1));
				document.removeBodyElement(index);
				document.removeBodyElement(index - 1);
			}
		}
		return document;
	}

	//基础信息
	private XWPFTable fillDeclarationData(XWPFTable table, ExpertationVO bo) {
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells = rows.get(0).getTableCells();
		/* 第一行 */
		String realname = bo.getRealname();
		if (StringUtil.notEmpty(realname)) {
			cells.get(1).setText(realname);
		}
		/* 第二行 */
		cells = rows.get(1).getTableCells();
		String sex = bo.getSex();
		if (StringUtil.notEmpty(sex)) {
			cells.get(1).setText(sex);
		}
		Date birthday = bo.getBirthday();
		String bir = DateUtil.date2Str(birthday,"yyyy-MM-dd");
		if (StringUtil.notEmpty(bir)) {
			cells.get(3).setText(bir);
		}

		/* 第三行 */
		cells = rows.get(2).getTableCells();
		String orgname = bo.getOrg_name();
		if (StringUtil.notEmpty(orgname)) {
			cells.get(1).setText(orgname);
		}
		String position = bo.getPosition();
		if (StringUtil.notEmpty(position)) {
			cells.get(3).setText(position);
		}
		String title = bo.getTitle();
		if (StringUtil.notEmpty(title)) {
			cells.get(5).setText(title);
		}
		/* 第四行 */
		cells = rows.get(3).getTableCells();
		String postcode = bo.getPostcode();
		if (StringUtil.notEmpty(postcode)) {
			cells.get(1).setText(postcode);
		}
		String address = bo.getAddress();
		if (StringUtil.notEmpty(address)) {
			cells.get(3).setText(address);
		}
		/* 第五行 */
		cells = rows.get(4).getTableCells();
		String telephone = bo.getTelephone();
		if (StringUtil.notEmpty(telephone)) {
			cells.get(1).setText(telephone);
		}
		String fax = bo.getFax();
		if (StringUtil.notEmpty(fax)) {
			cells.get(3).setText(fax);
		}
		String handphone = bo.getHandphone();
		if (StringUtil.notEmpty(handphone)) {
			cells.get(5).setText(handphone);
		}
		/* 第六行 */
		cells = rows.get(5).getTableCells();
		String email = bo.getEmail();
		if (StringUtil.notEmpty(email)) {
			cells.get(1).setText(email);
		}
		String idcard = bo.getIdcard(); //证件号码
		if (StringUtil.notEmpty(idcard)) {
			cells.get(3).setText(idcard);
		}
		String educationName = bo.getEducationName();
		if (StringUtil.notEmpty(educationName)) {
			cells.get(5).setText(educationName);
		}

		/* 第七行 */
		cells = rows.get(6).getTableCells();
		String banknumber = bo.getBanknumber();
		if (ObjectUtil.notNull(banknumber)) {
			cells.get(1).setText(banknumber);
		}
		String expertise = bo.getExpertise();
		if (StringUtil.notEmpty(expertise)) {
			cells.get(3).setText(expertise);
		}
		String bankaddress = bo.getBankaddress();
		if (ObjectUtil.notNull(bankaddress)) {
			cells.get(5).setText(bankaddress);
		}
		return table;
	}

	private XWPFTable fillDecEduExpData(XWPFTable table, List<DecEduExp> decEduExps) {
		if (CollectionUtil.isEmpty(decEduExps)) {
			return table;
		}
		if (decEduExps.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decEduExps.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecEduExp decEduExp : decEduExps) {
			cells = rows.get(rowCount).getTableCells();
			String dateBegin = decEduExp.getDateBegin();
			if (StringUtil.isEmpty(dateBegin)) {
				dateBegin = "未知";
			}
			String dateEnd = decEduExp.getDateEnd();
			if (StringUtil.isEmpty(dateEnd)) {
				dateEnd = "至今";
			}
			String value = dateBegin.concat("～").concat(dateEnd);
			cells.get(0).setText(value);
			value = decEduExp.getSchoolName();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			value = decEduExp.getMajor();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decEduExp.getDegree();
			if (StringUtil.notEmpty(value)) {
				cells.get(3).setText(value);
			}
			value = decEduExp.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(4).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecWorkExpData(XWPFTable table, List<DecWorkExp> decWorkExps) {
		if (CollectionUtil.isEmpty(decWorkExps)) {
			return table;
		}
		if (decWorkExps.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decWorkExps.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecWorkExp decWorkExp : decWorkExps) {
			cells = rows.get(rowCount).getTableCells();
			String dateBegin = decWorkExp.getDateBegin();
			if (StringUtil.isEmpty(dateBegin)) {
				dateBegin = "未知";
			}
			String dateEnd = decWorkExp.getDateEnd();
			if (StringUtil.isEmpty(dateEnd)) {
				dateEnd = "至今";
			}
			String value = dateBegin.concat("～").concat(dateEnd);
			cells.get(0).setText(value);
			value = decWorkExp.getOrgName();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			value = decWorkExp.getPosition();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decWorkExp.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(3).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecAcadeData(XWPFTable table, List<DecAcade> decAcadeList) {
		if (CollectionUtil.isEmpty(decAcadeList)) {
			return table;
		}
		if (decAcadeList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decAcadeList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecAcade decAcade : decAcadeList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String orgName = decAcade.getOrgName(); // 主要学术兼职
			if(!StringUtil.isEmpty(orgName)){
				cells.get(0).setText(orgName);
			}
			String rankName = decAcade.getRankName();  //级别
			if(!StringUtil.isEmpty(rankName)){
				cells.get(1).setText(rankName);
			}
			String position = decAcade.getPosition();  // 职务
			if(!StringUtil.isEmpty(position)){
				cells.get(2).setText(position);
			}
			String note = decAcade.getNote();  // 备注
			if(!StringUtil.isEmpty(note)){
				cells.get(3).setText(note);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	//人卫社教材编写情况
	private XWPFTable fillDecTextbookPmphData(XWPFTable table, List<DecTextbookPmph> decTextbookPmphList) {
		if (CollectionUtil.isEmpty(decTextbookPmphList)) {
			return table;
		}
		if (decTextbookPmphList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decTextbookPmphList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}

		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecTextbookPmph decTextbookPmph : decTextbookPmphList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String materialName = decTextbookPmph.getMaterialName(); // 教材名称
			if(!StringUtil.isEmpty(materialName)){
				cells.get(0).setText(materialName);
			}
			String rankName = decTextbookPmph.getRankName();  //级别
			if(!StringUtil.isEmpty(rankName)){
				cells.get(1).setText(rankName);
			}
			String position = decTextbookPmph.getPositionName();  // 编写职务
			if(!StringUtil.isEmpty(position)){
				cells.get(2).setText(position);
			}
			Boolean isDigitalEditor = decTextbookPmph.getIsDigitalEditor();  // 是否数字编委
			if(isDigitalEditor){
				cells.get(3).setText("是");
			}else{
				cells.get(3).setText("否");
			}
			Date publishDate = decTextbookPmph.getPublishDate();
			String date =DateUtil.date2Str(publishDate,"yyyy-MM-dd");
			if(!StringUtil.isEmpty(date)){
				cells.get(3).setText(date);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	//图书出版情况
	private XWPFTable fillDecMonographData(XWPFTable table, List<DecMonograph> decMonographList) {
		if (CollectionUtil.isEmpty(decMonographList)) {
			return table;
		}
		if (decMonographList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decMonographList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}

		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecMonograph decMonograph : decMonographList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String monographName =decMonograph.getMonographName() ; // 专著名称
			if(!StringUtil.isEmpty(monographName)){
				cells.get(0).setText(monographName);
			}
			Date monographDate = decMonograph.getMonographDate();  //专著发表日期

			if(!StringUtil.isEmpty(DateUtil.date2Str(monographDate,"yyyy-MM-dd"))){
				cells.get(1).setText(DateUtil.date2Str(monographDate,"yyyy-MM-dd"));
			}

			if(decMonograph.getIsSelfPaid()){  // 出版方式
				cells.get(2).setText("自费");
			}else{
				cells.get(2).setText("公费");
			}

			String publisher = decMonograph.getPublisher();  // 出版单位
			if(!StringUtil.isEmpty(publisher)){
				cells.get(3).setText(publisher);
			}

			Date publishDate = decMonograph.getPublishDate();
			String date =DateUtil.date2Str(publishDate,"yyyy-MM-dd");
			if(!StringUtil.isEmpty(date)){
				cells.get(4).setText(date);
			}
			if(!StringUtil.isEmpty(decMonograph.getNote())){
				cells.get(5).setText(decMonograph.getNote());
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecEditorBookData(XWPFTable table, List<DecEditorBook> decEditorBookList) {
		if (CollectionUtil.isEmpty(decEditorBookList)) {
			return table;
		}
		if (decEditorBookList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decEditorBookList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}

		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecEditorBook decEditorBook : decEditorBookList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String materialName =decEditorBook.getMaterialName() ; // 专图书名称
			if(!StringUtil.isEmpty(materialName)){
				cells.get(0).setText(materialName);
			}
			String publisher = decEditorBook.getPublisher();  // 出版单位
			if(!StringUtil.isEmpty(publisher)){
				cells.get(1).setText(publisher);
			}
			String publishDate = decEditorBook.getPublishDate();
			if(!StringUtil.isEmpty(publishDate)){
				cells.get(2).setText(publishDate);
			}
			if(!StringUtil.isEmpty(decEditorBook.getNote())){
				cells.get(3).setText(decEditorBook.getNote());
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecArticlePublishedData(XWPFTable table, List<DecArticlePublished> decArticlePublishedList) {
		if (CollectionUtil.isEmpty(decArticlePublishedList)) {
			return table;
		}
		if (decArticlePublishedList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decArticlePublishedList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}

		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecArticlePublished decArticlePublished : decArticlePublishedList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String title =decArticlePublished.getTitle() ; // 题目
			if(!StringUtil.isEmpty(title)){
				cells.get(0).setText(title);
			}
			String periodicalTitle = decArticlePublished.getPeriodicalTitle();  // 期刊名称
			if(!StringUtil.isEmpty(periodicalTitle)){
				cells.get(1).setText(periodicalTitle);
			}
			String periodicalLevel = decArticlePublished.getPeriodicalLevel(); //期刊级别（SCI或国内核心期刊）
			if(!StringUtil.isEmpty(periodicalLevel)){
				cells.get(2).setText(periodicalLevel);
			}
			if(!StringUtil.isEmpty(decArticlePublished.getNote())){
				cells.get(3).setText(decArticlePublished.getNote());
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	//本专业获奖情况
	private XWPFTable fillDecProfessionAwardData(XWPFTable table, List<DecProfessionAward> decProfessionAwardList) {
		if (CollectionUtil.isEmpty(decProfessionAwardList)) {
			return table;
		}
		if (decProfessionAwardList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decProfessionAwardList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}

		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1; //第二行
		for (DecProfessionAward decProfessionAward : decProfessionAwardList) {
			cells = rows.get(rowCount).getTableCells(); //获取这一行的所有单元格
			String title =decProfessionAward.getTitle() ; // 题目
			if(!StringUtil.isEmpty(title)){
				cells.get(0).setText(title);
			}
			String rank = decProfessionAward.getRank();  // 级别
			if(!StringUtil.isEmpty(rank)){
				cells.get(1).setText(rank);
			}

			if(!StringUtil.isEmpty(decProfessionAward.getNote())){
				cells.get(2).setText(decProfessionAward.getNote());
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillProductTypeData(XWPFTable table, List<ProductType> productTypeList) {
		if (CollectionUtil.isEmpty(productTypeList)) {
			return table;
		}
		/*if (productTypeList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < productTypeList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}*/
		List<XWPFTableRow> rows = table.getRows();

		String typeName ="" ;
		for(ProductType productType:productTypeList) {
			if(StringUtil.notEmpty(productType.getType_name())){
				typeName =typeName+ productType.getType_name()+",     ";
			}
		}
		typeName = typeName.substring(0,typeName.lastIndexOf(","));
		if (StringUtil.notEmpty(typeName)) {
			rows.get(0).getCell(0).setText(typeName); //每次添加一个换行符
		}
		return table;
	}

	/*private XWPFTable fillProductContentTypeData(XWPFTable table, List<ProductType> productContentTypeList) {
		if (CollectionUtil.isEmpty(productContentTypeList)) {
			return table;
		}

		if (productContentTypeList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < productContentTypeList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		return null;
	}*/

	private XWPFTable fillProductProfessionTypeData(XWPFTable table, List<ProductProfessionType> productProfessionTypeList) {
		if (CollectionUtil.isEmpty(productProfessionTypeList)) {
			return table;
		}
		/*if (productProfessionTypeList.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < productProfessionTypeList.size(); i++) {
				table.createRow().setHeight(height);
			}
		}*/
		List<XWPFTableRow> rows = table.getRows();
		String typeName ="" ;
		for(ProductProfessionType productProfessionType:productProfessionTypeList) {
			if(StringUtil.notEmpty(productProfessionType.getTypeName())){
				typeName =typeName+productProfessionType.getTypeName()+",     ";
			}
		}
		typeName = typeName.substring(0,typeName.lastIndexOf(","));
		if (StringUtil.notEmpty(typeName)) {
			rows.get(0).getCell(0).setText(typeName); //每次添加一个换行符
		}

		return table;
	}

	private XWPFTable fillRemarkData(XWPFTable table, String remark) {
		if (StringUtil.isEmpty(remark)) {
			return table;
		}

		List<XWPFTableRow> rows = table.getRows();

		if (StringUtil.notEmpty(remark)) {
			rows.get(0).getCell(0).setText(remark);
		}
		return table;
	}





}
