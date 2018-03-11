/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecIntention;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.po.DecMoocDigital;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.util.BinaryUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.DecExtensionVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import sun.tools.jconsole.inspector.XTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFSDTCell;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Microsoft Offic Word 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class WordHelper {

	private final Logger logger = LoggerFactory.getLogger(WordHelper.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 将某套教材单本书籍下的多个专家信息批量导出Word到指定目录
	 *
	 * @param materialName
	 *            教材名称
	 * @param textbookPath
	 *            系统唯一临时文件目录/教材名/书序和书名的组合，例如"/home/temp35723882/五年九轮/1.心理学"
	 * @param list
	 *            DeclarationEtcBO对象集合
	 * @param filter
	 *            使用可填项的二进制选中
	 * @throws CheckedServiceException
	 *             已检查的异常
	 */
	public void export(String materialName, String textbookPath, List<DeclarationEtcBO> list, String filter,
			List<MaterialExtension> extensions) throws CheckedServiceException {
		HashMap<String, XWPFDocument> map = fromDeclarationEtcBOList(materialName, list, filter, extensions);
		if (createPath(textbookPath)) {
			if (!textbookPath.endsWith(File.separator)) {
				textbookPath = textbookPath.concat(File.separator);
			}
			File file;
			for (Map.Entry<String, XWPFDocument> entry : map.entrySet()) {
				file = new File(textbookPath.concat(entry.getKey()));
				try {
					FileOutputStream out = new FileOutputStream(file);
					entry.getValue().write(out);
					out.flush();
					out.close();
				} catch (IOException ex) {
					logger.error("Word导出错误：{}", ex.getMessage());
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.FILE_CREATION_FAILED, "未能创建Word文件");
				}
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.DIRECTORY_CREATION_FAILED, "未能创建目录");
		}
	}

	/**
	 * 从一个或多个专家信息对象中读取数据，转化成若干Word文档
	 *
	 * @param materialName
	 *            教材名称
	 * @param list
	 *            DeclarationEtcBO实例集合
	 * @param filter
	 *            使用可填项的二进制选中
	 * @return 包含文档名称和Word格式文档的键值对
	 * @throws CheckedServiceException
	 *             已检查的异常
	 */
	public HashMap<String, XWPFDocument> fromDeclarationEtcBOList(String materialName, List<DeclarationEtcBO> list,
			String filter, List<MaterialExtension> extensions) throws CheckedServiceException {
		InputStream is;
		XWPFDocument document;
		String path = this.getClass().getClassLoader().getResource("ResumeTemplate.docx").getPath();
		HashMap<String, XWPFDocument> map = new HashMap<>(list.size());
		for (DeclarationEtcBO bo : list) {
			try {
				is = new FileInputStream(path);
				document = new XWPFDocument(is);
			} catch (IOException ex) {
				logger.error("读取Word模板文件'ResumeTemplate.docx'时出现错误：{}", ex.getMessage());
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
						CheckedExceptionResult.FILE_CREATION_FAILED, "未找到模板文件");
			}
			if (StringUtil.notEmpty(materialName)) {
				List<XWPFRun> runs = document.getParagraphs().get(0).getRuns();
				runs.get(0).setText(materialName.concat("专家申报表"), 0);
			}
			List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
			List<XWPFTable> tables = document.getTables();
			int i = 21;
			for (MaterialExtension extension : extensions) {
				XmlCursor cursor = xwpfParagraphs.get(i).getCTP().newCursor();
				XWPFParagraph xwpfParagraph = document.insertNewParagraph(cursor);// ---这个是关键
				CTPPr pPPr = xwpfParagraph.getCTP().getPPr() != null ? xwpfParagraph.getCTP().getPPr()
						: xwpfParagraph.getCTP().addNewPPr();
				CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing();
				pSpacing.setLine(BigInteger.valueOf(100L));
				XWPFRun xwpfRun = xwpfParagraph.createRun();
				xwpfRun.setText(extension.getExtensionName());
				xwpfRun.setFontSize(12);
				xwpfRun.setFontFamily("宋体");
				xwpfRun.setBold(true);
				insertXWPFParagraph(extension.getExtensionName(), document);
				i++;
			}
			/* 申报单位 */
			String chosenOrgName = bo.getChosenOrgName();
			if (StringUtil.notEmpty(chosenOrgName)) {
				xwpfParagraphs.get(i).createRun().setText(chosenOrgName);
			}
			i = 21;
			if (!bo.getDecExtensionVOs().isEmpty()) {
				for (DecExtensionVO decExtensionVO : bo.getDecExtensionVOs()) {
					if (ObjectUtil.notNull(decExtensionVO)) {
						List<XWPFTableRow> rows = tables.get(i).getRows();
						String value = decExtensionVO.getContent();
						if (StringUtil.notEmpty(value)) {
							rows.get(0).getCell(0).setText(value);
						}
					}
					i++;
				}
			}
			String filename = generateFileName(bo);
			fillDeclarationPosition(tables.get(0), bo);
			fillDeclarationData(tables.get(1), bo);
			fillDecEduExpData(tables.get(2), bo.getDecEduExps());
			fillDecWorkExpData(tables.get(3), bo.getDecWorkExps());
			fillDecTeachExpData(tables.get(4), bo.getDecTeachExps());
			fillDecAchievementData(tables.get(5), bo.getDecAchievement());
			fillDecAcadeData(tables.get(6), bo.getDecAcades());
			fillDecLastPositionData(tables.get(7), bo.getDecLastPositions());
			fillDecNationalPlanData(tables.get(8), bo.getDecNationalPlans());
			fillDecTextbookPmphData(tables.get(9), bo.getDecTextbookPmphs());
			fillDecTextbookData(tables.get(10), bo.getDecTextbooks());
			fillDecMoocDigitalData(tables.get(11), bo.getDecMoocDigital());
			fillDecCourseConstructionData(tables.get(12), bo.getDecCourseConstructions());
			fillDecResearchData(tables.get(13), bo.getDecResearchs());
			fillDecMonographData(tables.get(14), bo.getDecMonographs());
			fillDecPublishRewardData(tables.get(15), bo.getPublishRewards());
			fillDecSciData(tables.get(16), bo.getDecScis());
			fillDecClinicalRewardData(tables.get(17), bo.getDecClinicalRewards());
			fillDecAcadeRewardData(tables.get(18), bo.getDecAcadeRewards());
			fillDecIntentionData(tables.get(19), bo.getDecIntention());
			map.put(filename, removeEmptyTables(document, filter));
			map.put(filename, document);
		}
		return map;
	}

	private String generateFileName(DeclarationEtcBO bo) throws CheckedServiceException {
		String realname = bo.getRealname();
		String textbookName = bo.getTextbookName().get(0);
		String presetPosition = bo.getPresetPosition().get(0);
		String filename;
		if (StringUtil.notEmpty(textbookName) && StringUtil.notEmpty(presetPosition)) {
			if (StringUtil.isEmpty(realname)) {
				realname = "未署名";
			}
			StringBuilder sb = new StringBuilder();
			sb.append(textbookName);
			sb.append("_");
			sb.append(realname);
			sb.append("_");
			sb.append(presetPosition);
			sb.append(".docx");
			filename = sb.toString();
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					realname.concat("的申报图书或申报职位为空"));
		}
		return filename;
	}

	// 直接使用字符串判断
	private XWPFDocument removeEmptyTables(XWPFDocument document, String filter) {
		List<XWPFTable> tables = document.getTables();
		for (int i = 18; i >= 1; i--) {
			String str = String.valueOf(filter.charAt(i - 1));
			if (str.equals("0")) {
				int index = document.getPosOfTable(tables.get(i + 1));
				document.removeBodyElement(index);
				document.removeBodyElement(index - 1);
			}
		}
		return document;
	}

	// 使用位运算判断（产生了问题）
	private XWPFDocument removeEmptyTables(XWPFDocument document, Integer filter) {
		List<XWPFTable> tables = document.getTables();
		for (int i = 18; i >= 1; i--) {
			if (BinaryUtil.getBit(filter, i - 1) == false) {
				int index = document.getPosOfTable(tables.get(i));
				document.removeBodyElement(index);
				document.removeBodyElement(index - 1);
			}
		}
		return document;
	}

	private XWPFTable fillDeclarationPosition(XWPFTable table, DeclarationEtcBO bo) {
		List<XWPFTableRow> rows = table.getRows();
		List<String> textbookName = bo.getTextbookName();
		List<String> presetPosition = bo.getPresetPosition();
		if (textbookName.size() > 0) {
			for (int i = 0; i < textbookName.size(); i++) {
				int height = table.getRow(i).getHeight();
				List<XWPFTableCell> cells = rows.get(i).getTableCells();
				cells.get(0).setText("《".concat(textbookName.get(i)).concat("》"));
				cells.get(1).setText(presetPosition.get(i));
				if (i + 1 < textbookName.size()) {
					table.createRow().setHeight(height);
				}

			}
		}
		return table;
	}

	private XWPFTable fillDeclarationData(XWPFTable table, DeclarationEtcBO bo) {
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
		String birthday = bo.getBirthday();
		if (StringUtil.notEmpty(birthday)) {
			cells.get(3).setText(birthday);
		}
		Integer experience = bo.getExperience();
		if (ObjectUtil.notNull(experience)) {
			cells.get(5).setText(String.valueOf(experience));
		}
		/* 第三行 */
		cells = rows.get(2).getTableCells();
		String orgname = bo.getOrgName();
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
		String idcard = bo.getIdcard();
		if (StringUtil.notEmpty(idcard)) {
			cells.get(3).setText(idcard);
		}
		String degree = bo.getDegree();
		if (StringUtil.notEmpty(degree)) {
			cells.get(5).setText(degree);
		}

		/* 第七行 */
		cells = rows.get(6).getTableCells();
		Boolean isDispensed = bo.getIsDispensed();
		if (ObjectUtil.notNull(isDispensed)) {
			cells.get(1).setText(isDispensed ? "是" : "否");
		}
		String expertise = bo.getExpertise();
		if (StringUtil.notEmpty(expertise)) {
			cells.get(3).setText(expertise);
		}
		Boolean isUtec = bo.getIsUtec();
		if (ObjectUtil.notNull(isUtec)) {
			cells.get(5).setText(isUtec ? "是" : "否");
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

	private XWPFTable fillDecTeachExpData(XWPFTable table, List<DecTeachExp> decTeachExps) {
		if (CollectionUtil.isEmpty(decTeachExps)) {
			return table;
		}
		if (decTeachExps.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decTeachExps.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecTeachExp decTeachExp : decTeachExps) {
			cells = rows.get(rowCount).getTableCells();
			String dateBegin = decTeachExp.getDateBegin();
			if (StringUtil.isEmpty(dateBegin)) {
				dateBegin = "未知";
			}
			String dateEnd = decTeachExp.getDateEnd();
			if (StringUtil.isEmpty(dateEnd)) {
				dateEnd = "至今";
			}
			String value = dateBegin.concat("～").concat(dateEnd);
			cells.get(0).setText(value);
			value = decTeachExp.getSchoolName();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			value = decTeachExp.getSubject();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decTeachExp.getNote();
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

	private XWPFTable fillDecAchievementData(XWPFTable table, DecAchievement decAchievement) {
		if (null == decAchievement) {
			return table;
		}
		List<XWPFTableRow> rows = table.getRows();
		String value = decAchievement.getContent();
		if (StringUtil.notEmpty(value)) {
			rows.get(0).getCell(0).setText(value);
		}
		return table;
	}

	private XWPFTable fillDecMoocDigitalData(XWPFTable table, DecMoocDigital decMoocDigital) {
		if (null == decMoocDigital) {
			return table;
		}
		List<XWPFTableRow> rows = table.getRows();
		String value = decMoocDigital.getContent();
		if (StringUtil.notEmpty(value)) {
			rows.get(0).getCell(0).setText(value);
		}
		return table;
	}

	private XWPFTable fillDecIntentionData(XWPFTable table, DecIntention decIntention) {
		if (null == decIntention) {
			return table;
		}
		List<XWPFTableRow> rows = table.getRows();
		String value = decIntention.getContent();
		if (StringUtil.notEmpty(value)) {
			rows.get(0).getCell(0).setText(value);
		}
		return table;
	}

	private XWPFTable fillDecAcadeData(XWPFTable table, List<DecAcade> decAcades) {
		if (CollectionUtil.isEmpty(decAcades)) {
			return table;
		}
		if (decAcades.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decAcades.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecAcade decAcade : decAcades) {
			cells = rows.get(rowCount).getTableCells();
			String value = decAcade.getOrgName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Integer rank = decAcade.getRank();// 1=国际/2=国家/3=省部/4=其他
			if (null != rank) {
				switch (rank) {
				case 1:
					value = "国际";
					break;
				case 2:
					value = "国家";
					break;
				case 3:
					value = "省部";
					break;
				case 4:
					value = "其他";
					break;
				default:
					value = "无";
					break;
				}
				cells.get(1).setText(value);
			}
			value = decAcade.getPosition();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decAcade.getNote();
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

	private XWPFTable fillDecLastPositionData(XWPFTable table, List<DecLastPosition> decLastPositions) {
		if (CollectionUtil.isEmpty(decLastPositions)) {
			return table;
		}
		if (decLastPositions.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decLastPositions.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecLastPosition decLastPosition : decLastPositions) {
			cells = rows.get(rowCount).getTableCells();
			String value = decLastPosition.getMaterialName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Integer position = decLastPosition.getPosition();// 0=无/1=主编/2=副主编/3=编委
			if (null != position) {
				switch (position) {
				case 1:
					value = "主编";
					break;
				case 2:
					value = "副主编";
					break;
				case 3:
					value = "编委";
					break;
				case 4:
					value = "数字编委";
					break;
				default:
					value = "无";
					break;
				}
				cells.get(1).setText(value);
			}
			value = decLastPosition.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecCourseConstructionData(XWPFTable table,
			List<DecCourseConstruction> decCourseConstructions) {
		if (CollectionUtil.isEmpty(decCourseConstructions)) {
			return table;
		}
		if (decCourseConstructions.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decCourseConstructions.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecCourseConstruction decCourseConstruction : decCourseConstructions) {
			cells = rows.get(rowCount).getTableCells();
			String value = decCourseConstruction.getCourseName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Integer type = decCourseConstruction.getType();// 1=国家/2=省部/3=学校
			if (null != type) {
				switch (type) {
				case 1:
					value = "国家";
					break;
				case 2:
					value = "省部";
					break;
				case 3:
					value = "学校";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(1).setText(value);
			}
			value = decCourseConstruction.getClassHour();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decCourseConstruction.getNote();
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

	private XWPFTable fillDecNationalPlanData(XWPFTable table, List<DecNationalPlan> decNationalPlans) {
		if (CollectionUtil.isEmpty(decNationalPlans)) {
			return table;
		}
		if (decNationalPlans.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decNationalPlans.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecNationalPlan decNationalPlan : decNationalPlans) {
			cells = rows.get(rowCount).getTableCells();
			String value = decNationalPlan.getMaterialName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			value = decNationalPlan.getIsbn();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			Integer rank = decNationalPlan.getRank();// 1=教育部十二五/2=国家卫计委十二五/3=both
			if (null != rank) {
				switch (rank) {
				case 1:
					value = "教育部十二五";
					break;
				case 2:
					value = "国家卫计委十二五";
					break;
				case 3:
					value = "教育部和国家卫计委十二五";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(2).setText(value);
			}
			value = decNationalPlan.getNote();
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

	private XWPFTable fillDecTextbookPmphData(XWPFTable table, List<DecTextbookPmph> decTextbookPmphs) {
		if (CollectionUtil.isEmpty(decTextbookPmphs)) {
			return table;
		}
		if (decTextbookPmphs.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decTextbookPmphs.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecTextbookPmph decTextbookPmph : decTextbookPmphs) {
			cells = rows.get(rowCount).getTableCells();
			String value = decTextbookPmph.getMaterialName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			/* 0=无/1=国家/2=省部/3=协编/4=校本/5=其他教材 */
			Integer rank = decTextbookPmph.getRank();
			if (null != rank) {
				switch (rank) {
				case 0:
					value = "无";
				case 1:
					value = "国家";
					break;
				case 2:
					value = "省部";
					break;
				case 3:
					value = "协编";
					break;
				case 4:
					value = "校本";
					break;
				case 5:
					value = "其他教材";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(1).setText(value);
			}
			Integer position = decTextbookPmph.getPosition();// 0=无/1=主编/2=副主编/3=编委
			if (null != position) {
				switch (position) {
				case 1:
					value = "主编";
					break;
				case 2:
					value = "副主编";
					break;
				case 3:
					value = "编委";
					break;
				case 4:
					value = "数字编委";
					break;
				default:
					value = "无";
					break;
				}
				cells.get(2).setText(value);
			}
			Date publishDate = decTextbookPmph.getPublishDate();
			if (null != publishDate) {
				value = sdf.format(publishDate);
				cells.get(3).setText(value);
			}
			value = decTextbookPmph.getIsbn();
			if (StringUtil.notEmpty(value)) {
				cells.get(4).setText(value);
			}
			value = decTextbookPmph.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(5).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecTextbookData(XWPFTable table, List<DecTextbook> decTextbooks) {
		if (CollectionUtil.isEmpty(decTextbooks)) {
			return table;
		}
		if (decTextbooks.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decTextbooks.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecTextbook decTextbook : decTextbooks) {
			cells = rows.get(rowCount).getTableCells();
			String value = decTextbook.getMaterialName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			/* 0=无/1=国家/2=省部/3=协编/4=校本/5=其他教材 */
			Integer rank = decTextbook.getRank();
			if (null != rank) {
				switch (rank) {
				case 0:
					value = "无";
				case 1:
					value = "国家";
					break;
				case 2:
					value = "省部";
					break;
				case 3:
					value = "协编";
					break;
				case 4:
					value = "校本";
					break;
				case 5:
					value = "其他教材";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(1).setText(value);
			}
			Integer position = decTextbook.getPosition();// 0=无/1=主编/2=副主编/3=编委
			if (null != position) {
				switch (position) {
				case 1:
					value = "主编";
					break;
				case 2:
					value = "副主编";
					break;
				case 3:
					value = "编委";
					break;
				case 4:
					value = "数字编委";
					break;
				default:
					value = "无";
					break;
				}
				cells.get(2).setText(value);
			}
			Date publishDate = decTextbook.getPublishDate();
			if (null != publishDate) {
				value = sdf.format(publishDate);
				cells.get(3).setText(value);
			}
			value = decTextbook.getPublisher();
			if (StringUtil.notEmpty(value)) {
				cells.get(4).setText(value);
			}
			value = decTextbook.getIsbn();
			if (StringUtil.notEmpty(value)) {
				cells.get(5).setText(value);
			}
			value = decTextbook.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(6).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecResearchData(XWPFTable table, List<DecResearch> decResearchs) {
		if (CollectionUtil.isEmpty(decResearchs)) {
			return table;
		}
		if (decResearchs.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decResearchs.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecResearch decResearch : decResearchs) {
			cells = rows.get(rowCount).getTableCells();
			String value = decResearch.getResearchName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			value = decResearch.getApprovalUnit();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			value = decResearch.getAward();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decResearch.getNote();
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

	private XWPFTable fillDecMonographData(XWPFTable table, List<DecMonograph> decMonographs) {
		if (CollectionUtil.isEmpty(decMonographs)) {
			return table;
		}
		if (decMonographs.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decMonographs.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecMonograph decMonograph : decMonographs) {
			cells = rows.get(rowCount).getTableCells();
			String value = decMonograph.getMonographName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Date monographDate = decMonograph.getMonographDate();
			if (null != monographDate) {
				value = sdf.format(monographDate);
				cells.get(1).setText(value);
			}
			value = (decMonograph.getIsSelfPaid() ? "自费" : "公费");
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decMonograph.getPublisher();
			if (StringUtil.notEmpty(value)) {
				cells.get(3).setText(value);
			}
			Date publishDate = decMonograph.getPublishDate();
			if (null != publishDate) {
				value = sdf.format(publishDate);
				cells.get(4).setText(value);
			}
			value = decMonograph.getNote();
			if (StringUtil.notEmpty(value)) {
				cells.get(5).setText(value);
			}
			for (XWPFTableCell cell : cells) {
				cell.setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			rowCount++;
		}
		return table;
	}

	private XWPFTable fillDecPublishRewardData(XWPFTable table, List<DecPublishReward> decPublishRewards) {
		if (CollectionUtil.isEmpty(decPublishRewards)) {
			return table;
		}
		if (decPublishRewards.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decPublishRewards.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecPublishReward decPublishReward : decPublishRewards) {
			cells = rows.get(rowCount).getTableCells();
			String value = decPublishReward.getRewardName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Date rewardDate = decPublishReward.getRewardDate();
			if (null != rewardDate) {
				value = sdf.format(rewardDate);
				cells.get(1).setText(value);
			}
			value = decPublishReward.getAwardUnit();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			value = decPublishReward.getNote();
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

	private XWPFTable fillDecSciData(XWPFTable table, List<DecSci> decScis) {
		if (CollectionUtil.isEmpty(decScis)) {
			return table;
		}
		if (decScis.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decScis.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecSci decSci : decScis) {
			cells = rows.get(rowCount).getTableCells();
			String value = decSci.getPaperName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			value = decSci.getJournalName();
			if (StringUtil.notEmpty(value)) {
				cells.get(1).setText(value);
			}
			value = decSci.getFactor();
			if (StringUtil.notEmpty(value)) {
				cells.get(2).setText(value);
			}
			Date publishDate = decSci.getPublishDate();
			if (null != publishDate) {
				value = sdf.format(publishDate);
				cells.get(3).setText(value);
			}
			value = decSci.getNote();
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

	private XWPFTable fillDecClinicalRewardData(XWPFTable table, List<DecClinicalReward> decClinicalRewards) {
		if (CollectionUtil.isEmpty(decClinicalRewards)) {
			return table;
		}
		if (decClinicalRewards.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decClinicalRewards.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecClinicalReward decClinicalReward : decClinicalRewards) {
			cells = rows.get(rowCount).getTableCells();
			String value = decClinicalReward.getRewardName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Date rewardDate = decClinicalReward.getRewardDate();
			if (null != rewardDate) {
				value = sdf.format(rewardDate);
				cells.get(1).setText(value);
			}
			Integer type = decClinicalReward.getAwardUnit();
			if (null != type) {
				switch (type) {
				case 0:
					value = "无";
					break;
				case 1:
					value = "国际";
					break;
				case 2:
					value = "国家";
					break;
				case 3:
					value = "省部";
					break;
				case 4:
					value = "市";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(2).setText(value);
			}
			value = decClinicalReward.getNote();
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

	private XWPFTable fillDecAcadeRewardData(XWPFTable table, List<DecAcadeReward> decAcadeRewards) {
		if (CollectionUtil.isEmpty(decAcadeRewards)) {
			return table;
		}
		if (decAcadeRewards.size() > 1) {
			int height = table.getRow(1).getHeight();
			for (int i = 1; i < decAcadeRewards.size(); i++) {
				table.createRow().setHeight(height);
			}
		}
		List<XWPFTableRow> rows = table.getRows();
		List<XWPFTableCell> cells;
		int rowCount = 1;
		for (DecAcadeReward decAcadeReward : decAcadeRewards) {
			cells = rows.get(rowCount).getTableCells();
			String value = decAcadeReward.getRewardName();
			if (StringUtil.notEmpty(value)) {
				cells.get(0).setText(value);
			}
			Date rewardDate = decAcadeReward.getRewardDate();
			if (null != rewardDate) {
				value = sdf.format(rewardDate);
				cells.get(1).setText(value);
			}
			Integer type = decAcadeReward.getAwardUnit();
			if (null != type) {
				switch (type) {
				case 0:
					value = "无";
					break;
				case 1:
					value = "国际";
					break;
				case 2:
					value = "国家";
					break;
				case 3:
					value = "省部";
					break;
				case 4:
					value = "市";
					break;
				default:
					value = "其他";
					break;
				}
				cells.get(2).setText(value);
			}
			value = decAcadeReward.getNote();
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

	/**
	 * 路径创建
	 *
	 * @param dest
	 *            要创建的路径地址
	 */
	private boolean createPath(String dest) {
		File destDir;
		if (dest.endsWith(File.separator)) {
			destDir = new File(dest);// 给出的是路径时
		} else {
			destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			return destDir.mkdirs();
		}
		return true;
	}

	public static void insertXWPFParagraph(String key, XWPFDocument doc2) {
		List<XWPFParagraph> paragraphList = doc2.getParagraphs();
		if (paragraphList != null && paragraphList.size() > 0) {
			for (int i = 0; i < paragraphList.size(); i++) {
				List<XWPFRun> runs = paragraphList.get(i).getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if (text != null) {
						if (text.equals(key)) {
							XmlCursor cursor = paragraphList.get(i + 1).getCTP().newCursor();
							XWPFTable xwpfTable = doc2.insertNewTbl(cursor);// ---这个是关键
						}
					}
				}
			}
		}
	}
}
