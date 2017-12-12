/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Microsoft Offic Excel 工具类
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class ExcelHelper {

	private class ColumnProperties {

		private HashMap<String, String> map;
		private int colCount;
		private int[] maxLength;

		public ColumnProperties(int colCount, int[] maxLength) {
			this.colCount = colCount;
			this.maxLength = maxLength;
		}

		public int getColCount() {
			return colCount;
		}

		public void setColCount(int colCount) {
			this.colCount = colCount;
		}

		public int[] getMaxLength() {
			return maxLength;
		}

		public int getCurrentMaxElement() {
			return maxLength[colCount];
		}

		public int getMaxElement(int num) {
			if (num > maxLength.length) {
				throw new IllegalArgumentException("参数num超过了数组maxLength的最大长度");
			}
			return maxLength[num];
		}

		public void setCurrentMaxElement(int length) {
			maxLength[colCount] = length;
		}

		public void setMaxElement(int num, int length) {
			if (num > maxLength.length) {
				throw new IllegalArgumentException("参数num超过了数组maxLength的最大长度");
			}
			maxLength[num] = length;
		}

		public void setMaxLength(int[] maxLength) {
			this.maxLength = maxLength;
		}

		public HashMap<String, String> getMap() {
			return map;
		}

		public void setMap(HashMap<String, String> map) {
			this.map = map;
		}
	}

	private final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 根据Map集合创建工作簿
	 *
	 * @param maps
	 *            工作簿的数据源
	 * @param sheetname
	 *            表名
	 * @return 根据Map集合创建的工作簿
	 */
	public Workbook fromMaps(List<Map<String, Object>> maps, String sheetname) {
		if (null == maps || maps.isEmpty()) {
			throw new IllegalArgumentException("生成Excel时数据源对象为空");
		}
		/* 创建工作簿 */
		Workbook workbook = new HSSFWorkbook();
		/* 创建工作表 */
		Sheet sheet = workbook.createSheet(sheetname);
		int rowCount = 0; // 行计数
		int columnCount = 0; // 列计数
		for (Map<String, Object> map : maps) {
			/* 先设置表头，表头等于Map键名 */
			if (rowCount == 0) {
				Row header = sheet.createRow(0);
				for (String key : map.keySet()) {
					Cell cell = header.createCell(columnCount);
					cell.setCellValue(key);
					columnCount++;
				}
				rowCount++;
			}
			columnCount = 0;// 列计数归零
			Row row = sheet.createRow(rowCount);
			/* 开始以键值填充单元格 */
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (null != entry.getValue()) {
					Cell cell = row.createCell(columnCount);
					cell.setCellValue(entry.getValue().toString());
				}
				columnCount++;
			}
			rowCount++;
		}
		return workbook;
	}

	/**
	 * 根据Map集合创建工作簿并导出到指定路径
	 *
	 * @param maps
	 *            工作簿的数据源
	 * @param sheetname
	 *            表名
	 * @param path
	 *            Excel导出路径，为空时导出到本工程路径下
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void exportFromMaps(List<Map<String, Object>> maps, String sheetname, String path)
			throws FileNotFoundException, IOException {
		if(maps.size() < 1 ){
			return ;
		}
		LinkedHashSet<Map<String, Object>> set = new LinkedHashSet<>();
		set.addAll(maps);
		maps.clear();
		maps.addAll(set);
		Workbook workbook = fromMaps(maps, sheetname);
		if (StringUtil.isEmpty(path)) {
			path = "";
		}
		StringBuilder sb = new StringBuilder(path);
		sb.append(sheetname);
		sb.append(".xls");
		try (FileOutputStream out = new FileOutputStream(sb.toString())) {
			workbook.write(out);
			out.flush();
		}
	}

	/**
	 * 根据业务对象（BO）集合创建工作簿
	 *
	 * @param dataSource
	 *            业务对象（BO）集合
	 * @param sheetName
	 *            要生成的Excel表名（非文件名）
	 * @return Excel工作簿
	 */
	public Workbook fromBusinessObjectList(List dataSource, String sheetName)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
		if (null == dataSource || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用于导出Excel的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		sheet = generateHeader(sheet, dataSource.get(0).getClass()); // 生成表头
		headerStyleSetup(workbook, 1); // 设置表头样式
		Field[] fields = dataSource.get(0).getClass().getDeclaredFields();
		/* 设置行计数器 */
		int rowCount = 1;
		/* 记录列数和最大列宽 */
		ColumnProperties columnProperties = new ColumnProperties(1, new int[sheet.getRow(0).getLastCellNum()]);
		/* 遍历list中的对象 */
		Iterator iterator = dataSource.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			Row row = sheet.createRow(rowCount);
			/* 设置序号及宽度 */
			row.createCell(0).setCellValue(String.valueOf(rowCount));
			columnProperties.setMaxElement(0, 2);
			/* 设置列计数器 */
			columnProperties.setColCount(1);
			for (Field field : fields) {
				field.setAccessible(true);// 可访问性设置
				if (field.isAnnotationPresent(ExcelHeader.class)) {
					ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
					String headerName = excelHeader.header();
					if (StringUtil.notEmpty(headerName)) {
						Object o = field.get(object);
						Cell cell = row.createCell(columnProperties.getColCount());
						if (null != o) {
							String value = o.toString();
							cell.setCellValue(value);
							if (value.length() > columnProperties.getCurrentMaxElement()) {
								columnProperties.setCurrentMaxElement(value.length());
							}
						}
						columnProperties.setColCount(columnProperties.getColCount() + 1);
					}
				}
			}
			rowCount++;
		}
		/* 样式调整 */
		return dataStyleSetup(workbook, 1, rowCount, columnProperties);
	}

	/**
	 * 根据业务对象（包含子集合的BO）集合创建工作簿
	 *
	 * @param dataSource
	 *            业务对象（BO）集合
	 * @param sheetName
	 *            要生成的Excel表名（非文件名）
	 * @return Excel工作簿
	 */
	public Workbook fromDeclarationEtcBOList(List<DeclarationEtcBO> dataSource, String sheetName)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
		long startTime = System.currentTimeMillis();// 获取当前时间
		if (null == dataSource || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用于导出Excel的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		sheet = generateDeclarationEtcBOHeader(sheet); // 生成表头
		headerStyleSetup(workbook, 2); // 设置表头样式
		Field[] fields = dataSource.get(0).getClass().getDeclaredFields();
		/* 设置行计数器 */
		int rowCount = 2;
		/* 记录列数和最大列宽 */
		ColumnProperties columnProperties = new ColumnProperties(1, new int[sheet.getRow(1).getLastCellNum()]);
		/* 遍历list中的对象 */
		Iterator iterator = dataSource.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			Row row = sheet.createRow(rowCount);
			/* 设置序号及宽度 */
			row.createCell(0).setCellValue(String.valueOf(rowCount - 1));
			columnProperties.setMaxElement(0, 2);
			/* 设置列计数器 */
			columnProperties.setColCount(1);
			for (Field field : fields) {
				field.setAccessible(true);// 可访问性设置
				if (field.isAnnotationPresent(ExcelHeader.class)) {
					ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
					String headerName = excelHeader.header();
					if (StringUtil.notEmpty(headerName)) {
						switch (headerName) {
						case "学习经历": {
							List<DecEduExp> list = (List<DecEduExp>) field.get(object);
							columnProperties = fillDecEduExpData(list, row, columnProperties);
							break;
						}
						case "工作经历": {
							List<DecWorkExp> list = (List<DecWorkExp>) field.get(object);
							columnProperties = fillDecWorkExpData(list, row, columnProperties);
							break;
						}
						case "教学经历": {
							List<DecTeachExp> list = (List<DecTeachExp>) field.get(object);
							columnProperties = fillDecTeachExpData(list, row, columnProperties);
							break;
						}
						case "学术兼职": {
							List<DecAcade> list = (List<DecAcade>) field.get(object);
							columnProperties = fillDecAcadeData(list, row, columnProperties);
							break;
						}
						case "上版教材参编情况": {
							List<DecLastPosition> list = (List<DecLastPosition>) field.get(object);
							columnProperties = fillDecLastPositionData(list, row, columnProperties);
							break;
						}
						case "精品课程建设情况": {
							List<DecCourseConstruction> list = (List<DecCourseConstruction>) field.get(object);
							columnProperties = fillDecCourseConstructionData(list, row, columnProperties);
							break;
						}
						case "主编国家级规划教材情况": {
							List<DecNationalPlan> list = (List<DecNationalPlan>) field.get(object);
							columnProperties = fillDecNationalPlanData(list, row, columnProperties);
							break;
						}
						case "教材编写情况": {
							List<DecTextbook> list = (List<DecTextbook>) field.get(object);
							columnProperties = fillDecTextbookData(list, row, columnProperties);
							break;
						}
						case "科研情况": {
							List<DecResearch> list = (List<DecResearch>) field.get(object);
							columnProperties = fillDecResearchData(list, row, columnProperties);
							break;
						}
						default:
							Object o = field.get(object);
							Cell cell = row.createCell(columnProperties.getColCount());
							if (null != o) {
								String value = o.toString();
								cell.setCellValue(value);
								if (value.length() > columnProperties.getCurrentMaxElement()) {
									columnProperties.setCurrentMaxElement(value.length());
								}
							}
							columnProperties.setColCount(columnProperties.getColCount() + 1);
							break;
						}
					}
				}
			}
			rowCount++;
		}
		long endTime = System.currentTimeMillis();
		System.err.println("------------------------------------------");
		System.err.println("导入excel时间：" + (endTime - startTime) + "ms");
		/* 样式调整 */
		return dataStyleSetup(workbook, 2, rowCount, columnProperties);
	}

	private CellStyle generateStyle(Workbook workbook, boolean hasBorder, boolean centerAlign,
			boolean hasBackgroundColor) {
		CellStyle cellStyle = workbook.createCellStyle();
		if (hasBorder) {
			cellStyle.setBorderBottom(BorderStyle.THIN);// 下边框
			cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
			cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
			cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		}
		if (centerAlign) {
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		}
		if (hasBackgroundColor) {
			cellStyle.setFillBackgroundColor((short) 13);
		}
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	private Sheet generateHeader(Sheet sheet, Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		/* 获取Excel首行，利用反射设置表头 */
		Row row = sheet.createRow(0);
		Cell numcell = row.createCell(0);
		numcell.setCellValue("序号");
		int count = 1;
		for (Field field : fields) {
			field.setAccessible(true);// 可访问性设置
			/* 仅查找与ExcelHeader注解匹配的表头 */
			if (field.isAnnotationPresent(ExcelHeader.class)) {
				ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
				String headerName = excelHeader.header();
				if (StringUtil.notEmpty(headerName)) {
					Cell cell = row.createCell(count);
					sheet.setColumnWidth(count, (headerName.length() + 1) * 512);// 设置基本列宽度
					cell.setCellValue(headerName);
					count++;
				}
			}
		}
		return sheet;
	}

	private Sheet generateDeclarationEtcBOHeader(Sheet sheet) {
		Field[] fields = DeclarationEtcBO.class.getDeclaredFields();
		Row r1 = sheet.createRow(0);
		Row r2 = sheet.createRow(1);
		Cell numcell = r1.createCell(0);
		numcell.setCellValue("序号");
		CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(region);
		int count = 1;
		for (Field field : fields) {
			field.setAccessible(true);// 可访问性设置
			/* 仅查找与ExcelHeader注解匹配的表头 */
			if (field.isAnnotationPresent(ExcelHeader.class)) {
				ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
				String headerName = excelHeader.header();
				if (StringUtil.notEmpty(headerName)) {
					switch (headerName) {
					case "学习经历": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 3);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("起止时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("学校名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("所学专业");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("学历");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						break;
					}
					case "工作经历": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("起止时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("工作单位");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("职位");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						break;
					}
					case "教学经历": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("起止时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("学校名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("教学科目");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "学术兼职": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("兼职学术组织");
						sheet.setColumnWidth(count, 7 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("级别");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("职务");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						break;
					}
					case "上版教材参编情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 1);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("教材名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("编写职务");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "精品课程建设情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("课程名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("课程级别");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("全年课时");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "主编国家级规划教材情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("教材名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("标准书号");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("教材级别");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "教材编写情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 4);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("教材名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("级别");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("编写职务");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("标准书号");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "科研情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("课题名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("审批单位");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("获奖情况");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					default:
						Cell cell = r1.createCell(count);
						sheet.setColumnWidth(count, (headerName.length() + 1) * 512);// 设置基本列宽度
						cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 1, count, count);
						sheet.addMergedRegion(region);
						count++;
						break;
					}
				}
			}
		}
		return sheet;
	}

	private Workbook headerStyleSetup(Workbook workbook, int headerRowsNumber) {
		CellStyle style = generateStyle(workbook, true, true, false);
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName("微软雅黑");
		style.setFont(font);
		style.setFillBackgroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		Sheet sheet = workbook.getSheetAt(0);
		for (int i = 0; i < headerRowsNumber; i++) {
			Iterator<Cell> it = sheet.getRow(i).cellIterator();
			while (it.hasNext()) {
				Cell cell = it.next();
				cell.setCellStyle(style);
			}
		}
		logger.info("最后一个单元格排序：{}", sheet.getRow(headerRowsNumber - 1).getLastCellNum());
		sheet.createFreezePane(0, headerRowsNumber);
		return workbook;
	}

	private Workbook dataStyleSetup(Workbook workbook, int rowStart, int rowCount, ColumnProperties properties) {
		CellStyle style = generateStyle(workbook, true, true, false);
		Font font = workbook.createFont();
		font.setFontName("微软雅黑");
		style.setFont(font);
		Sheet sheet = workbook.getSheetAt(0);
		for (int i = rowStart; i < rowCount; i++) {
			Iterator<Cell> it = sheet.getRow(i).cellIterator();
			while (it.hasNext()) {
				Cell cell = it.next();
				cell.setCellStyle(style);
			}
		}
		int[] maxLength = properties.getMaxLength();
		for (int j = 0; j < properties.getColCount(); j++) {
			if (maxLength[j] > 0 && sheet.getColumnWidth(j) < (maxLength[j] + 1) * 512) {
				if (maxLength[j] > 15) {
					sheet.setColumnWidth(j, 16 * 512);// 最长15个字符，超出部分换行
				} else {
					sheet.setColumnWidth(j, (maxLength[j] + 1) * 512);// 设置列宽度
				}
			}
		}
		return workbook;
	}

	private ColumnProperties fillDecEduExpData(List<DecEduExp> decEduExps, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decEduExps)) {
			for (int i = 0; i < 4; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 4; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecEduExp decEduExp : decEduExps) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				String dateBegin = decEduExp.getDateBegin();
				String dateEnd = decEduExp.getDateEnd();
				if (StringUtil.isEmpty(dateBegin)) {
					dateBegin = " ";
				}
				if (StringUtil.isEmpty(dateEnd)) {
					dateEnd = "至今";
				}
				value = dateBegin.concat(" ～ ").concat(dateEnd);
				builders.get(index++).append(value);
				if (value.length() / 2 + 1 > maxLength[colCount]) {
					maxLength[colCount] = value.length() / 2 + 1;
				}
				colCount++;
				value = decEduExp.getSchoolName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decEduExp.getMajor();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decEduExp.getDegree();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 4; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecWorkExpData(List<DecWorkExp> decWorkExps, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decWorkExps)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecWorkExp decWorkExp : decWorkExps) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				String dateBegin = decWorkExp.getDateBegin();
				String dateEnd = decWorkExp.getDateEnd();
				if (StringUtil.isEmpty(dateBegin)) {
					dateBegin = " ";
				}
				if (StringUtil.isEmpty(dateEnd)) {
					dateEnd = "至今";
				}
				value = dateBegin.concat(" ～ ").concat(dateEnd);
				builders.get(index++).append(value);
				if (value.length() / 2 + 1 > maxLength[colCount]) {
					maxLength[colCount] = value.length() / 2 + 1;
				}
				colCount++;
				value = decWorkExp.getOrgName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decWorkExp.getPosition();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecTeachExpData(List<DecTeachExp> decTeachExps, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decTeachExps)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecTeachExp decTeachExp : decTeachExps) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				String dateBegin = decTeachExp.getDateBegin();
				String dateEnd = decTeachExp.getDateEnd();
				if (StringUtil.isEmpty(dateBegin)) {
					dateBegin = " ";
				}
				if (StringUtil.isEmpty(dateEnd)) {
					dateEnd = "至今";
				}
				value = dateBegin.concat(" ～ ").concat(dateEnd);
				builders.get(index++).append(value);
				if (value.length() / 2 + 1 > maxLength[colCount]) {
					maxLength[colCount] = value.length() / 2 + 1;
				}
				colCount++;
				value = decTeachExp.getSchoolName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decTeachExp.getSubject();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecAcadeData(List<DecAcade> decAcades, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decAcades)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecAcade decAcade : decAcades) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decAcade.getOrgName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "其他";
				Integer rank = decAcade.getRank();
				if (ObjectUtil.isNull(rank)) {
					rank = 0;
				}
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
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decAcade.getPosition();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecLastPositionData(List<DecLastPosition> decLastPositions, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decLastPositions)) {
			for (int i = 0; i < 2; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 2; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecLastPosition decLastPosition : decLastPositions) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decLastPosition.getMaterialName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "无";
				Integer position = decLastPosition.getPosition();
				if (ObjectUtil.isNull(position)) {
					position = 0;
				}
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
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 2; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecCourseConstructionData(List<DecCourseConstruction> decCourseConstructions, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decCourseConstructions)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecCourseConstruction decCourseConstruction : decCourseConstructions) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decCourseConstruction.getCourseName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "其他";
				switch (decCourseConstruction.getType()) {
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
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decCourseConstruction.getClassHour();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecNationalPlanData(List<DecNationalPlan> decNationalPlans, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decNationalPlans)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecNationalPlan decNationalPlan : decNationalPlans) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decNationalPlan.getMaterialName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decNationalPlan.getIsbn();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "其他";
				switch (decNationalPlan.getRank()) {
				case 1:
					value = "教育部十二五";
					break;
				case 2:
					value = "国家卫计委十二五";
					break;
				case 3:
					value = "两者皆是";
					break;
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecTextbookData(List<DecTextbook> decTextbooks, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decTextbooks)) {
			for (int i = 0; i < 5; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(5);
			for (int i = 0; i < 5; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecTextbook decTextbook : decTextbooks) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decTextbook.getMaterialName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "其他";
				switch (decTextbook.getRank()) {
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
					value = "其他";
					break;
				case 6:
					value = "教育部规划";
					break;
				case 7:
					value = "卫计委规划";
					break;
				case 8:
					value = "区域规划";
					break;
				case 9:
					value = "创新教材";
					break;
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "无";
				switch (decTextbook.getPosition()) {
				case 1:
					value = "主编";
					break;
				case 2:
					value = "副主编";
					break;
				case 3:
					value = "编委";
					break;
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				if (ObjectUtil.isNull(decTextbook.getPublishDate())) {
					value = "";
				} else {
					value = sdf.format(decTextbook.getPublishDate());
				}

				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decTextbook.getIsbn();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 5; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecResearchData(List<DecResearch> decResearchs, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decResearchs)) {
			for (int i = 0; i < 3; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(3);
			for (int i = 0; i < 3; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecResearch decResearch : decResearchs) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decResearch.getResearchName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decResearch.getApprovalUnit();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decResearch.getAward();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 3; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}
}
