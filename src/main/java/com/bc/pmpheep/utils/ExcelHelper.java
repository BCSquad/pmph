/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.bo.DecPositionBO;
import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.bo.WriterBO;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

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
	 * 根据List 的object[] 集合创建工作簿
	 *
	 * @introduction
	 * @author Mryang
	 * @createDate 2018年1月2日 下午4:34:41
	 * @param lst
	 * @param sheetname
	 * @param path
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void exportFromList(List<Object[]> lst, String sheetname, String path)
			throws FileNotFoundException, IOException {
		if (lst.size() < 1) {
			return;
		}
		/* 创建工作簿 */
		Workbook workbook = new HSSFWorkbook();
		/* 创建工作表 */
		Sheet sheet = workbook.createSheet(sheetname);
		// 装入数据
		for (int i = 0; i < lst.size(); i++) {
			// 创建行
			Row row = sheet.createRow(i);
			// 本行数据
			Object[] objArray = lst.get(i);
			// 放置列数据
			for (int j = 0; j < objArray.length; j++) {
				// 创建单元格
				Cell cell = row.createCell(j);
				cell.setCellValue(String.valueOf(objArray[j]));
			}
		}
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
	 * 根据Map集合创建工作簿
	 *
	 * @param maps
	 *            数据源
	 * @param sheetname
	 *            表名
	 * @return 根据Map集合创建的工作簿
	 */
	public Workbook fromResultMaps(List<Map<String, Object>> maps, String sheetname) {
		if (null == maps || maps.isEmpty()) {
			throw new IllegalArgumentException("生成Excel时数据源对象为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetname);
		int rowCount = 0;
		int columnCount = 0;
		for (Map<String, Object> map : maps) {
			if (rowCount == 0) {
				Row header = sheet.createRow(rowCount);
				for (String key : map.keySet()) {
					Cell cell = header.createCell(columnCount);
					cell.setCellValue(key);
					columnCount++;
				}
				rowCount++;
			}
			columnCount = 0;
			String[] reason = map.get("异常原因").toString().split("。");
			Integer number = reason.length;
			String[] dealWith = map.get("处理方式").toString().split("。");
			if (number > 1) {
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 1, 1));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 2, 2));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 3, 3));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 4, 4));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 5, 5));
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + number - 1, 6, 6));
			}
			for (int i = 0; i < reason.length; i++) {
				Row row = sheet.createRow(rowCount + i);
				columnCount = 0;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Cell cell = row.createCell(columnCount);
					if (i == 0) {
						if ("异常原因".equals(entry.getKey())) {
							cell.setCellValue(reason[i]);
						} else if ("处理方式".equals(entry.getKey())) {
							cell.setCellValue(dealWith[i]);
						} else {
							cell.setCellValue(entry.getValue().toString());
						}
					} else {
						if ("异常原因".equals(entry.getKey())) {
							cell.setCellValue(reason[i]);
						} else if ("处理方式".equals(entry.getKey())) {
							cell.setCellValue(dealWith[i]);
						}
					}
					columnCount++;
				}
			}
			rowCount = rowCount + reason.length;
		}
		int[] maxLength = { 5, 10, 5, 5, 5, 5, 5, 30, 45 };
		return dataStyleSetup(workbook, 0, rowCount, new ColumnProperties(9, maxLength));
	}

	/**
	 * 导出总体统计结果Excel文档
	 *
	 * @param maps
	 *            导出数据源
	 * @param sheetname
	 *            表名
	 * @param path
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void exportFromResultMaps(List<Map<String, Object>> maps, String sheetname, String path)
			throws FileNotFoundException, IOException {
		if (maps.size() < 1) {
			return;
		}
		LinkedHashSet<Map<String, Object>> set = new LinkedHashSet<>();
		set.addAll(maps);
		maps.clear();
		maps.addAll(set);
		Workbook workbook = fromResultMaps(maps, sheetname);
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
	 * 根据教材遴选表业务对象（DecPositionBO）集合创建工作簿，适用于主编/副主编导出
	 *
	 * @param dataSource
	 *            DecPositionBO业务对象集合
	 * @param sheetName
	 *            工作表名称
	 * @return 根据Map集合创建的工作簿
	 */
	public Workbook fromDecPositionBOList(List<DecPositionBO> dataSource, String sheetName) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("书序");
		header.createCell(1).setCellValue("书名");
		header.createCell(2).setCellValue("版次");
		header.createCell(3).setCellValue("序号");
		header.createCell(4).setCellValue("姓名");
		header.createCell(5).setCellValue("申报单位");
		header.createCell(6).setCellValue("编写职位");
		headerStyleSetup(workbook, 1); // 设置表头样式
		/*** 重新设置头背景颜色end ***/
		for (int i = 0; i < 7; i++) {
			Cell cell = workbook.getSheet(sheetName).getRow(0).getCell(i);
			CellStyle style = cell.getCellStyle();
			style.setFillBackgroundColor(HSSFColorPredefined.GREY_50_PERCENT.getIndex());
			cell.setCellStyle(style);
		}
		/*** 重新设置头背景颜色end ***/
		/* 设置行计数器 */
		int rowCount = 1;
		if (null != dataSource && !dataSource.isEmpty()) {
			/* 遍历list中的对象 */
			for (DecPositionBO bo : dataSource) {
				List<WriterBO> writers = bo.getWriters();
				if (CollectionUtil.isEmpty(writers)) {
					continue;
				}
				Integer zhuBianTotalNum = 0;
				Integer fuZhuBianTotalNum = 0;
				for (WriterBO writer : writers) {
					Integer chosenPosition = writer.getChosenPosition();
					if (null != chosenPosition && (chosenPosition == 12 || chosenPosition == 4)) {
						zhuBianTotalNum++;
					} else if (null != chosenPosition && (chosenPosition == 10 || chosenPosition == 2)) {
						fuZhuBianTotalNum++;
					}
				}
				Row row = sheet.createRow(rowCount);
				row.createCell(0).setCellValue(bo.getSort());
				row.createCell(1).setCellValue(bo.getTextbookName());
				row.createCell(2).setCellValue(bo.getTextbookRound());
				int writerNum = 1;
				for (WriterBO writer : writers) {
					if (writerNum > 1) {
						row = sheet.createRow(rowCount);
						row.createCell(0);
						row.createCell(1);
						row.createCell(2);
					}
					row.createCell(3).setCellValue(String.valueOf(writerNum));
					row.createCell(4).setCellValue(writer.getRealname());
					row.createCell(5).setCellValue(writer.getChosenOrgName());
					Integer chosenPosition = writer.getChosenPosition();
					if (null == chosenPosition) {
						continue;
					}
					String position = "";
					String rank = "";
					if (null != writer.getRank()) {
						rank = String.valueOf(writer.getRank());
					}
					if (null != chosenPosition && chosenPosition == 12) {
						position = "主编 " + zhuBianTotalNum + "-" + rank + "，数字编委";
					} else if (null != chosenPosition && chosenPosition == 4) {
						position = "主编 " + zhuBianTotalNum + "-" + rank;
					} else if (null != chosenPosition && chosenPosition == 10) {
						position = "副主编 " + fuZhuBianTotalNum + "-" + rank + "，数字编委";
					} else if (null != chosenPosition && chosenPosition == 2) {
						position = "副主编 " + fuZhuBianTotalNum + "-" + rank;
					} else if (null != chosenPosition && chosenPosition == 9) {
						position = "编委，数字编委";
					} else if (null != chosenPosition && chosenPosition == 1) {
						position = "编委";
					}
					row.createCell(6).setCellValue(position);
					writerNum++;
					rowCount++;
				}
			}
		}
		int[] maxLength = { 2, 12, 2, 2, 5, 15, 9 };
		return dataStyleSetup(workbook, 1, rowCount, new ColumnProperties(7, maxLength));
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
		if (maps.size() < 1) {
			return;
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
			short maxLineHeight = 1;
			columnProperties.setMaxElement(0, 2);
			/* 设置列计数器 */
			columnProperties.setColCount(1);
			for (Field field : fields) {
				field.setAccessible(true);// 可访问性设置

				if (field.isAnnotationPresent(ExcelHeader.class)) {
					ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
					String headerName = excelHeader.header();
					String cellType = excelHeader.cellType();
					if (StringUtil.notEmpty(headerName)) {
						Object o = field.get(object);
						Cell cell = row.createCell(columnProperties.getColCount());

						if (null != o) {
							String value = o.toString();
							if(StringUtil.notEmpty(cellType)&&"2".equals(cellType)){
								//cell.setCellType(Integer.parseInt(cellType));
								//cell.setCellFormula(value);
								cell.setCellValue(value);
								if(StringUtil.notEmpty(value)){
									String seperator = "\r\n";
									//String nowrapStr = value.replace(seperator,"");
									//int lineHeight = (value.length()-nowrapStr.length())/seperator.length() +1;
									String[] strPerLine= value.split(seperator);
									int lineHeight = strPerLine.length+1;
									for (String s:strPerLine) {
										if(s.length()>15){
											lineHeight += 1;
										}
									}
									maxLineHeight =  (short) lineHeight>maxLineHeight?(short) lineHeight:maxLineHeight;
								}
							}else{
								cell.setCellValue(value);
							}
							if (value.length() > columnProperties.getCurrentMaxElement()) {
								columnProperties.setCurrentMaxElement(value.length());
							}
						}
						columnProperties.setColCount(columnProperties.getColCount() + 1);
					}
				}
			}
			row.setHeight((short)(maxLineHeight * row.getHeight()));
			rowCount++;
		}
		sheet.setForceFormulaRecalculation(true);
		/* 样式调整 */
		return dataStyleSetup(workbook, 1, rowCount, columnProperties);
	}


	/**
	 *
	 * @param sheetMap
	 * @return
	 * @throws CheckedServiceException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Workbook fromSheetMap(Map<String,Object> sheetMap)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {

		Workbook workbook = new HSSFWorkbook();

		for (Map.Entry e:sheetMap.entrySet()) {
			String key = e.getKey().toString();
			List<ExpertationCountnessVO> dataSource = (ArrayList<ExpertationCountnessVO>)e.getValue();

			if (null == dataSource || dataSource.isEmpty()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
						"用于导出Excel的数据源为空");
			}

			Sheet sheet = workbook.createSheet(key);
			sheet = generateHeader(sheet, dataSource.get(0).getClass()); // 生成表头
			headerStyleSetup(workbook, sheet, 1);// 设置表头样式
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
				short maxLineHeight = 1;
				columnProperties.setMaxElement(0, 2);
				/* 设置列计数器 */
				columnProperties.setColCount(1);
				for (Field field : fields) {
					field.setAccessible(true);// 可访问性设置
					if (field.isAnnotationPresent(ExcelHeader.class)) {
						ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
						String headerName = excelHeader.header();
						String cellType = excelHeader.cellType();
						if (StringUtil.notEmpty(headerName)) {
							Object o = field.get(object);
							Cell cell = row.createCell(columnProperties.getColCount());

							if (null != o) {
								String value = o.toString();
								if(StringUtil.notEmpty(cellType)&&"2".equals(cellType)){
									//cell.setCellType(Integer.parseInt(cellType));
									//cell.setCellFormula(value);
									cell.setCellValue(value);
									if(StringUtil.notEmpty(value)){
										String seperator = "\r\n";

										String nowrapStr = value.replace(seperator,"");
										//int lineHeight = (value.length()-nowrapStr.length())/seperator.length() +1;

										String[] strPerLine= value.split(nowrapStr);
										int lineHeight = strPerLine.length+1;
										for (String s:strPerLine) {
											if(s.length()>15){
												lineHeight += 1;
											}
										}

										maxLineHeight =  (short) lineHeight>maxLineHeight?(short) lineHeight:maxLineHeight;

									}
								}else{
									cell.setCellValue(value);
								}
								if (value.length() > columnProperties.getCurrentMaxElement()) {
									columnProperties.setCurrentMaxElement(value.length());
								}
							}
							columnProperties.setColCount(columnProperties.getColCount() + 1);
						}
					}
				}
				row.setHeight((short)(maxLineHeight * row.getHeight()));
				rowCount++;
			}
			sheet.setForceFormulaRecalculation(true);
			CellStyle style = generateStyle(workbook, true, true, false);
			Font font = workbook.createFont();
			font.setFontName("微软雅黑");
			style.setFont(font);
			/* 样式调整 */
			dataStyleSetUp(sheet, rowCount, columnProperties, style);
		}

		return workbook;
	}

	/**
	 * 设置表头样式
	 * @param workbook
	 * @param sheet
	 * @param headerRowsNumber
	 */
	private void headerStyleSetup(Workbook workbook, Sheet sheet, int headerRowsNumber) {
		CellStyle style = generateStyle(workbook, true, true, false);
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontName("微软雅黑");
		style.setFont(font);
		style.setFillBackgroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		for (int i = 0; i < headerRowsNumber; i++) {
            Iterator<Cell> it = sheet.getRow(i).cellIterator();
            while (it.hasNext()) {
                Cell cell = it.next();
                cell.setCellStyle(style);
            }
        }
		logger.info("最后一个单元格排序：{}", sheet.getRow(headerRowsNumber - 1).getLastCellNum());
		sheet.createFreezePane(0, headerRowsNumber);
	}

	private void dataStyleSetUp(Sheet sheet, int rowCount, ColumnProperties columnProperties, CellStyle style) {
		for (int i = 1; i < rowCount; i++) {
            Iterator<Cell> it = sheet.getRow(i).cellIterator();
            while (it.hasNext()) {
                Cell cell = it.next();
                cell.setCellStyle(style);
            }
        }
		int[] maxLength = columnProperties.getMaxLength();
		for (int j = 0; j < columnProperties.getColCount(); j++) {
            if (maxLength[j] > 0 && sheet.getColumnWidth(j) < (maxLength[j] + 1) * 512) {
                if (maxLength[j] > 15) {
                    sheet.setColumnWidth(j, 16 * 512);// 最长15个字符，超出部分换行
                } else {
                    sheet.setColumnWidth(j, (maxLength[j] + 1) * 512);// 设置列宽度
                }
            }
        }
	}

	/**
	 * 根据业务对象（包含子集合的BO）集合创建工作簿
	 *
	 * @param material
	 *            申报表所属教材对象
	 * @param extensions
	 *            教材扩展项集合
	 * @param dataSource
	 *            业务对象（BO）集合
	 * @param sheetName
	 *            要生成的Excel表名（非文件名）
	 * @return Excel工作簿
	 */
	public Workbook fromDeclarationEtcBOList(Material material, List<MaterialExtension> extensions,
			List<DeclarationEtcBO> dataSource, String sheetName)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
		if (null == dataSource || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用于导出Excel的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		sheet = generateDeclarationEtcBOHeader(dataSource.get(0).getClass().getDeclaredFields(),extensions, sheet); // 生成表头
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
							//if(material.getIsEduExpUsed()){
								List<DecEduExp> list = (List<DecEduExp>) field.get(object);
								columnProperties = fillDecEduExpData(list, row, columnProperties);
							//}

							break;
						}
						case "工作经历": {
							//if(material.getIsWorkExpUsed()){
								List<DecWorkExp> list = (List<DecWorkExp>) field.get(object);
								columnProperties = fillDecWorkExpData(list, row, columnProperties);
							//}

							break;
						}
						case "教学经历": {
							//if(material.getIsTeachExpUsed()){
								List<DecTeachExp> list = (List<DecTeachExp>) field.get(object);
								columnProperties = fillDecTeachExpData(list, row, columnProperties);
							//}

							break;
						}
						case "个人成就": {
							//if(material.getIsAchievementUsed()){
								DecAchievement decAchievement = (DecAchievement) field.get(object);
								columnProperties = fillDecAchievementData(decAchievement, row, columnProperties);
							//}

							break;
						}
						case "学术兼职": {
							//if(material.getIsAcadeUsed()){
								List<DecAcade> list = (List<DecAcade>) field.get(object);
								columnProperties = fillDecAcadeData(list, row, columnProperties);
							//}

							break;
						}
						case "本套上版教材参编情况": {
							//if(material.getIsLastPositionUsed()){
								List<DecLastPosition> list = (List<DecLastPosition>) field.get(object);
								columnProperties = fillDecLastPositionData(list, row, columnProperties);
							//}

							break;
						}
						case "精品课程建设情况": {
							//if(material.getIsCourseUsed()){
								List<DecCourseConstruction> list = (List<DecCourseConstruction>) field.get(object);
								columnProperties = fillDecCourseConstructionData(list, row, columnProperties);
							//}

							break;
						}
						case "主编国家级规划教材情况": {
							//if(material.getIsNationalPlanUsed()){
								List<DecNationalPlan> list = (List<DecNationalPlan>) field.get(object);
								columnProperties = fillDecNationalPlanData(list, row, columnProperties);
							//}

							break;
						}
						case "人卫社教材编写情况": {
							//if(material.getIsPmphTextbookUsed()){
								List<DecTextbookPmph> list = (List<DecTextbookPmph>) field.get(object);
								columnProperties = fillDecTextbookPmphData(list, row, columnProperties);
							//}

							break;
						}
						case "其他社教材编写情况": {
							//if(material.getIsTextbookUsed()){
								List<DecTextbook> list = (List<DecTextbook>) field.get(object);
								columnProperties = fillDecTextbookData(list, row, columnProperties);
							//}

							break;
						}
						case "参加人卫慕课、数字教材编写情况": {
							//if(material.getIsMoocDigitalUsed()){
								DecMoocDigital decMoocDigital = (DecMoocDigital) field.get(object);
								columnProperties = fillDecMoocDigitalData(decMoocDigital, row, columnProperties);
							//}

							break;
						}
						case "科研情况": {
							//if(material.getIsResearchUsed()){
								List<DecResearch> list = (List<DecResearch>) field.get(object);
								columnProperties = fillDecResearchData(list, row, columnProperties);
							//}

							break;
						}
						case "学术专著": {
							//if(material.getIsMonographUsed()){
								List<DecMonograph> list = (List<DecMonograph>) field.get(object);
								columnProperties = fillDecMonographData(list, row, columnProperties);
							//}

							break;
						}
						case "出版行业获奖情况": {
							//if(material.getIsPublishRewardUsed()){
								List<DecPublishReward> list = (List<DecPublishReward>) field.get(object);
								columnProperties = fillDecPublishRewardData(list, row, columnProperties);
							//}

							break;
						}
						case "SCI论文投稿及影响因子情况": {
							//if(material.getIsSciUsed()){
								List<DecSci> list = (List<DecSci>) field.get(object);
								columnProperties = fillDecSciData(list, row, columnProperties);
						//	}

							break;
						}
						case "临床医学获奖情况": {
							//if(material.getIsClinicalRewardUsed()){
								List<DecClinicalReward> list = (List<DecClinicalReward>) field.get(object);
								columnProperties = fillDecClinicalRewardData(list, row, columnProperties);
							//}

							break;
						}
						case "学术荣誉授予情况": {
							//if(material.getIsAcadeRewardUsed()){
								List<DecAcadeReward> list = (List<DecAcadeReward>) field.get(object);
								columnProperties = fillDecAcadeRewardData(list, row, columnProperties);
							//}

							break;
						}
						case "编写内容意向": {
							//if(material.getIsIntentionUsed()){
								DecIntention decIntention = (DecIntention) field.get(object);
								columnProperties = fillDecIntentionData(decIntention, row, columnProperties);
							//}

							break;
						}
						case "作家扩展项": {
							if (extensions == null || extensions.isEmpty()) {
								break;
							}
							List<DecExtensionVO> list = (List<DecExtensionVO>) field.get(object);
							columnProperties = fillDecExtensionVODataPlus(extensions, list, row, columnProperties);
							break;
						}
						default:
							Object o = field.get(object);
							Cell cell = row.createCell(columnProperties.getColCount());
							if (null != o) {
								String value = "";
								if(o instanceof List){
									value = ((List<String>) o).get(0);
								}else{
									value=o.toString();
								}

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
		/* 样式调整 */
		workbook = dataStyleSetup(workbook, 2, rowCount, columnProperties);
		/* 以下隐藏多余的列 */
		return clearColumns(workbook, material);
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

	private Sheet generateDeclarationEtcBOHeader(Field[] fields ,List<MaterialExtension> extensions, Sheet sheet) {
		//Field[] fields = DeclarationEtcBO.class.getDeclaredFields();
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
					case "个人成就": {
						Cell r1cell = r1.createCell(count);
						r2.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 1, count, count);
						sheet.setColumnWidth(count, 15 * 512);
						sheet.addMergedRegion(region);
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
					case "本套上版教材参编情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 4);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("教材名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("编写职务");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("数字编辑");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版单位");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版时间");
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
					case "人卫社教材编写情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 5);
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
						r2cell.setCellValue("数字编辑");
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
					case "其他社教材编写情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 6);
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
						r2cell.setCellValue("数字编辑");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版社");
						sheet.setColumnWidth(count, 4 * 512);
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
					case "参加人卫慕课、数字教材编写情况": {
						Cell r1cell = r1.createCell(count);
						r2.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 1, count, count);
						sheet.setColumnWidth(count, 15 * 512);
						sheet.addMergedRegion(region);
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
					case "学术专著": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 4);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("专著名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("专著发表日期");
						sheet.setColumnWidth(count, 7 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版方式");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版单位");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("出版时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "出版行业获奖情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("奖项名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("评奖单位");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("获奖时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "SCI论文投稿及影响因子情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 3);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("论文名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("期刊名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("期刊SCI影响因子");
						sheet.setColumnWidth(count, 9 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("发表时间");
						sheet.setColumnWidth(count, 3 * 512);
						count++;
						break;
					}
					case "临床医学获奖情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("奖项名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("奖项级别");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("获奖时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "学术荣誉授予情况": {
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 0, count, count + 2);
						sheet.addMergedRegion(region);
						Cell r2cell = r2.createCell(count);
						r2cell.setCellValue("荣誉名称");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("荣誉级别");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						r2cell = r2.createCell(count);
						r2cell.setCellValue("授予时间");
						sheet.setColumnWidth(count, 5 * 512);
						count++;
						break;
					}
					case "编写内容意向": {
						Cell r1cell = r1.createCell(count);
						r2.createCell(count);
						r1cell.setCellValue(headerName);
						region = new CellRangeAddress(0, 1, count, count);
						sheet.setColumnWidth(count, 15 * 512);
						sheet.addMergedRegion(region);
						count++;
						break;
					}
					case "作家扩展项": {
						if (extensions == null || extensions.isEmpty()) {
							break;
						}
						Cell r1cell = r1.createCell(count);
						r1cell.setCellValue(headerName);
						if (extensions.size() > 1) {
							region = new CellRangeAddress(0, 0, count, count + extensions.size() - 1);
							sheet.addMergedRegion(region);
						}
						for (MaterialExtension extension : extensions) {
							Cell cell = r2.createCell(count);
							String extensionName = extension.getExtensionName() == null ? ""
									: extension.getExtensionName();
							cell.setCellValue(extensionName);
							int length = extension.getExtensionName().length() > 10 ? 10
									: extension.getExtensionName().length();
							sheet.setColumnWidth(count, length * 512);
							count++;
						}
						// Cell r2cell = r2.createCell(count);
						// r2cell.setCellValue("扩展项名称");
						// sheet.setColumnWidth(count, 6 * 512);
						// count++;
						// r2cell = r2.createCell(count);
						// r2cell.setCellValue("扩展项内容");
						// sheet.setColumnWidth(count, 5 * 512);
						// count++;
						break;
					}
					default:
						Cell cell = r1.createCell(count);
						r2.createCell(count);
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

	private Workbook clearColumns(Workbook workbook, Material material) {
		int startColumn = 27;
		if (!material.getIsEduExpUsed()) {
			for (int i = 0; i < 4; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 4;
		}
		if (!material.getIsWorkExpUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsTeachExpUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsAchievementUsed()) {
			workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
		} else {
			startColumn++;
		}
		if (!material.getIsAcadeUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsLastPositionUsed()) {
			for (int i = 0; i < 5; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 5;
		}
		if (!material.getIsCourseUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsNationalPlanUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsPmphTextbookUsed()) {
			for (int i = 0; i < 6; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 6;
		}
		if (!material.getIsTextbookUsed()) {
			for (int i = 0; i < 7; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 7;
		}
		if (!material.getIsMoocDigitalUsed()) {
			workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
		} else {
			startColumn++;
		}
		if (!material.getIsResearchUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsMonographUsed()) {
			for (int i = 0; i < 5; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 5;
		}
		if (!material.getIsPublishRewardUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsSciUsed()) {
			for (int i = 0; i < 4; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 4;
		}
		if (!material.getIsClinicalRewardUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsAcadeRewardUsed()) {
			for (int i = 0; i < 3; i++) {
				workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
			}
		} else {
			startColumn += 3;
		}
		if (!material.getIsIntentionUsed()) {
			workbook.getSheetAt(0).setColumnHidden(startColumn++, true);
		} else {
			startColumn++;
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
				value = "无";
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
				case 4:
					value = "市级";
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
			for (int i = 0; i < 5; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(6);
			for (int i = 0; i < 5; i++) {
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
				colCount++;
				value = decLastPosition.getIsDigitalEditor() ? "是" : "否";
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decLastPosition.getPublisher();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "";
				if (ObjectUtil.notNull(decLastPosition.getPublishDate())) {
					value = DateUtil.date2Str(decLastPosition.getPublishDate());
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
				value = "无";
				switch (decCourseConstruction.getType()) {
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
				value = decNationalPlan.getRankText();
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

	private ColumnProperties fillDecTextbookPmphData(List<DecTextbookPmph> decTextbookPmphs, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decTextbookPmphs)) {
			for (int i = 0; i < 6; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(6);
			for (int i = 0; i < 6; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecTextbookPmph decTextbookPmph : decTextbookPmphs) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decTextbookPmph.getMaterialName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "其他";
				switch (decTextbookPmph.getRank()) {
				case 0:
					value = "无";
					break;
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
				default:
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "无";
				switch (decTextbookPmph.getPosition()) {
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
				value = decTextbookPmph.getIsDigitalEditor() ? "是" : "否";
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				if (ObjectUtil.isNull(decTextbookPmph.getPublishDate())) {
					value = "";
				} else {
					value = sdf.format(decTextbookPmph.getPublishDate());
				}

				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decTextbookPmph.getIsbn();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < 6; i++) {
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
			for (int i = 0; i < 7; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(7);
			for (int i = 0; i < 7; i++) {
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
				case 0:
					value = "无";
					break;
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
				value = decTextbook.getIsDigitalEditor() ? "是" : "否";
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decTextbook.getPublisher();
				if (StringUtil.isEmpty(value)) {
					value = "";
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
			for (int i = 0; i < 7; i++) {
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

	private ColumnProperties fillDecMonographData(List<DecMonograph> decMonographs, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decMonographs)) {
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
			for (DecMonograph decMonograph : decMonographs) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decMonograph.getMonographName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date date = decMonograph.getMonographDate();
				value = "";
				if (!ObjectUtil.isNull(date)) {
					value = sdf.format(date);
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = "";
				if (decMonograph.getIsSelfPaid()) {
					value = "自费";
				} else {
					value = "公费";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decMonograph.getPublisher();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date publish = decMonograph.getPublishDate();
				value = "";
				if (!ObjectUtil.isNull(publish)) {
					value = sdf.format(publish);
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

	private ColumnProperties fillDecPublishRewardData(List<DecPublishReward> decPublishRewards, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decPublishRewards)) {
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
			for (DecPublishReward decPublishReward : decPublishRewards) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decPublishReward.getRewardName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decPublishReward.getAwardUnit();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date reward = decPublishReward.getRewardDate();
				value = "";
				if (!ObjectUtil.isNull(reward)) {
					value = sdf.format(reward);
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

	private ColumnProperties fillDecSciData(List<DecSci> decScis, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decScis)) {
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
			for (DecSci decSci : decScis) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decSci.getPaperName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() / 2 + 1 > maxLength[colCount]) {
					maxLength[colCount] = value.length() / 2 + 1;
				}
				colCount++;
				value = decSci.getJournalName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				value = decSci.getFactor();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date publishDate = decSci.getPublishDate();
				value = "";
				if (ObjectUtil.notNull(publishDate)) {
					value = sdf.format(publishDate);
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

	private ColumnProperties fillDecClinicalRewardData(List<DecClinicalReward> decClinicalRewards, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decClinicalRewards)) {
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
			for (DecClinicalReward decClinicalReward : decClinicalRewards) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decClinicalReward.getRewardName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				switch (decClinicalReward.getAwardUnit()) {
				case 0:
					value = "无";
					break;
				case 1:
					value = "国际";
					break;
				case 2:
					value = "国家";
					break;

				default:
					value = "";
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date reward = decClinicalReward.getRewardDate();
				value = "";
				if (!ObjectUtil.isNull(reward)) {
					value = sdf.format(reward);
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

	private ColumnProperties fillDecAcadeRewardData(List<DecAcadeReward> decAcadeRewards, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decAcadeRewards)) {
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
			for (DecAcadeReward decAcadeReward : decAcadeRewards) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decAcadeReward.getRewardName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				switch (decAcadeReward.getAwardUnit()) {
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
					value = "";
					break;
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;
				Date reward = decAcadeReward.getRewardDate();
				value = "";
				if (!ObjectUtil.isNull(reward)) {
					value = sdf.format(reward);
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

	private ColumnProperties fillDecAchievementData(DecAchievement decAchievement, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (ObjectUtil.isNull(decAchievement)) {
			row.createCell(colCount++);
		} else {
			Cell cell = row.createCell(colCount++);
			String content = decAchievement.getContent();
			if (StringUtil.notEmpty(content)) {
				cell.setCellValue(content);
				maxLength[colCount - 1] = content.length();
			} else {
				maxLength[colCount - 1] = 2;
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecMoocDigitalData(DecMoocDigital decMoocDigital, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (ObjectUtil.isNull(decMoocDigital)) {
			row.createCell(colCount++);
		} else {
			Cell cell = row.createCell(colCount++);
			String content = decMoocDigital.getContent();
			if (StringUtil.notEmpty(content)) {
				cell.setCellValue(content);
				maxLength[colCount - 1] = content.length();
			} else {
				maxLength[colCount - 1] = 2;
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecIntentionData(DecIntention decIntention, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (ObjectUtil.isNull(decIntention)) {
			row.createCell(colCount++);
		} else {
			Cell cell = row.createCell(colCount++);
			String content = decIntention.getContent();
			if (StringUtil.notEmpty(content)) {
				cell.setCellValue(content);
				maxLength[colCount - 1] = content.length();
			} else {
				maxLength[colCount - 1] = 2;
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecExtensionVOData(List<DecExtensionVO> decExtensionVOs, Row row,
			ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decExtensionVOs)) {
			for (int i = 0; i < 2; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(2);
			for (int i = 0; i < 2; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecExtensionVO decExtensionVO : decExtensionVOs) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;
				value = decExtensionVO.getExtensionName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length() / 2 + 1;
				}
				colCount++;
				value = decExtensionVO.getContent();
				if (StringUtil.isEmpty(value)) {
					value = "";
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

	private ColumnProperties fillDecExtensionVODataPlus(List<MaterialExtension> extensions,
			List<DecExtensionVO> decExtensionVOs, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decExtensionVOs)) {
			for (MaterialExtension extension : extensions) {
				row.createCell(colCount++);
			}
		} else {
			for (MaterialExtension extension : extensions) {
				for (DecExtensionVO vo : decExtensionVOs) {
					if (vo.getExtensionId().equals(extension.getId())) {
						Cell cell = row.createCell(colCount++);
						String content = vo.getContent();
						if (StringUtil.notEmpty(content)) {
							cell.setCellValue(content);
							maxLength[colCount - 1] = content.length();
						} else {
							maxLength[colCount - 1] = 2;
						}
						break;
					}
				}
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	/**
	 *
	 * Description:用于设置选题号页面的导入功能
	 *
	 * @author:lyc
	 * @date:2018年1月23日下午6:03:03
	 * @param
	 * @return Workbook
	 */
	public Workbook fromTextbookTopic(List<Textbook> dataSource, String sheetName)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
		if (ObjectUtil.isNull(dataSource) || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.TEXTBOOK, CheckedExceptionResult.NULL_PARAM,
					"用于导出的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("书序");
		header.createCell(1).setCellValue("书籍名称");
		header.createCell(2).setCellValue("版次");
		header.createCell(3).setCellValue("选题号");
		headerStyleSetup(workbook, 1);
		int rowCount = 1;
		for (Textbook textbook : dataSource) {
			Row row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(textbook.getSort());
			row.createCell(1).setCellValue(textbook.getTextbookName());
			row.createCell(2).setCellValue(textbook.getTextbookRound());
			row.createCell(3).setCellValue(textbook.getTopicNumber());
			rowCount++;
		}
		int[] maxLength = { 2, 12, 2, 12 };
		return dataStyleSetup(workbook, 1, rowCount, new ColumnProperties(4, maxLength));
	}

	public Workbook fromOrgVO(List<OrgVO> dataSource, String sheetName)
			throws CheckedServiceException, IllegalAccessException, IllegalArgumentException {
		if (null == dataSource || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL, CheckedExceptionResult.NULL_PARAM,
					"用于导出的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("序号");
		header.createCell(1).setCellValue("机构名称");
		header.createCell(2).setCellValue("机构账号");
		header.createCell(3).setCellValue("管理员名称");
		header.createCell(4).setCellValue("机构类型");
		headerStyleSetup(workbook, 1);
		int rowCount = 1;
		for (OrgVO orgVO : dataSource) {
			Row row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(rowCount);
			row.createCell(1).setCellValue(orgVO.getOrgName());
			row.createCell(2).setCellValue(orgVO.getUsername());
			row.createCell(3).setCellValue(orgVO.getRealname());
			row.createCell(4).setCellValue(orgVO.getOrgTypeName());
			rowCount++;
		}
		int[] maxLength = { 2, 15, 15, 10, 15 };
		return dataStyleSetup(workbook, 1, rowCount, new ColumnProperties(5, maxLength));
	}


	/**
	 * 根据业务对象（包含子集合的BO）集合创建工作簿
	 *
	 * @param product
	 *            申报表所属教材对象
	 * @param dataSource
	 *            业务对象（BO）集合
	 * @param sheetName
	 *            要生成的Excel表名（非文件名）
	 * @return Excel工作簿
	 */
	public Workbook fromExpertationList(ProductVO product,
										List<ExpertationVO> dataSource, String sheetName)
			throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
		List<ProductExtension> extensions = product.getProductExtensionList();
		if (null == dataSource || dataSource.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
					"用于导出Excel的数据源为空");
		}
		Workbook workbook = new HSSFWorkbook();
		sheetName = sheetName.replaceAll("-\\d{4}-\\d{2}-\\d{2}-\\d{2}：\\d{2}：\\d{2}","");
		Sheet sheet = workbook.createSheet(sheetName);
		sheet = generateExpertationHeader(dataSource,product, sheet); // 生成表头
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
					String usedPropertyName=excelHeader.usedPropertyName();
					Boolean used = true;
					if(StringUtil.notEmpty(usedPropertyName)){
						used = getPropertyByName(product, usedPropertyName);
					}
					if (StringUtil.notEmpty(headerName) && used) {
						switch (headerName) {
							case "学习经历": {
								//if(product.getIsEduExpUsed()){
								List<DecEduExp> list = (List<DecEduExp>) field.get(object);
								columnProperties = fillDecEduExpData(list, row, columnProperties);
								//}

								break;
							}
							case "工作经历": {
								//if(product.getIsWorkExpUsed()){
								List<DecWorkExp> list = (List<DecWorkExp>) field.get(object);
								columnProperties = fillDecWorkExpData(list, row, columnProperties);
								//}

								break;
							}
							case "学术兼职": {
								//if(product.getIsAcadeUsed()){
								List<DecAcade> list = (List<DecAcade>) field.get(object);
								columnProperties = fillDecAcadeData(list, row, columnProperties);
								//}

								break;
							}
							
							case "人卫社教材编写情况": {
								//if(product.getIsPmphTextbookUsed()){
								List<DecTextbookPmph> list = (List<DecTextbookPmph>) field.get(object);
								columnProperties = fillDecTextbookPmphData(list, row, columnProperties);
								//}

								break;
							}


							case "图书出版情况": {
								//if(product.getIsAcadeRewardUsed()){
								List<DecMonograph> list = (List<DecMonograph>) field.get(object);
								columnProperties = fillDecMonographData(list, row, columnProperties);
								//}

								break;
							}
							case "主编或参编图书情况": {
								//if(product.getIsIntentionUsed()){
								List<DecEditorBook> list = (List<DecEditorBook>) field.get(object);
								columnProperties = fillDecEditorBookData(list, row, columnProperties);

								//}

								break;
							}
							case "本专业获奖情况": {
								//if(product.getIsIntentionUsed()){
								List<DecProfessionAward> list = (List<DecProfessionAward>) field.get(object);
								columnProperties = fillDecProfessionAwardData(list, row, columnProperties);

								//}

								break;
							}
							case "文章发表情况": {
								//if(product.getIsIntentionUsed()){
								List<DecArticlePublished> list = (List<DecArticlePublished>) field.get(object);
								columnProperties = fillDecArticlePublishedData(list, row, columnProperties);

								//}

								break;
							}case "学科分类": {
								List<ProductType> list = (List<ProductType>) field.get(object);
								columnProperties = fillProductTypeData(list, row, columnProperties);
								break;
							}
							case "内容分类": {
								List<ProductType> list = (List<ProductType>) field.get(object);
								columnProperties = fillProductTypeData(list, row, columnProperties);
								break;
							}
							case "申报专业": {
								List<ProductProfessionType> list = (List<ProductProfessionType>) field.get(object);
								columnProperties = fillProductProfessionTypeData(list, row, columnProperties);
								break;
							}
							
							
							case "作家扩展项": {
								if (extensions == null || extensions.isEmpty()) {
									break;
								}
								List<DecExtension> list = (List<DecExtension>) field.get(object);
								columnProperties = fillExpExtensionVODataPlus(extensions, list, row, columnProperties);
								break;
							}
							default:
								Object o = field.get(object);
								Cell cell = row.createCell(columnProperties.getColCount());
								if (null != o) {
									String value = "";
									if(o instanceof List){
										value = ((List<String>) o).get(0);
									}else{
										value=o.toString();
									}

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
		/* 样式调整 */
		workbook = dataStyleSetup(workbook, 2, rowCount, columnProperties);
		return workbook;
	}

	private ColumnProperties fillProductProfessionTypeData(List<ProductProfessionType> list, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		int propertyNum = 1;
		if (CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < propertyNum; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(propertyNum);
			for (int i = 0; i < propertyNum; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (ProductProfessionType productType : list) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;

				value = productType.getTypeName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < propertyNum; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;

	}

	private ColumnProperties fillProductTypeData(List<ProductType> list, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		int propertyNum = 1;
		if (CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < propertyNum; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(propertyNum);
			for (int i = 0; i < propertyNum; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (ProductType productType : list) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;

				value = StringUtil.isEmpty(productType.getFullNamePath())?productType.getType_name():productType.getFullNamePath();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < propertyNum; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;


	}

	private ColumnProperties fillDecArticlePublishedData(List<DecArticlePublished> list, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		int propertyNum = 3;
		if (CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < propertyNum; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(propertyNum);
			for (int i = 0; i < propertyNum; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecArticlePublished decArticlePublished : list) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;

				value = decArticlePublished.getTitle();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decArticlePublished.getPeriodicalTitle();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decArticlePublished.getPeriodicalLevel();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}

				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < propertyNum; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecProfessionAwardData(List<DecProfessionAward> list, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		int propertyNum = 3;
		if (CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < propertyNum; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(propertyNum);
			for (int i = 0; i < propertyNum; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecProfessionAward decProfessionAward : list) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;

				value = decProfessionAward.getTitle();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decProfessionAward.getRank();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decProfessionAward.getNote();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}

				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < propertyNum; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillDecEditorBookData(List<DecEditorBook> list, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		int propertyNum = 3;
		if (CollectionUtil.isEmpty(list)) {
			for (int i = 0; i < propertyNum; i++) {
				row.createCell(colCount++);
			}
		} else {
			String value;
			List<StringBuilder> builders = new ArrayList<>(propertyNum);
			for (int i = 0; i < propertyNum; i++) {
				builders.add(new StringBuilder());
			}
			boolean isFirst = true;
			for (DecEditorBook decEditorBook : list) {
				if (isFirst == false) {
					for (StringBuilder builder : builders) {
						builder.append("\r\n");
					}
				} else {
					isFirst = false;
				}
				int index = 0;

				value = decEditorBook.getMaterialName();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decEditorBook.getPublisher();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}
				colCount++;

				value = decEditorBook.getPublishDate();
				if (StringUtil.isEmpty(value)) {
					value = "";
				}
				builders.get(index++).append(value);
				if (value.length() > maxLength[colCount]) {
					maxLength[colCount] = value.length();
				}

				colCount = properties.getColCount();// 列数复位
			}
			for (int i = 0; i < propertyNum; i++) {
				Cell cell = row.createCell(colCount++);
				value = builders.get(i).toString();
				cell.setCellValue(value);
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private ColumnProperties fillExpExtensionVODataPlus(List<ProductExtension> extensions,
														List<DecExtension> decExtensionVOs, Row row, ColumnProperties properties) {
		int colCount = properties.getColCount();
		int[] maxLength = properties.getMaxLength();
		if (CollectionUtil.isEmpty(decExtensionVOs)) {
			for (ProductExtension extension : extensions) {
				if(extension.getIsDeleted()==null || !extension.getIsDeleted()){
					row.createCell(colCount++);
				}
			}
		} else {
			for (ProductExtension extension : extensions) {
				Cell cell = row.createCell(colCount++);
				if (extension.getIsDeleted()==null || !extension.getIsDeleted()) {
					for (DecExtension vo : decExtensionVOs) {
						if (vo.getExtensionId().equals(extension.getId())) {

							String content = vo.getContent();
							if (StringUtil.notEmpty(content)) {
								cell.setCellValue(content);
								maxLength[colCount - 1] = content.length();
							} else {
								maxLength[colCount - 1] = 2;
							}
							break;
						}
					}
				}
			}
		}
		properties.setColCount(colCount);
		properties.setMaxLength(maxLength);
		return properties;
	}

	private Boolean getPropertyByName(Object obj, String propertyName)  {
		String getterStr = "get" + propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
		Boolean used = true;
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
            Method get = obj.getClass().getMethod(getterStr);
            used = (Boolean)get.invoke(obj);
        }catch (NoSuchFieldException e){
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
			e.printStackTrace();
		}
		return used;
	}

	private Sheet generateExpertationHeader(List<ExpertationVO> dataSource, ProductVO product, Sheet sheet) {
		//todo 生成表头
		Field[] fields = dataSource.get(0).getClass().getDeclaredFields();
		List<ProductExtension> extensions = product.getProductExtensionList();
		Row r1 = sheet.createRow(0);
		Row r2 = sheet.createRow(1);
		Cell numcell = r1.createCell(0);
		numcell.setCellValue("序号");
		CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(region);
		int count = 1;

		for (Field field:fields) {

			field.setAccessible(true);// 可访问性设置
			/* 仅查找与ExcelHeader注解匹配的表头 */
			if (field.isAnnotationPresent(ExcelHeader.class)) {
				ExcelHeader excelHeader = (ExcelHeader) field.getAnnotation(ExcelHeader.class);
				String headerName = excelHeader.header();
				String usedPropertyName=excelHeader.usedPropertyName();
				Boolean used = true;
				if(StringUtil.notEmpty(usedPropertyName)){
					used = getPropertyByName(product, usedPropertyName);
				}
				if (StringUtil.notEmpty(headerName) && used) {
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
						case "人卫社教材编写情况": {
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							region = new CellRangeAddress(0, 0, count, count + 5);
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
							r2cell.setCellValue("数字编辑");
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
						}case "图书出版情况": {
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							region = new CellRangeAddress(0, 0, count, count + 4);
							sheet.addMergedRegion(region);
							Cell r2cell = r2.createCell(count);
							r2cell.setCellValue("专著名称");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("发表日期");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("出版方式");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("出版单位");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("出版时间");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							break;
						}case "主编或参编图书情况": {
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							region = new CellRangeAddress(0, 0, count, count + 2);
							sheet.addMergedRegion(region);
							Cell r2cell = r2.createCell(count);
							r2cell.setCellValue("图书名称");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("出版单位");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("出版时间");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							break;
						}case "文章发表情况": {
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							region = new CellRangeAddress(0, 0, count, count + 2);
							sheet.addMergedRegion(region);
							Cell r2cell = r2.createCell(count);
							r2cell.setCellValue("题目");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("期刊名称");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("期刊级别");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							break;
						}case "本专业获奖情况": {
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							region = new CellRangeAddress(0, 0, count, count + 2);
							sheet.addMergedRegion(region);
							Cell r2cell = r2.createCell(count);
							r2cell.setCellValue("题目");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("级别");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							r2cell = r2.createCell(count);
							r2cell.setCellValue("备注");
							sheet.setColumnWidth(count, 5 * 512);
							count++;
							break;
						}

						case "作家扩展项": {
							if (extensions == null || extensions.isEmpty()) {
								break;
							}
							Cell r1cell = r1.createCell(count);
							r1cell.setCellValue(headerName);
							if (extensions.size() > 1) {
								region = new CellRangeAddress(0, 0, count, count + extensions.size() - 1);
								sheet.addMergedRegion(region);
							}
							for (ProductExtension extension : extensions) {
								Cell cell = r2.createCell(count);
								String extensionName = extension.getExtensionName() == null ? ""
										: extension.getExtensionName();
								cell.setCellValue(extensionName);
								int length = extension.getExtensionName().length() > 10 ? 10
										: extension.getExtensionName().length();
								sheet.setColumnWidth(count, length * 512);
								count++;
							}
							// Cell r2cell = r2.createCell(count);
							// r2cell.setCellValue("扩展项名称");
							// sheet.setColumnWidth(count, 6 * 512);
							// count++;
							// r2cell = r2.createCell(count);
							// r2cell.setCellValue("扩展项内容");
							// sheet.setColumnWidth(count, 5 * 512);
							// count++;
							break;
						}
						default:
							Cell cell = r1.createCell(count);
							r2.createCell(count);
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


}
